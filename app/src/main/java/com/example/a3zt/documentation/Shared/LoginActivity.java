package com.example.a3zt.documentation.Shared;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.a3zt.documentation.R;
import com.google.gson.Gson;

import in.anshul.libray.PasswordEditText;

public class LoginActivity extends AppCompatActivity {


    private TextInputLayout textInputLayoutEmail, textInputLayoutPassword;
    private String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private EditText editTextEmail;
    private PasswordEditText editTextPassword;
    private Button buttonLogin,buttonRegister;
    private LinearLayout Container;
    private AnimationDrawable anim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        InitComponent();

        setAnimation();


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValidEmail()&&ValidPassword()) {

                }
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (anim != null && !anim.isRunning())
            anim.start();
    }
    private void setAnimation() {
        anim = (AnimationDrawable) Container.getBackground();
        anim.setEnterFadeDuration(6000);
        anim.setExitFadeDuration(2000);
    }


    private void InitComponent() {

        editTextEmail= (EditText) findViewById(R.id.editText_email);
        editTextPassword= (PasswordEditText) findViewById(R.id.editText_password);
        buttonLogin= (Button) findViewById(R.id.button_Login);
        buttonRegister= (Button) findViewById(R.id.button_Register);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.TIL_Email);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.TIL_Password);
        Container= (LinearLayout) findViewById(R.id.Container);
    }


    private boolean ValidEmail()
    {
        boolean isValid=true;
        if(editTextEmail.getText().toString().isEmpty())
        {
            textInputLayoutEmail.setError("This field is required");
            isValid=false;
        }
        else if ((!editTextEmail.getText().toString().trim().matches(emailPattern))&&isValid)
        {
            textInputLayoutEmail.setError("This email address is invalid");
            isValid=false;
        }
        else {
            textInputLayoutEmail.setErrorEnabled(false);
            isValid=true;
        }
        return isValid;
    }
    private boolean ValidPassword()
    {
        boolean isValid;
        if(editTextPassword.getText().toString().length()<5)
        {
            textInputLayoutPassword.setError("This field must more than 5 ");
            isValid=false;
        }

        else {
            textInputLayoutPassword.setErrorEnabled(false);
            isValid=true;
        }
        return isValid;
    }




    /*private void SaveSharedPreference(User user)
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor prefsEditor = sharedPref.edit();
        prefsEditor.putBoolean("isLogin",true);
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString("userObject", json);
        prefsEditor.commit();
    }*/


    @Override
    public void onBackPressed() {
        finish();
    }

}
