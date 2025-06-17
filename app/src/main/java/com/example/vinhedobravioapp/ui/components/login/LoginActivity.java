package com.example.vinhedobravioapp.ui.components.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.components.CustomButtonComponent;
import com.example.vinhedobravioapp.ui.components.helper.ConfirmacaoHelper;
import com.example.vinhedobravioapp.ui.components.inicial.DashboardAdmActivity;
import com.example.vinhedobravioapp.ui.components.inicial.MenuActivity;
import com.example.vinhedobravioapp.ui.components.inicial.PainelRepresentanteActivity;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(getString(R.string.preferencia_login), MODE_PRIVATE);
        boolean manterLogado = prefs.getBoolean(getString(R.string.manter_logado_shared), false);
        int tipoUsuario = prefs.getInt(getString(R.string.tipo_usuario_shared), getIntent().getIntExtra(getString(R.string.tipo_usuario_input), 0));
        String nomeUsuario;

        if (manterLogado) {
            Intent intent;
            if (tipoUsuario == 1) {
                intent = new Intent(this, DashboardAdmActivity.class);
            } else {
                intent = new Intent(this, PainelRepresentanteActivity.class);
            }
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }

        setContentView(R.layout.home_login);

        TextView tituloLogin = findViewById(R.id.login);
        TextView esqueceuSenha = findViewById(R.id.esqueceuSenha);
        CustomButtonComponent btnLogin = findViewById(R.id.btnLogin);
        CustomButtonComponent btnVoltar = findViewById(R.id.btnVoltar);
        EditText campoSenha = findViewById(R.id.senha);
        EditText campoEmail = findViewById(R.id.email);
        ImageView toggleSenha = findViewById(R.id.iconToggleSenha);
        CheckBox checkboxManterLogado = findViewById(R.id.checkboxManterLogado);

        String emailPreenchido = getIntent().getStringExtra(getString(R.string.email_input));
        String senhaPreenchida = getIntent().getStringExtra(getString(R.string.senha_input));
        if (emailPreenchido != null) campoEmail.setText(emailPreenchido);
        if (senhaPreenchida != null) campoSenha.setText(senhaPreenchida);

        final String[] senhas = {getString(R.string.senha_adm), getString(R.string.senha_rep)};
        final String[] emails = {getString(R.string.email_adm), getString(R.string.email_rep)};
        final boolean[] senhaVisivel = {false};

        if (tipoUsuario == 1) {
            nomeUsuario = getString(R.string.titulo_adm);
        } else {
            nomeUsuario = getString(R.string.titulo_rep);
        }

        toggleSenha.setOnClickListener(v -> {
            if (senhaVisivel[0]) {
                campoSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                toggleSenha.setImageResource(R.drawable.icon_eye_close);
            } else {
                campoSenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                toggleSenha.setImageResource(R.drawable.icon_eye_open);
            }
            campoSenha.setSelection(campoSenha.getText().length());
            senhaVisivel[0] = !senhaVisivel[0];
        });

        String textoFinal = getString(R.string.login_geral, nomeUsuario);
        tituloLogin.setText(textoFinal);

        btnLogin.setOnClickListener(v -> {
            String senha = campoSenha.getText().toString().trim();
            String email = campoEmail.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, getString(R.string.campos_vazios), Toast.LENGTH_SHORT).show();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, getString(R.string.email_senha_incorreto), Toast.LENGTH_SHORT).show();
                return;
            }

            if(tipoUsuario == 1){
                verificaLogin(email, senha, tipoUsuario, emails[0], senhas[0], checkboxManterLogado);
            }

            if(tipoUsuario == 2){
                verificaLogin(email, senha, tipoUsuario, emails[1], senhas[1], checkboxManterLogado);
            }

        });

        esqueceuSenha.setOnClickListener(v -> {
            Intent intent = new Intent(this, EsqueceuSenhaActivity.class);
            intent.putExtra(getString(R.string.tipo_usuario_input), tipoUsuario);
            intent.putExtra(getString(R.string.email_input), campoEmail.getText().toString().trim());
            intent.putExtra(getString(R.string.senha_input), campoSenha.getText().toString().trim());
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        btnVoltar.setOnClickListener(v -> {
            mostrarConfirmacaoSaida();
        });
    }

    private void verificaLogin(String emailDigitado, String senhaDigitada, int tipoUsuario, String emailCerto, String senhaCerta, CheckBox checkboxManterLogado) {
        if (emailDigitado.equals(emailCerto) && senhaDigitada.equals(senhaCerta)) {
            if (checkboxManterLogado.isChecked()) {
                SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preferencia_login), MODE_PRIVATE).edit();
                editor.putBoolean(getString(R.string.manter_logado_shared), true);
                editor.putString(getString(R.string.email_shared), emailDigitado);
                editor.putString(getString(R.string.senha_shared), senhaDigitada);
                editor.putInt(getString(R.string.tipo_usuario_shared), tipoUsuario);
                editor.apply();
            }

            Intent intent;
            if (tipoUsuario == 1) {
                intent = new Intent(this, DashboardAdmActivity.class);
            } else {
                intent = new Intent(this, PainelRepresentanteActivity.class);
            }

            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.email_senha_incorreto), Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarConfirmacaoSaida() {
        String mensagem = getString(R.string.pergunta_saida, getString(R.string.confirmar_retorno_menu));

        ConfirmacaoHelper.mostrarConfirmacao(this, mensagem, () -> {
            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preferencia_login), MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        mostrarConfirmacaoSaida();
    }
}