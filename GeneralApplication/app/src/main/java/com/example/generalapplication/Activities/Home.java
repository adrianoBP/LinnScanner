package com.example.generalapplication.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.generalapplication.R;

public class Home extends AppCompatActivity {

    // Class elements
    Context context;
    View view;

    FloatingActionButton fabAddOrders;


    public Home(final Context context, final View view){

        this.context = context;
        this.view = view;

        fabAddOrders = view.findViewById(R.id.fabAddOrders);

        fabAddOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cameraIntent = new Intent(context, ScannerActivity.class);
                ((Activity)context).startActivity(cameraIntent);

            }
        });

    }

}
