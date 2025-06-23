package com.example.vinhedobravioapp.ui.components.inicial;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.FindAnyUsers;
import com.example.vinhedobravioapp.database.dao.DaoCrudTester;
import com.example.vinhedobravioapp.ui.components.utils.LoginStatus;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class InicialActivity extends Activity {

    private static final int DELAY_MS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_tela_inicial);
        
        DPOpenHelper db = new DPOpenHelper(this);
        FindAnyUsers.ensureDefaultUsers(this);
        FindAnyUsers.ensureDefaultWineTypes(this);
        FindAnyUsers.ensureDefaultGeographicOrigins(this);
        FindAnyUsers.ensureDefaultGrapes(this);
        FindAnyUsers.ensureDefaultTastingNotes(this);
        FindAnyUsers.ensureDefaultWineries(this);
        DaoCrudTester.testAllDaos(this);


        new Handler().postDelayed(() -> {
            SharedPreferences prefs = getSharedPreferences(getString(R.string.preferencia_login), MODE_PRIVATE);
            String json = prefs.getString("login_status", null);

            Intent intent;
            if (json != null) {
                Gson gson = new Gson();
                LoginStatus status = gson.fromJson(json, LoginStatus.class);
                Log.d( "onCreate: ", status.toString());
                boolean manterLogado = status.isManterLogado();
                boolean dentroDoPrazo = false;

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date dataLogin = sdf.parse(status.getDataLogin());
                    Date hoje = new Date();

                    long diffMillis = hoje.getTime() - dataLogin.getTime();
                    long dias = TimeUnit.MILLISECONDS.toDays(diffMillis);

                    dentroDoPrazo = dias <= 7;
                } catch (Exception e) {
                    e.printStackTrace(); // data inválida = não logar
                }

                if (manterLogado && dentroDoPrazo) {
                    if (status.isAdmin()) {
                        intent = new Intent(this, DashboardAdmActivity.class);
                    } else {
                        intent = new Intent(this, PainelRepresentanteActivity.class);
                    }
                } else {
                    intent = new Intent(this, MenuActivity.class);
                }
            } else {
                intent = new Intent(this, MenuActivity.class);
            }

            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }, DELAY_MS);
    }

}
