package com.example.generalapplication.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import com.example.generalapplication.Classes.VirtualPrinter;
import com.example.generalapplication.R;

import java.util.List;

import static com.example.generalapplication.APIHelper.Internal.GetVirtualPrinters;

public class PrintActivity extends AppCompatActivity {

    public static Spinner sPrinters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        sPrinters = findViewById(R.id.sPrinters);

        GetVirtualPrinters(this);

        // TODO: add selection of printing type


    }
}
