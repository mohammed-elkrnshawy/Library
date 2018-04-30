package com.example.a3zt.documentation.Shared;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.a3zt.documentation.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                ReadDataFromSharedPreference();
            }
        }.start();

    }


    private void ReadDataFromSharedPreference()
    {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
        /*SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean isLogin=sharedPref.getBoolean("isLogin",false);
        if (isLogin)
        {
            Gson gson = new Gson();
            String json = sharedPref.getString("userObject","");
            User obj = gson.fromJson(json, User.class);
            if (obj.getType()==1)
            {
                Intent TutorIntent=new Intent(this,TutorHomeActivity.class);
                TutorIntent.putExtra("TutorObject",obj);
                startActivity(TutorIntent);
                finish();
            }
            else {
                Intent StudentIntent=new Intent(this,StudentHomeActivity.class);
                StudentIntent.putExtra("StudentObject",obj);
                startActivity(StudentIntent);
                finish();
            }
        }
        else {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }*/
    }

    @Override
    public void onBackPressed() {
    }

}
