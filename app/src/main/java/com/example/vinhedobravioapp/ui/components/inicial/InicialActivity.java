package com.example.vinhedobravioapp.ui.components.inicial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.FindAnyUsers;
import com.example.vinhedobravioapp.ui.components.visitas.VisitasActivity;

public class InicialActivity extends AppCompatActivity {

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
//        DaoCrudTester.testAllDaos(this);

        new Handler().postDelayed(() -> {
            SharedPreferences prefs = getSharedPreferences(getString(R.string.preferencia_login), MODE_PRIVATE);
            boolean isLoggedIn = prefs.getBoolean(getString(R.string.manter_logado_shared), false);
            int tipoUsuario = prefs.getInt(getString(R.string.tipo_usuario_shared), 0);

            Intent intent;
            if (isLoggedIn) {
                if (tipoUsuario == 1) {
                    intent = new Intent(this, DashboardAdmActivity.class);
                } else {
                    intent = new Intent(this, VisitasActivity.class);
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
