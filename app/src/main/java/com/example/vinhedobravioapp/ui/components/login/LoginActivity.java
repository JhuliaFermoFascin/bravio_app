package com.example.vinhedobravioapp.ui.components.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.components.CustomButtonComponent;
import com.example.vinhedobravioapp.ui.components.inicial.MenuActivity;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        TextView tituloLogin = findViewById(R.id.loginAdm);
        CustomButtonComponent btnVoltar = findViewById(R.id.btnRetornar);
        CustomButtonComponent btnLogin = findViewById(R.id.btnLogin);
        TextView esqueceuSenha = findViewById(R.id.esqueceuSenha);

        int tipoUsuario = getIntent().getIntExtra("TIPO_USUARIO", 0);
        String nomeUsuario;

        switch (tipoUsuario) {
            case 1:
                nomeUsuario = "Administrador";
                break;
            case 2:
                nomeUsuario = "Representante";
                break;
            case 3:
                nomeUsuario = "Visitante";
                break;
            default:
                nomeUsuario = "Usuário";
                break;
        }

        String textoFinal = getString(R.string.login_geral, nomeUsuario);
        tituloLogin.setText(textoFinal);

        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        });
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, PainelAdmActivity.class);
            startActivity(intent);
        });
        //esqueceuSenha.setOnClickListener(v -> {
            //Intent intent = new Intent(this, RecuperarSenhaActivity.class); // ou outra activity
            //startActivity(intent);
        //});
    }
}
