package com.example.a3zt.documentation.Shared.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a3zt.documentation.Classes.User;
import com.example.a3zt.documentation.R;
import com.example.a3zt.documentation.Shared.LoginActivity;
import com.example.a3zt.documentation.Student.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class StudentFragment extends Fragment {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private User Student;
    private View view;
    private EditText editTextID,editTextUsername,editTextPassword,editTextEmail;
    Button buttonRegister;

    public StudentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_student, container, false);
        InitComponent();





        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentRegister();
            }
        });









        return view;
    }


    private void InitComponent()
    {
        buttonRegister=view.findViewById(R.id.button_Register);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        editTextID=view.findViewById(R.id.editText_ID);
        editTextUsername=view.findViewById(R.id.editText_Username);
        editTextPassword=view.findViewById(R.id.editText_Password);
        editTextEmail=view.findViewById(R.id.editText_Email);
    }


    private void Verification(final FirebaseUser user)
    {
        mAuth = FirebaseAuth.getInstance();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "Email sent.");
                        }
                    }
                });
    }

    private void CreateStudent()
    {
        Student=new User(editTextID.getText()+"",editTextUsername.getText()+""
                ,editTextPassword.getText()+"",editTextEmail.getText()+"");
    }

    private void StudentRegister()
    {
        CreateStudent();
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(Student.getEmail(), Student.getPassword())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("G", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Verification(user);
                            mDatabase.child("Student").child(user.getUid()).setValue(Student);
                            Student.setUuid(user.getUid());
                            OpenHome();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("G", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            /*updateUI(null);*/
                        }

                        // ...
                    }
                });
    }

    private void OpenHome()
    {
        startActivity(new Intent(getActivity(),LoginActivity.class));
        getActivity().finish();
    }
  /*  private void SaveSharedPreference(User user)
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor prefsEditor = sharedPref.edit();
        prefsEditor.putBoolean("isLogin",true);
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString("userObject", json);
        prefsEditor.commit();
    }*/
}
