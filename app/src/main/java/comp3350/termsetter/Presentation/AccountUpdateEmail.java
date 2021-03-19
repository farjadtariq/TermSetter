package comp3350.termsetter.Presentation;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;

import comp3350.termsetter.Logic.AccessStudents;
import comp3350.termsetter.Persistence.DomainSpecific.StubDatabase;
import comp3350.termsetter.Persistence.DomainSpecific.hsqldbObjects.StudentAccess;
import comp3350.termsetter.Persistence.UserPersistence;
import comp3350.termsetter.R;

import comp3350.termsetter.Persistence.DomainSpecific.User;

public class AccountUpdateEmail extends AppCompatActivity {
    private static Context mContext;
    private UserPersistence database;
    private boolean validate;
    private EditText newEmail;
    private EditText newEmailConfirm;

    private AccessStudents accessStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_update_email);

        mContext = getApplicationContext();
        //database = new StubDatabase(mContext,"test.db");
//        database = new StudentAccess("users.db");

        accessStudents = new AccessStudents();
        database = accessStudents.getStudentPersistence();
        try {
            displayProfile();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void displayProfile() throws SQLException {
        User user = database.getCurrentUser();
        TextView studentEmail = findViewById(R.id.updateEmailTxtStudent);
        studentEmail.setText(user.getEmailAddress());
    }

    public void updateEmail(View view) throws SQLException {
        User user;

        newEmail = findViewById(R.id.updateEmailEdtxt1);
        newEmailConfirm = findViewById(R.id.updateEmailEdtxt2);

        String inputNewEmail = newEmail.getText().toString();
        String inputNewEmailConfirm = newEmailConfirm.getText().toString();

        if (inputNewEmail.isEmpty() || inputNewEmailConfirm.isEmpty()) {
            Toast.makeText(AccountUpdateEmail.this, "Too empty buddy, try again!", Toast.LENGTH_SHORT).show();
        } else {
            validate = validate(inputNewEmail, inputNewEmailConfirm);
            if (validate) {
                if (database.updateEmail(inputNewEmail)) {
                    Toast.makeText(AccountUpdateEmail.this, "Email changes!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AccountUpdateEmail.this, AccountManagementMenu.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "Update Email is not working!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AccountUpdateEmail.this, "Please try again!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private boolean validate(String newEmail, String newEmailConfirm) {
        boolean result = false;
        if (newEmail.contains("@myumanitoba.ca") && newEmail.equals(newEmailConfirm)) {
            result = true;
        }
        return result;
    }
}