package com.example.a3zt.documentation.Professor;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.a3zt.documentation.Classes.User;
import com.example.a3zt.documentation.R;
import com.google.gson.Gson;

public class UploadActivity extends AppCompatActivity {

    private User Professor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        InitComponent();
    }








    private void InitComponent()
    {
        getData();
    }

    private void getData()
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPref.getString("userObject","");
        Professor = gson.fromJson(json, User.class);
        Toast.makeText(this, Professor.getEmail()   , Toast.LENGTH_SHORT).show();
    }


}
