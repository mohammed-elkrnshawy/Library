package com.example.a3zt.documentation.Shared;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3zt.documentation.Classes.Comment;
import com.example.a3zt.documentation.Classes.CommentAdapter;
import com.example.a3zt.documentation.Classes.Project;
import com.example.a3zt.documentation.Classes.User;
import com.example.a3zt.documentation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {
    private DatabaseReference reference;
    private User user;
    private DatabaseReference mDatabase;
    private FirebaseDatabase firebasedatabase;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList=new ArrayList<>();
    Comment comment;
    private Project project;
    private TextView textViewTitle,textViewCategorytv,textViewYear,textViewDepartmenttv;
    private ImageView imageViewDown,imageViewShare;
    private EditText editTextWritrComment;
    private ListView listViewcomments_list;
    private Button buttonAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        InitComponent();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddComment(editTextWritrComment.getText().toString());
            }
        });

        getComments();
    }

    private void AddComment(String s) {

        comment=new Comment(user.getUsername(),s);


        firebasedatabase= FirebaseDatabase.getInstance();
        mDatabase = firebasedatabase.getReferenceFromUrl
                ("https://library-7661b.firebaseio.com/Comment/'"+project.getUID()+"'");
        DatabaseReference id=mDatabase.push();
        id.setValue(comment);
    }

    private void InitComponent() {
        reference = FirebaseDatabase.getInstance().getReference();


        textViewTitle= (TextView) findViewById(R.id.titletv);
        textViewCategorytv= (TextView) findViewById(R.id.categorytv);
        textViewYear= (TextView) findViewById(R.id.yeartv);
        textViewDepartmenttv= (TextView) findViewById(R.id.departmenttv);

        imageViewDown= (ImageView) findViewById(R.id.downloadfile);
        imageViewShare= (ImageView) findViewById(R.id.sharefile);

        editTextWritrComment= (EditText) findViewById(R.id.reviewet);

        listViewcomments_list= (ListView) findViewById(R.id.comments_list);

        buttonAdd= (Button) findViewById(R.id.add);

        getData();
    }

    private void getData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            project = (Project) extras.getSerializable("Object");
            user = (User) extras.getSerializable("User");

            textViewTitle.setText(project.getTitle());
            textViewCategorytv.setText(project.getCategory());
            textViewYear.setText(project.getYear());
            textViewDepartmenttv.setText(project.getDepartment());

        }

    }

    private void getComments()
    {

        Query query=reference.child("Comment");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        if(project.getUID().equals(snapshot.getKey().replace("'","")))
                        {
                            for(DataSnapshot issue:snapshot.getChildren())
                            {
                                commentList.add(new Comment(
                                   ""+issue.child("name").getValue(),
                                   ""+issue.child("comment").getValue()
                                ));
                            }
                        }
                    }
                }


                commentAdapter=new CommentAdapter(DisplayActivity.this,R.layout.comments_list_item,commentList);
                listViewcomments_list.setAdapter(commentAdapter);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
