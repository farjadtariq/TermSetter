package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AccountManagementMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management_menu);
    }

    public void openManageProfile(View view)
    {
        Intent intent = new Intent(this, AccountManageProfile.class);
        startActivity(intent);
    }

    public void openUpdateEmail(View view)
    {
        Intent intent = getIntent();
        Database database = (Database)intent.getSerializableExtra("database");
        Intent intentI = new Intent(this, AccountUpdateEmail.class);
        intentI.putExtra("database", database);
        startActivity(intentI);
    }

    public void openChangePassword(View view)
    {
        Intent intent = getIntent();
        Database database = (Database)intent.getSerializableExtra("database");
        Intent intentI = new Intent(this, AccountChangePassword.class);
        intentI.putExtra("database", database);
        startActivity(intentI);
    }

    public void openSettings(View view)
    {
        Intent intent = new Intent(this, AccountSettings.class);
        startActivity(intent);
    }
}