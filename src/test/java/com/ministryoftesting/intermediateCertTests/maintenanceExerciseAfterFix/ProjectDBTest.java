package com.ministryoftesting.intermediateCertTests.maintenanceExerciseAfterFix;

import com.ministryoftesting.db.ProjectDB;
import com.ministryoftesting.models.project.Entry;
import com.ministryoftesting.models.project.Project;
import com.ministryoftesting.models.project.ProjectDetails;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectDBTest {



    @Test
    public void returnPositiveResultCreatingProject() throws SQLException {
        ProjectDB projectDB = new ProjectDB();
        Project project = new Project( "Project 1", "This is a brief description of Project 1");

        int result = projectDB.createProject(project);

        assertTrue(result > 0);

        int noOfProjects = projectDB.getProjects().size();
        projectDB.deleteProject(noOfProjects);
    }

    @Test
    public void returnPositiveResultDeletingProject() throws SQLException {
        ProjectDB projectDB = new ProjectDB();

        boolean result = projectDB.deleteProject(1);

        assertTrue(result);
    }

    @Test
    public void returnNegativeResultDeletingProject() throws SQLException {
        ProjectDB projectDB = new ProjectDB();

        boolean result = projectDB.deleteProject(999);

        assertFalse(result);
    }

    @Test
    public void returnPositiveResultDeletingEntry() throws SQLException {
        ProjectDB projectDB = new ProjectDB();

        boolean result = projectDB.deleteEntry(1, 1);

        assertTrue(result);
    }

    @Test
    public void returnNegativeResultDeletingEntry() throws SQLException {
        ProjectDB projectDB = new ProjectDB();

        boolean result = projectDB.deleteEntry(1, 999);

        assertFalse(result);
    }

    @Test
    public void returnPositiveResultWhenCreatingEntry() throws SQLException {
        ProjectDB projectDB = new ProjectDB();

        int result = projectDB.storeEntry(1, new Entry(LocalDate.now(), 8, "Ate cake"));

        assertTrue(result > 0);
    }

    @Test
    public void returnProjectsWhenQueryingProjectDB() throws SQLException {
        ProjectDB projectDB = new ProjectDB();

        //cleardown any existing projects in the DB
        for (int i = projectDB.getProjects().size(); i > 0; i--) {
            projectDB.deleteProject(i);
        }

        projectDB.createProject(new Project( "Project 1", "This is a brief description of Project 1"));
        projectDB.createProject(new Project( "Project 2", "This is a brief description of Project 2"));
        projectDB.createProject(new Project( "Project 3", "This is a brief description of Project 3"));
        List<Project> projects = projectDB.getProjects();

        assertEquals(3, projects.size());
    }

    @Test
    public void returnProjectDetailsWhenQueryingProjectDB() throws SQLException {
        ProjectDB projectDB = new ProjectDB();

        int projectId = projectDB.createProject(new Project( "Group project", "This project has lots of entries"));
        projectDB.storeEntry(projectId, new Entry(LocalDate.of(2023,1,1), 8, "Ate cake"));
        projectDB.storeEntry(projectId, new Entry(LocalDate.of(2023,1,2), 7, "Ate pie"));
        projectDB.storeEntry(projectId, new Entry(LocalDate.of(2023, 1, 3), 6, "Ate pizza"));

        ProjectDetails project = projectDB.getProject(projectId);

        assertEquals("Group project", project.getName());
        assertEquals(3, project.getEntries().size());
    }
}
