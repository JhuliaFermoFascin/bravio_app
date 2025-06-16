package com.example.vinhedobravioapp.ui.components.inicial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.database.DPOpenHelper;

public class InicialActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_inicial);


        // Redireciona para a próxima Activity (painel, login, etc)
        Intent intent = new Intent(InicialActivity.this, BemVindoActivity.class);
        startActivity(intent);
        finish(); // Fecha a tela inicial
    }

}
