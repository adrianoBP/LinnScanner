package com.example.generalapplication.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.generalapplication.Adapters.OrderAdapter;
import com.example.generalapplication.R;

import io.github.yavski.fabspeeddial.FabSpeedDial;

import static com.example.generalapplication.Helpers.UI.orderAdapter;

public class OrdersActivity extends AppCompatActivity {

    // Class elements
    Context classContext;
    View classView;

    ListView lvOrders;
    FabSpeedDial fabOrderActions;

    public OrdersActivity(final Context context, final View view){

        this.classContext = context;
        this.classView = view;


        // View elements initialization
        lvOrders = classView.findViewById(R.id.lvOrders);
        fabOrderActions = classView.findViewById(R.id.fabOrderActions);


        orderAdapter = new OrderAdapter(classContext);
        lvOrders.setAdapter(orderAdapter);

        fabOrderActions.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.orderOptionsScanner:
                        Intent cameraIntent = new Intent(context, ScannerActivity.class);
                        context.startActivity(cameraIntent);
                        break;
                }
                return true;
            }

            @Override
            public void onMenuClosed() {

            }
        });
    }
}
