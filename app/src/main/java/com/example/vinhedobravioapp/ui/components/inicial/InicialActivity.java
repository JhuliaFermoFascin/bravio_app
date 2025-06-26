package com.example.vinhedobravioapp.ui.components.inicial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.database.CreateDefaults;
import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.loginManager.LoginManager;

import com.example.vinhedobravioapp.ui.components.utils.LoginStatus;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
//import com.example.vinhedobravioapp.database.dao.DaoCrudTester;

public class InicialActivity extends AppCompatActivity {

    private static final int DELAY_MS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_tela_inicial);
        
        DPOpenHelper db = new DPOpenHelper(this);
        CreateDefaults.Start(this);

        new Handler().postDelayed(() -> {
            Intent intent = LoginManager.getInstance().getNextActivity(this);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }, DELAY_MS);

    }

}
