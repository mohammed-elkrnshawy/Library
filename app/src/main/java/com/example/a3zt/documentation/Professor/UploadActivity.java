package com.example.a3zt.documentation.Professor;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3zt.documentation.Classes.Project;
import com.example.a3zt.documentation.Classes.User;
import com.example.a3zt.documentation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;

public class UploadActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseDatabase firebasedatabase;
    private Project Documentation;
    private StorageReference mStorageReference;
    private Uri PDFUri;
    private final static int PICK_PDF_CODE= 1;
    private EditText editTextTitle,editTextDescription,editTextDepartment,editTextYear,editTextCategory,editTextKey;
    private Button buttonAddKey,buttonPDF,buttonFinish;
    private TagContainerLayout mTagContainerLayout;
    private User Professor;
    private List<String> items=new ArrayList<>();
    private List<String> students=new ArrayList<>();

    private TextView textViewPDF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        InitComponent();

        buttonAddKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.add(editTextKey.getText()+"");
                 mTagContainerLayout.setTags(items);
                 editTextKey.setText("");
            }
        });

        buttonPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPDF_Documentation();
            }
        });

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFileCertification(PDFUri);
            }
        });

    }


    private void InitComponent()
    {
        getData();

        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mTagContainerLayout = (TagContainerLayout) findViewById(R.id.tagcontainerLayout);

        textViewPDF= (TextView) findViewById(R.id.textView_PDF);
        editTextTitle= (EditText) findViewById(R.id.editText_Title);
        editTextDescription= (EditText) findViewById(R.id.editText_Description);
        editTextDepartment= (EditText) findViewById(R.id.editText_Department);
        editTextYear= (EditText) findViewById(R.id.editText_Year);
        editTextCategory= (EditText) findViewById(R.id.editText_Category);
        editTextKey=(EditText)findViewById(R.id.editText_Key);

        buttonPDF= (Button) findViewById(R.id.button_PDF);
        buttonAddKey= (Button) findViewById(R.id.add_Key);
        buttonFinish= (Button) findViewById(R.id.button_Finish);

    }

    private void getData()
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPref.getString("userObject","");
        Professor = gson.fromJson(json, User.class);
        Toast.makeText(this, Professor.getEmail()   , Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE&& resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {

                PDFUri =data.getData();

                textViewPDF.setText("Done");
                //uploading the file
                //uploadFile(data.getData());
            }else{
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void getPDF_Documentation() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            return;
        }

        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE);
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
                           CreateDocumentation(Url[0]);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
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


    private void CreateDocumentation(String s)
    {
        students.add("l");
        Documentation=new Project(Professor.getUuid()+"",editTextTitle.getText()+"",editTextDescription.getText()+"",editTextDepartment.getText()+"",
                editTextYear.getText()+"",students,editTextCategory.getText()+"",items,s);


        firebasedatabase= FirebaseDatabase.getInstance();
        mDatabase = firebasedatabase.getReferenceFromUrl
                ("https://library-7661b.firebaseio.com/Documentation/");
        DatabaseReference id=mDatabase.push();
        id.setValue(Documentation);

    }




}
