package com.example.generalapplication.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.internal.NavigationMenu;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.generalapplication.Adapters.OrderAdapter;
import com.example.generalapplication.Classes.PrinterStatus;
import com.example.generalapplication.R;

import io.github.yavski.fabspeeddial.FabSpeedDial;

import static com.example.generalapplication.APIHelper.Internal.GetAllOrdersByBarcodes;
import static com.example.generalapplication.APIHelper.Internal.ProcessOrders;
import static com.example.generalapplication.Adapters.OrderAdapter.multiSelectedOrders;
import static com.example.generalapplication.Helpers.Core.GetLocationNames;
import static com.example.generalapplication.Helpers.Core.GetPreferredLocationUUIDfromName;
import static com.example.generalapplication.Helpers.Core.ReadPreference;
import static com.example.generalapplication.Helpers.Core.WritePreference;
import static com.example.generalapplication.Helpers.UI.CreateBasicSnack;
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

                        String preferredLocation = ReadPreference(context, context.getString(R.string.preference_ordersViewLocation));

                        LayoutInflater li = LayoutInflater.from(context);
                        View promptsView = li.inflate(R.layout.dialog_filter_orders, null);

                        final Spinner mySpinner = promptsView.findViewById(R.id.spinner);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(classContext,android.R.layout.simple_dropdown_item_1line,GetLocationNames(classContext));
                        mySpinner.setAdapter(adapter);

                        mySpinner.setSelection(preferredLocation == null ? 0 : GetLocationNames(classContext).indexOf(preferredLocation));

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setView(promptsView);
                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("SAVE",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                WritePreference(context, context.getString(R.string.preference_ordersViewLocation), mySpinner.getSelectedItem().toString());
                                                GetAllOrdersByBarcodes(classContext, true, false);
                                            }
                                        })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                dialog.cancel();
                                            }
                                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        break;

                    case R.id.orderOptionsnProcess:
                        ProcessOrders(classContext, multiSelectedOrders, GetPreferredLocationUUIDfromName(classContext));
                        break;
                    case R.id.orderOptionsnPrint:
                        if(multiSelectedOrders.size() > 0){
                            Intent printIntent = new Intent(classContext, PrintActivity.class);
                            classContext.startActivity(printIntent);
                        }else {
                            CreateBasicSnack("Please select at least one orders.", null, context);
                        }
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
