package com.ministryoftesting.intermediateCertTests.maintenanceExerciseAfterFix;

import com.ministryoftesting.api.TimesheetManagerApplication;
import com.ministryoftesting.models.CreatedID;
import com.ministryoftesting.models.auth.Credentials;
import com.ministryoftesting.models.project.Entry;
import com.ministryoftesting.models.project.Project;
import io.restassured.response.Response;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = TimesheetManagerApplication.class)
@ActiveProfiles("dev")
public class ProjectSIT {

    @Test
    public void returnPositiveResponseWhenGettingProjectTimesheet(){
        Credentials credentials = getCredentials();

        String token = credentials.getToken();

        CreatedID createdID = getCreatedID(token);

        createNewEntry(token, createdID);

        createNewEntry(token, createdID);

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .get("/v1/project/" + createdID.getId());

        Approvals.verify(response.getBody().prettyPrint());
    }

    private static Credentials getCredentials() {
        Credentials credentials = given()
                .body("{\"email\":\"admin@test.com\",\"password\":\"password123\"}")
                .contentType("application/json")
                .post("/v1/auth/login")
                .as(Credentials.class);
        return credentials;
    }

    private static CreatedID getCreatedID(String token) {
        CreatedID createdID = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(new Project("Project 1", "Ate Cake"))
                .when()
                .post("/v1/project")
                .as(CreatedID.class);
        return createdID;
    }

    private static void createNewEntry(String token, CreatedID createdID) {
        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(new Entry(createdID.getId(), LocalDate.of(2050, 1, 1), 8, "Ate cake"))
                .when()
                .post("/v1/project/" + createdID.getId() + "/entry")
                .as(CreatedID.class);
    }
}
