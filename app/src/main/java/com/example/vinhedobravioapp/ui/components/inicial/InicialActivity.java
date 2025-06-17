package com.example.vinhedobravioapp.ui.components.inicial;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.vinhedobravioapp.R;

public class InicialActivity extends Activity {

    private static final int DELAY_MS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_tela_inicial);

        new Handler().postDelayed(() -> {
            SharedPreferences prefs = getSharedPreferences(getString(R.string.preferencia_login), MODE_PRIVATE);
            boolean isLoggedIn = prefs.getBoolean(getString(R.string.manter_logado_shared), false);

            Intent intent;
            if (isLoggedIn) {
                intent = new Intent(this, PainelRepresentanteActivity.class);
            } else {
                intent = new Intent(this, MenuActivity.class);
            }

            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }, DELAY_MS);
    }
}
