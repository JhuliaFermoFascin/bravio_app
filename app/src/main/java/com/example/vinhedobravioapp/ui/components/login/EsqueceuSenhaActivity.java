package com.example.vinhedobravioapp.ui.components.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.components.CustomButtonComponent;
import com.example.vinhedobravioapp.ui.components.inicial.MenuActivity;

public class EsqueceuSenhaActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.esqueceu_senha);

        CustomButtonComponent btnVoltar = findViewById(R.id.btnRetornar);

        int tipoUsuario = getIntent().getIntExtra("TIPO_USUARIO", 0);

        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("TIPO_USUARIO", tipoUsuario);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }
}
