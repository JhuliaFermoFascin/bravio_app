package com.example.vinhedobravioapp.ui.components.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.ui.components.helper.CustomButtonHelper;

public class EsqueceuSenhaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.esqueceu_senha);

        CustomButtonHelper btnVoltar = findViewById(R.id.btnRetornar);

        int tipoUsuario = getIntent().getIntExtra(getString(R.string.tipo_usuario_input), 0);

        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(getString(R.string.tipo_usuario_input), tipoUsuario);
            intent.putExtra(getString(R.string.email_input), getIntent().getStringExtra(getString(R.string.email_input)));
            intent.putExtra(getString(R.string.senha_input), getIntent().getStringExtra(getString(R.string.senha_input)));
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }
}
