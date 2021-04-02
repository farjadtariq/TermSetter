package comp3350.termsetter.Tests;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import comp3350.termsetter.Logic.OfferedClassLogic;
import comp3350.termsetter.Persistence.CourseCategoryPersistence;
import comp3350.termsetter.Persistence.CourseOffering;
import comp3350.termsetter.Persistence.CourseSection;
import comp3350.termsetter.Persistence.DBImporter;
import comp3350.termsetter.Persistence.DomainSpecific.hsqldbObjects.CourseAccess;
import comp3350.termsetter.Persistence.Faculty;
import comp3350.termsetter.Persistence.Main;
import comp3350.termsetter.Presentation.OfferedClassesCategories;
import comp3350.termsetter.Presentation.OfferedClassesView;

public class CourseAccessTest {

    CourseAccess ca;

    @Before
    public void setup() throws IOException {
        OfferedClassesCategories act = mock(OfferedClassesCategories.class);
        DBImporter.copyDatabaseToDevice(act);
        String path = Main.getDBPathName();
        ca = new CourseAccess(path);
    }

    @Test
    public void testCourseAccessExists() throws SQLException{
        System.out.println("\nStarting testCourseAccessExists: object exists after creation\n");
        assertNotNull(ca);
        System.out.println("End testCourseAccessExists: object exists after creation\n");
    }

    @Test
    public void testGetFaculties() throws SQLException {
        System.out.println("\nStarting testGetFaculties: objects are retrieved\n");
        List<String> faculties = ca.getAllFaculties();
        assertNotNull(faculties);
        System.out.println("End testGetFaculties: objects are retrieved\n");
    }

    @Test
    public void testGetEachFaculty() throws SQLException {
        System.out.println("\nStarting testGetFaculties: objects are retrieved\n");
        List<String> faculties = ca.getAllFaculties();
        for(int i = 0; i < faculties.size(); i++){
            assertNotEquals("", faculties.get(i));
        }
        System.out.println("End testGetFaculties: objects are retrieved\n");
    }

    @Test
    public void testGetCourseByFaculty() throws SQLException {
        System.out.println("\nStarting testGetCourseByFaculty: objects are retrieved\n");
        List<String> faculties = ca.getAllFaculties();
        for(int i = 0; i < faculties.size(); i++){
            String theFaculty = faculties.get(i);
            List<String> courses = ca.getCourseByFaculty(theFaculty);
            assertNotNull(courses);
        }
        System.out.println("End testGetCourseByFaculty: objects are retrieved\n");
    }

    @Test
    public void testGetSectionByCourse() throws SQLException {
        System.out.println("\nStarting testGetSectionByCourse: objects are retrieved\n");
        List<String> faculties = ca.getAllFaculties();
        for(int i = 0; i < faculties.size(); i++){
            String theFaculty = faculties.get(i);
            List<String> courses = ca.getCourseByFaculty(theFaculty);
            for(int j = 0; j < courses.size(); j++){
                String theCourse = courses.get(j);
                List<String> sections = ca.getSectionByCourse(theFaculty, theCourse);
                assertNotNull(sections);
            }
        }
        System.out.println("End testGetSectionByCourse: objects are retrieved\n");
    }
}
