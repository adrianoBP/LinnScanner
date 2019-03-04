package com.example.generalapplication.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.generalapplication.R;

import java.util.Arrays;
import java.util.List;

import static com.example.generalapplication.Helpers.Core.CoreInit;
import static com.example.generalapplication.Helpers.UI.ChangeView;
import static com.example.generalapplication.Helpers.UI.CreateBasicSnack;

public class MainActivity extends AppCompatActivity {

    // TODO: add settings: 1) print regardless printed status

    View vHome, vOrders;
    public  static LinearLayout llMainLayout;
    public static List<View> views;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CoreInit(this);

        // Views CoreInit
        vHome = getLayoutInflater().inflate(R.layout.layout_home, null);
        vOrders = getLayoutInflater().inflate(R.layout.layout_orders, null);

        views = Arrays.asList(vHome, vOrders);

        // Items CoreInit
        llMainLayout = findViewById(R.id.llMainLayout);

        // Request permissions
        ActivityCompat.requestPermissions((Activity) this,
                new String[]{Manifest.permission.CAMERA},
                1);

        // Start main view
        if(ContextCompat.checkSelfPermission( this, Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED){
            new OrdersActivity(this, vOrders);
            ChangeView(vOrders);
        }else{
            CreateBasicSnack("Access to the camera is required to use this application.", null, this);
        }
    }




}
