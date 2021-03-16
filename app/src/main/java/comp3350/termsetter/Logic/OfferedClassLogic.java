package comp3350.termsetter.Logic;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import comp3350.termsetter.Persistence.CourseCategoryDriver;
import comp3350.termsetter.Persistence.CourseCategorySQLDriver;
import comp3350.termsetter.Persistence.Faculty;
import comp3350.termsetter.R;

public class OfferedClassLogic{

    List<Faculty> courseData;
    Context context;

    public OfferedClassLogic(boolean useSQL, Context context){
        this.context = context;
        if(useSQL){
            loadFromSQL();
        }
        else{
            loadFromDriver();
        }
    }

    public List<Faculty> getCourseData(){
        return courseData;
    }

    private void loadFromSQL(){
        try{
            CourseCategorySQLDriver courseDatabase = new CourseCategorySQLDriver();
            courseData = courseDatabase.getFaculties();
        }
        catch(SQLException e){
            System.out.println("CourseCategorySQLDriver failed loading the course data.");
            e.printStackTrace();
        }
    }

    private void loadFromDriver(){
        try{
            InputStream is = context.getResources().openRawResource(R.raw.classdatabase);
            CourseCategoryDriver courseDatabase = new CourseCategoryDriver(is);
            courseData = courseDatabase.getFaculties();
        }
        catch(IOException e){
            System.out.println("Database source file 'classdb.txt' is missing from res/assets.");
            e.printStackTrace();
        }
    }

}