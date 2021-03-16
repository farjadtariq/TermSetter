package comp3350.termsetter.Presentation;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.termsetter.R;

import comp3350.termsetter.Persistence.DomainSpecific.Database;
import comp3350.termsetter.Persistence.DomainSpecific.User;

public class AccountUpdateEmail extends AppCompatActivity {
    boolean validate;
    private EditText newEmail;
    private EditText newEmailConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_update_email);
    }

    public void updateEmail(View view) {
        Intent intent = getIntent();
        Database database = (Database) intent.getSerializableExtra("database");
        User user;

        newEmail = findViewById(R.id.update_email_input1);
        newEmailConfirm = findViewById(R.id.update_email_input2);

        String inputNewEmail = newEmail.getText().toString();
        String inputNewEmailConfirm = newEmailConfirm.getText().toString();

        if (inputNewEmail.isEmpty() || inputNewEmailConfirm.isEmpty()) {
            Toast.makeText(AccountUpdateEmail.this, "Too empty buddy, try again!", Toast.LENGTH_SHORT).show();
        } else {
            validate = validate(inputNewEmail, inputNewEmailConfirm);
            if (validate) {
                user = database.getUser();
                user.setEmail(inputNewEmail);
                //database.updateUser(user);
                Toast.makeText(AccountUpdateEmail.this, "Email changes!", Toast.LENGTH_SHORT).show();
                Intent intentI = new Intent(AccountUpdateEmail.this, AccountManagementMenu.class);
                intentI.putExtra("database", database);
                startActivity(intentI);
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