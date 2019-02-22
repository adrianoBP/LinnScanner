package com.example.generalapplication.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.generalapplication.R;

import java.util.Arrays;
import java.util.List;

import static com.example.generalapplication.Helpers.UI.CreateBasicSnack;

public class MainActivity extends AppCompatActivity {

    View vHome;
    LinearLayout llMainLayout;
    List<View> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Views Init
        vHome = getLayoutInflater().inflate(R.layout.layout_home, null);

        views = Arrays.asList(vHome);

        // Items Init
        llMainLayout = findViewById(R.id.llMainLayout);

        // Request permissions
        ActivityCompat.requestPermissions((Activity) this,
                new String[]{Manifest.permission.CAMERA},
                1);

        // Start main view
        if(ContextCompat.checkSelfPermission( this, Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED){
            new Home(this, vHome);
            changeView(vHome);
        }else{
            CreateBasicSnack("Access to the camera is required to use this application.", null, this);
        }

    }

    private void changeView(View view) {  // Delete all views and add specified one
        for (View v : views) {
            llMainLayout.removeView(v);
        }
        try {
            view.setLayoutParams(new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT));
            llMainLayout.addView(view);
        } catch (Exception ex) {
            Log.e("MAIN.CHANGEVIEW", ex.getMessage());
        }
    }
}
