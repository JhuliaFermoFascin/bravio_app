package com.example.vinhedobravioapp.ui.components.inicial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.ui.components.login.LoginActivity;
import com.example.vinhedobravioapp.ui.components.helper.CustomButtonHelper;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_menu);

        CustomButtonHelper btnAdm = findViewById(R.id.btnEntrarAdm);
        CustomButtonHelper btnRep = findViewById(R.id.btnEntrarRepresentante);
        CustomButtonHelper btnVis = findViewById(R.id.BtnEntrarVisitante);


        btnAdm.setOnClickListener(v -> abrirLogin(1));
        btnRep.setOnClickListener(v -> abrirLogin(2));
        btnVis.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistoriaActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }

    private void abrirLogin(int tipoUsuario) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(getString(R.string.tipo_usuario_input), tipoUsuario);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}