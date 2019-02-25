package com.example.generalapplication.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.generalapplication.R;

import static com.example.generalapplication.APIHelper.Internal.RetrieveUserId;
import static com.example.generalapplication.Helpers.UI.CreateBasicSnack;
import static com.example.generalapplication.Helpers.Core.CheckLoginSaved;
import static com.example.generalapplication.Helpers.Core.HideKeyboard;
import static com.example.generalapplication.Helpers.Core.IsNullOrEmpty;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button bLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Items init
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        bLogin = findViewById(R.id.bLogin);

        // Listeners
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!IsNullOrEmpty(etEmail.getText().toString()) && !IsNullOrEmpty(etPassword.getText().toString())){
                    HideKeyboard(getCurrentFocus(), LoginActivity.this);
                    // TODO: start loading animation.
                    RetrieveUserId(etEmail.getText().toString(), etPassword.getText().toString(), LoginActivity.this);
                }
                else{
                    CreateBasicSnack("Missing fields.", null, LoginActivity.this);
                }
            }
        });

        // CoreInit
        CheckLoginSaved(this);
        // TODO: check behaviour.

    }
}
