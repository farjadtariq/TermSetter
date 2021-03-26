package comp3350.termsetter.Presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.sql.SQLException;
import comp3350.termsetter.Logic.AccessStudents;
import comp3350.termsetter.Logic.AccountValidation;
import comp3350.termsetter.Persistence.DBImporter;
import comp3350.termsetter.Persistence.DomainSpecific.StubDatabase;
import comp3350.termsetter.Persistence.StudentPersistence;
import comp3350.termsetter.R;

public class LoginPage extends AppCompatActivity {
    private static Context mContext;
    private StudentPersistence database;
    private EditText eID;
    private EditText ePassword;
    private Button eLogin;
    private AccountValidation accountValidation;
    private AccessStudents accessStudents;
    String currAccount = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        try {
            DBImporter.copyDatabaseToDevice(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mContext = getApplicationContext();

        // Comment this database to switch to Real database
        // Uncomment this database to switch to Stub database
        //database = new StubDatabase(mContext,"test.db");

        // if DB is real
        if(!(database instanceof StubDatabase)) {
            accessStudents = new AccessStudents();
            database = accessStudents.getStudentPersistence();
            Toast.makeText(LoginPage.this, "REAL DB", Toast.LENGTH_SHORT).show();

        }
        else
        {
            accessStudents = new AccessStudents(mContext);
            database = accessStudents.getStudentPersistence();
            Toast.makeText(LoginPage.this, "FakeDB", Toast.LENGTH_SHORT).show();
        }


    }

    public void onClickLoginButton(View view) throws SQLException {
        eID = findViewById(R.id.editTextUserID);
        ePassword = findViewById(R.id.editTextPassword);
        eLogin = findViewById(R.id.buttonLogin);
        accountValidation = new AccountValidation();

        String inputID = eID.getText().toString();
        String inputPassword = ePassword.getText().toString();

        if (accountValidation.validID(inputID)) {
            if (accountValidation.validPassword(inputPassword) && database.getStudent(inputID) != null) {
                database.setCurrentStudentID(inputID);
//                Student currStudent = database.getCurrentStudentID();
//                editor.clear();
//                editor.putString("currAccount", currStudent.getStudentID()); // could use inputID but more explicit

                Toast.makeText(LoginPage.this, "Welcome " + inputID + " !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginPage.this, MainActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(LoginPage.this, "Check your password again!", Toast.LENGTH_SHORT).show();
            }
        }
            else {
            Toast.makeText(LoginPage.this, "Check your ID again!", Toast.LENGTH_SHORT).show();
            }
    }

    public void onClickCreateAccountButton(View view) throws SQLException {
        // Brief message
        // Shows create account page
        Toast.makeText(this, "Create Account Button pressed!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);

    }
}