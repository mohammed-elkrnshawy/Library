package com.example.a3zt.documentation.Professor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a3zt.documentation.Classes.ListAdapter;
import com.example.a3zt.documentation.Classes.Project;
import com.example.a3zt.documentation.Classes.User;
import com.example.a3zt.documentation.R;
import com.example.a3zt.documentation.Shared.DisplayActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfHomeActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;


    private DatabaseReference reference;
    private List<Project> projectList=new ArrayList<>();
    ListAdapter listAdapt;
    private User Professor;
    private ImageView menu;
    private ListView listView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_home);
        InitComponent();
        ReturnPDF();


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                ReturnPDF();
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchPDF(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Project project= (Project) parent.getItemAtPosition(position);
                Intent intent=new Intent(ProfHomeActivity.this, DisplayActivity.class);
                intent.putExtra("Object",project);
                intent.putExtra("User",Professor);
                startActivity(intent);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT); //CLOSE Nav Drawer!
                }else{
                    mDrawerLayout.openDrawer(GravityCompat.START,true); //OPEN Nav Drawer!
                }
            }
        });

        //region SlideButtonClick
        mNavigationView.setNavigationItemSelectedListener
                (new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.uploadDocument:
                                Intent ProfileIntent=new Intent(ProfHomeActivity.this,UploadActivity.class);
                                startActivity(ProfileIntent);
                                break;
                        }
                        return true;
                    }
                });
        //endregion

    }

    private void InitComponent() {
        getData();
        menu= (ImageView) findViewById(R.id.action_image);
        listView= (ListView) findViewById(R.id.document_list);
        searchView= (SearchView) findViewById(R.id.serach);
        reference = FirebaseDatabase.getInstance().getReference();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mNavigationView = (NavigationView) findViewById(R.id.fr);
    }
    private void getData()
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPref.getString("userObject","");
        Professor = gson.fromJson(json, User.class);
    }

    private void ReturnPDF()
    {
        Query query=reference.child("Documentation");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                projectList.clear();
                if(dataSnapshot.exists())
                {
                   try {
                       for (DataSnapshot info : dataSnapshot.getChildren()) {
                           if (Professor.getUuid().equals(info.child("doctorUID").getValue()+"")) {
                               projectList.add(new Project(
                                       info.child("doctorUID").getValue() + "",
                                       info.child("title").getValue() + "",
                                       info.child("description").getValue() + "",
                                       info.child("department").getValue() + "",
                                       info.child("year").getValue() + "",
                                       (List<String>) info.child("studentNames").getValue(),
                                       info.child("category").getValue() + "",
                                       (List<String>) info.child("keyWords").getValue(),
                                       info.child("downloadUel").getValue() + "",
                                       info.getKey()+""
                               ));
                           }
                       }

                   }
                   catch (Exception t)
                   {
                       Toast.makeText(ProfHomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                   }
                }

                listAdapt=new ListAdapter(ProfHomeActivity.this,R.layout.document_list_item,projectList);
                listView.setAdapter(listAdapt);




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void SearchPDF(final String s)
    {
        final boolean[] flag = {false};
        Query query=reference.child("Documentation");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                projectList.clear();
                if(dataSnapshot.exists())
                {
                    try {
                        for (DataSnapshot info : dataSnapshot.getChildren()) {
                            if (Professor.getUuid().equals(info.child("doctorUID").getValue()+"")) {
                                List<String> Keys=(List<String>) info.child("keyWords").getValue();
                                for(String key:Keys)
                                {
                                    if(key.toLowerCase().contains(s.toLowerCase()))
                                    {
                                        flag[0] =true;
                                        break;
                                    }
                                }

                                if(flag[0])
                                {
                                    projectList.add(new Project(
                                            info.child("doctorUID").getValue() + "",
                                            info.child("title").getValue() + "",
                                            info.child("description").getValue() + "",
                                            info.child("department").getValue() + "",
                                            info.child("year").getValue() + "",
                                            (List<String>) info.child("studentNames").getValue(),
                                            info.child("category").getValue() + "",
                                            (List<String>) info.child("keyWords").getValue(),
                                            info.child("downloadUel").getValue() + "",
                                            info.getKey()+""
                                    ));
                                }
                            }
                        }

                    }
                    catch (Exception t)
                    {
                        Toast.makeText(ProfHomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                listAdapt=new ListAdapter(ProfHomeActivity.this,R.layout.document_list_item,projectList);
                listView.setAdapter(listAdapt);




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
