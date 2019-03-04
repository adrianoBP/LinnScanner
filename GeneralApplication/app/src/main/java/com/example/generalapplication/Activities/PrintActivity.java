package com.example.generalapplication.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.generalapplication.Classes.TemplateHeader;
import com.example.generalapplication.Classes.VirtualPrinter;
import com.example.generalapplication.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.example.generalapplication.APIHelper.Internal.GetVirtualPrinters;
import static com.example.generalapplication.Adapters.OrderAdapter.multiSelectedOrders;
import static com.example.generalapplication.Helpers.UI.UpdateTemplateNames;
import static com.example.generalapplication.Helpers.UI.UpdateTemplateTypes;

public class PrintActivity extends AppCompatActivity {

    public static Spinner sPrinters, sTemplateType, sTemplateId;
    TextView tvOrdersToPrint;
    public static Map<UUID, TemplateHeader> allTemplates = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        sPrinters = findViewById(R.id.sPrinters);
        sTemplateType = findViewById(R.id.sTemplateTypes);
        sTemplateId = findViewById(R.id.sTemplateId);
        tvOrdersToPrint = findViewById(R.id.tvNOrdersToPrint);

        tvOrdersToPrint.setText(multiSelectedOrders.size() + "");

        GetVirtualPrinters(this);

        UpdateTemplateTypes(this);

        sTemplateType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UpdateTemplateNames(PrintActivity.this, sTemplateType.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // TODO: add selection of printing type

    }
}
