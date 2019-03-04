package com.example.generalapplication.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.generalapplication.Classes.VirtualPrinter;
import com.example.generalapplication.R;

import java.util.List;

import static com.example.generalapplication.APIHelper.Internal.GetVirtualPrinters;
import static com.example.generalapplication.Adapters.OrderAdapter.multiSelectedOrders;

public class PrintActivity extends AppCompatActivity {

    public static Spinner sPrinters;
    TextView tvOrdersToPrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        sPrinters = findViewById(R.id.sPrinters);
        tvOrdersToPrint = findViewById(R.id.tvNOrdersToPrint);

        tvOrdersToPrint.setText(multiSelectedOrders.size() + "");

        GetVirtualPrinters(this);

        // TODO: add selection of printing type


    }
}
