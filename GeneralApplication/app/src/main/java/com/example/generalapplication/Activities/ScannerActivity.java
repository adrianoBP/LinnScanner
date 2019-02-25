package com.example.generalapplication.Activities;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.generalapplication.R;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.example.generalapplication.APIHelper.Internal.GetAllOrdersByBarcodes;
import static com.example.generalapplication.Helpers.UI.CreateBasicSnack;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    public static List<String> scannedBarcodes;

    TextView tvOrderCounter;
    FloatingActionButton fabFlash, fabScanComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        ViewGroup contentFrame = findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);

        scannedBarcodes = new ArrayList<>();

        tvOrderCounter = findViewById(R.id.tvOrdersCounter);
        fabFlash = findViewById(R.id.fabToggleFlash);
        fabScanComplete = findViewById(R.id.fabScanComplete);

        // Listeners
        fabFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScannerView.setFlash(!mScannerView.getFlash());
            }
        });

        fabScanComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetAllOrdersByBarcodes(ScannerActivity.this, scannedBarcodes);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {

        String result = rawResult.getText();
        String format = rawResult.getBarcodeFormat().toString();

        if(!scannedBarcodes.contains(result)){
            scannedBarcodes.add(result);
            tvOrderCounter.setText(String.valueOf(scannedBarcodes.size()));
            Log.i("SCNR.HANDLE.RESULT", result);
            Log.i("SCNR.HANDLE.FORMAT", format);
            CreateBasicSnack("Barcode successfully scanned!", null, this);
        }else{
            CreateBasicSnack("Barcode already scanned!", null, this);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(ScannerActivity.this);
            }
        }, 2000);
    }
}
