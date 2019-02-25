package com.example.generalapplication.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.generalapplication.Adapters.OrderAdapter;
import com.example.generalapplication.R;

import static com.example.generalapplication.Helpers.UI.orderAdapter;

public class OrdersActivity extends AppCompatActivity {

    // Class elements
    Context classContext;
    View classView;

    // View elements declaration
    ListView lvOrders;
    FloatingActionButton fabAddOrders;

    public OrdersActivity(final Context context, final View view){

        this.classContext = context;
        this.classView = view;


        // View elements initialization
        lvOrders = classView.findViewById(R.id.lvOrders);
        fabAddOrders = view.findViewById(R.id.fabAddOrders);

        orderAdapter = new OrderAdapter(classContext);
        lvOrders.setAdapter(orderAdapter);

        fabAddOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cameraIntent = new Intent(context, ScannerActivity.class);
                context.startActivity(cameraIntent);

            }
        });

//        GetOpenOrders(classContext);
    }
}
