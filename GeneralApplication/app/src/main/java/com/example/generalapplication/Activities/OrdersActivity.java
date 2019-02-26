package com.example.generalapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.internal.NavigationMenu;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.generalapplication.Adapters.OrderAdapter;
import com.example.generalapplication.R;

import io.github.yavski.fabspeeddial.FabSpeedDial;

import static com.example.generalapplication.APIHelper.Internal.GetAllOrdersByBarcodes;
import static com.example.generalapplication.Helpers.Core.WritePreference;
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
                Intent cameraIntent = new Intent(context, ScannerActivity.class);

                switch (menuItem.getItemId()){
                    case R.id.orderOptionsnNewScans:
                        cameraIntent.putExtra("SCANNER_MODE", "NEW");
                        context.startActivity(cameraIntent);
                        break;
                    case R.id.orderOptionsnAddScans:
                        cameraIntent.putExtra("SCANNER_MODE", "ADD");
                        context.startActivity(cameraIntent);
                        break;
                    case R.id.orderOptionsnFilters:
                        WritePreference(classContext, context.getString(R.string.preference_ordersViewLocation), "Default");
                        GetAllOrdersByBarcodes(classContext, true, false);
                        break;
                    case R.id.orderOptionsnFilters2:
                        WritePreference(classContext, context.getString(R.string.preference_ordersViewLocation), "3PL");
                        GetAllOrdersByBarcodes(classContext, true, false);
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
