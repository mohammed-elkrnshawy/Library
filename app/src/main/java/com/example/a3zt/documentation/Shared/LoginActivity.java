package com.example.a3zt.documentation.Shared;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.a3zt.documentation.Classes.User;
import com.example.a3zt.documentation.Professor.ProfHomeActivity;
import com.example.a3zt.documentation.R;
import com.example.a3zt.documentation.Student.StudentHomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import in.anshul.libray.PasswordEditText;

public class LoginActivity extends AppCompatActivity {

    private User userLogin;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;
    private TextInputLayout textInputLayoutEmail, textInputLayoutPassword;
    private String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private EditText editTextEmail;
    private PasswordEditText editTextPassword;
    private Button buttonLogin,buttonRegister;
    private LinearLayout Container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        InitComponent();



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
                    SignIn();
                }
            }
        });

    }





    private void InitComponent() {
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setMessage("Please wait...");
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
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




    private void SignIn()
    {
        mAuth.signInWithEmailAndPassword(editTextEmail.getText()+"", editTextPassword.getText()+"")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("AAA", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user.isEmailVerified())
                            {
                                Search(user);
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Pls Valid Email", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            dialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Log.w("", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed."+"\n"+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }



    private void Search(final FirebaseUser user)
    {
        Query query=reference.child("Users");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    for (DataSnapshot info : dataSnapshot.getChildren()) {
                        if (info.getKey().equals(user.getUid())) {
                            userLogin=new User(
                              info.child("id").getValue()+"",
                              info.child("username").getValue()+"",
                              info.child("password").getValue()+"",
                              info.child("email").getValue()+"",
                              Integer.parseInt(info.child("type").getValue()+"")
                            );
                            userLogin.setUuid(user.getUid());
                        }
                    }
                }

                if(userLogin.getID()!=null)
                {
                    SaveSharedPreference(userLogin);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }







    private void SaveSharedPreference(User user)
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor prefsEditor = sharedPref.edit();
        prefsEditor.putBoolean("isLogin",true);
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString("userObject", json);
        prefsEditor.commit();
        OpenHome();
    }

    private void OpenHome()
    {
        if(userLogin.getType()==1)
        {
            startActivity(new Intent(this, ProfHomeActivity.class));
        }
        else if(userLogin.getType()==0)
        {
            startActivity(new Intent(this, StudentHomeActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
