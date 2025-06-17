package com.example.vinhedobravioapp.ui.components.inicial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.vinhedobravioapp.R;

public class InicialActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_tela_inicial);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }, 2000);
    }
}
