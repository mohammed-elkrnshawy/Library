package com.example.a3zt.documentation.Shared.Fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3zt.documentation.Classes.User;
import com.example.a3zt.documentation.R;
import com.example.a3zt.documentation.Shared.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

public class ProfessorFragment extends Fragment {
    private Uri CVUri, CertificationUri;
    private final static int PICK_PDF_CODE_CV = 2342;
    private final static int PICK_PDF_CODE_Certification= 1;
    private StorageReference mStorageReference;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private User Professor;
    private View view;
    private EditText editTextID,editTextUsername,editTextPassword,editTextEmail;
    private Button buttonRegister,buttonCertification,buttonCV;
    private TextView textViewCV,textViewCertification;

    public ProfessorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_professor, container, false);



        InitComponent();


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentRegister();
            }
        });

        buttonCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPDF_CV();
            }
        });

        buttonCertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPDF_Certification();
            }
        });

        return view;
    }

    private void InitComponent()
    {
        mStorageReference = FirebaseStorage.getInstance().getReference();
        buttonRegister=view.findViewById(R.id.button_Register);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        editTextID=view.findViewById(R.id.editText_ID);
        editTextUsername=view.findViewById(R.id.editText_Username);
        editTextPassword=view.findViewById(R.id.editText_Password);
        editTextEmail=view.findViewById(R.id.editText_Email);
        buttonCertification=view.findViewById(R.id.button_Certification);
        buttonCV=view.findViewById(R.id.button_CV);
        textViewCV=view.findViewById(R.id.textView_CV);
        textViewCertification=view.findViewById(R.id.textView_Certification);
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
        Professor=new User(editTextID.getText()+"",editTextUsername.getText()+""
                ,editTextPassword.getText()+"",editTextEmail.getText()+"",1);
    }

    private void StudentRegister()
    {
        CreateStudent();
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(Professor.getEmail(), Professor.getPassword())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("G", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Verification(user);
                            try{
                                mDatabase.child("Users").child(user.getUid()).setValue(Professor);
                                Professor.setUuid(user.getUid());
                                uploadFileCV(CVUri);
                            }
                            catch (Exception u)
                            {
                                Log.d("AAAA",u.getMessage());
                            }
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





    private void getPDF_CV() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getActivity().getPackageName()));
            startActivity(intent);
            return;
        }

        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE_CV);
    }

    private void getPDF_Certification() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getActivity().getPackageName()));
            startActivity(intent);
            return;
        }

        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE_Certification);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE_CV && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {

                CVUri =data.getData();

                textViewCV.setText("Done");
                //uploading the file
                //uploadFile(data.getData());
            }else{
                Toast.makeText(getActivity(), "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
        else  if (requestCode == PICK_PDF_CODE_Certification && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                CertificationUri=data.getData();
                textViewCertification.setText("Done");
                //uploading the file
                //uploadFile(data.getData());
            }else{
                Toast.makeText(getActivity(), "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadFileCV(Uri data) {
        final String[] Url = new String[1];
        StorageReference sRef = mStorageReference.child("Uploads/" + System.currentTimeMillis() + ".pdf");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Url[0] =taskSnapshot.getDownloadUrl().toString();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful())
                        {
                             mDatabase.child("Users").child(Professor.getUuid()).child("CV").setValue(Url[0]);
                             uploadFileCertification(CertificationUri);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getActivity().getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    }
                });

    }

    private void uploadFileCertification(Uri data) {
        final String[] Url = new String[1];
        StorageReference sRef = mStorageReference.child("Uploads/" + System.currentTimeMillis() + ".pdf");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Url[0] =taskSnapshot.getDownloadUrl().toString();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful())
                {
                    mDatabase.child("Users").child(Professor.getUuid()).child("Certification").setValue(Url[0]);
                    OpenHome();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getActivity().getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    }
                });

    }
}
