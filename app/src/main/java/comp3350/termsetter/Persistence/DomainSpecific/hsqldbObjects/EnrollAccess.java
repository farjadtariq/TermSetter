package comp3350.termsetter.Persistence.DomainSpecific.hsqldbObjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.termsetter.Persistence.DomainSpecific.User;

public class EnrollAccess {


    Connection connect = null;

    private final String dbPath;

    public EnrollAccess(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }


    /*// need the userID of the user!
    public List<String> getEnrollment(User user) throws SQLException {
        List<String> currentCourses = new ArrayList<>();
        // first connect
        connect = this.connection();

        user.getStudentID();
        //query (this wont look pretty)
        PreparedStatement statement = connect.prepareStatement("select enrollment.course_id , courses.course_name, enrollment.student_id, students.student_username " +
                "from enrollment inner join courses " +
                "inner join students " +
                "on enrollment.course_id = courses.course_id AND students.student_id = enrollment.student_id " +
                "where enrollment.student_id = ?;");
        statement.setString(1, user.getStudentID());

        ResultSet resultSet = statement.executeQuery();

        // collect
        while (resultSet.next()) {
            final String course_id = resultSet.getString("course_id");
            final String course_name = resultSet.getString("course_name");

            //put them in a list for now
            final String course = course_id + " " + course_name;
            currentCourses.add(course);
        }

        //I hope this works
        return currentCourses;
    }*/


    public List<String> getEnrollment(User user) throws SQLException {
        List<String> currentCourses = new ArrayList<>();

        connect = this.connection();

        //query
        PreparedStatement statement = connect.prepareStatement("select course_id, course_name from enrollment where student_id = ?");
        ResultSet resultSet = statement.executeQuery();

        //collect
        while (resultSet.next()) {
            final String course_id = resultSet.getString("course_id");
            final String course_name = resultSet.getString("course_name");

            //put them in a list for now
            final String course = course_id + " " + course_name;
            currentCourses.add(course);
        }
        return currentCourses;
    }
}
