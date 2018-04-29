package com.example.a3zt.documentation.Shared;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.a3zt.documentation.R;
import com.example.a3zt.documentation.Shared.Fragment.ProfessorFragment;
import com.example.a3zt.documentation.Shared.Fragment.StudentFragment;

public class RegisterActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private LinearLayout linearLayout;
    private RelativeLayout relativeLayoutDoctor,relativeLayoutStudent;
    private FragmentManager fragmentManager ;
    private Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);


        InitComponent();

        relativeLayoutDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
                fragment=new ProfessorFragment();
                  fragmentManager.beginTransaction().replace(R.id.content, fragment)
                .commit();
            }
        });


        relativeLayoutStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
                fragment=new StudentFragment();
                  fragmentManager.beginTransaction().replace(R.id.content, fragment)
                .commit();
            }
        });




    }



    private void InitComponent()
    {
        linearLayout= (LinearLayout) findViewById(R.id.LL_Role);
        fragmentManager = getFragmentManager();
        relativeLayoutDoctor= (RelativeLayout) findViewById(R.id.RL_Doctor);
        relativeLayoutStudent= (RelativeLayout) findViewById(R.id.RL_Student);
        frameLayout= (FrameLayout) findViewById(R.id.content);
    }


    @Override
    public void onBackPressed() {
        if(linearLayout.getVisibility()==View.GONE)
        {
            frameLayout.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }
        else
        super.onBackPressed();
    }
}
