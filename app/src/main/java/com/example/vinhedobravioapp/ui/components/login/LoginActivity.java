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
import com.example.vinhedobravioapp.ui.components.inicial.MenuActivity;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        boolean manterLogado = prefs.getBoolean("manterLogado", false);

        if (manterLogado) {
            Intent intent = new Intent(this, PainelAdmActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.login);

        TextView tituloLogin = findViewById(R.id.login);
        TextView esqueceuSenha = findViewById(R.id.esqueceuSenha);
        CustomButtonComponent btnLogin = findViewById(R.id.btnLogin);
        EditText campoSenha = findViewById(R.id.senha);
        EditText campoEmail = findViewById(R.id.email);
        ImageView toggleSenha = findViewById(R.id.iconToggleSenha);
        CheckBox checkboxManterLogado = findViewById(R.id.checkboxManterLogado);

        int tipoUsuario = prefs.getInt("tipoUsuario", getIntent().getIntExtra("TIPO_USUARIO", 0));
        String nomeUsuario;
        final String[] senhas = {"Adm", "Rep"};
        final String[] emails = {"adm@bravio.com", "rep@bravio.com"};
        final boolean[] senhaVisivel = {false};

        if (tipoUsuario == 1) {
            nomeUsuario = "Administrador";
        } else if (tipoUsuario == 2) {
            nomeUsuario = "Representante";
        } else {
            nomeUsuario = "Usuário";
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
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "E-mail inválido", Toast.LENGTH_SHORT).show();
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
            intent.putExtra("TIPO_USUARIO", tipoUsuario);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }

    private void verificaLogin(String emailDigitado, String senhaDigitada, int tipoUsuario, String emailCerto, String senhaCerta, CheckBox checkboxManterLogado) {
        if (emailDigitado.equals(emailCerto) && senhaDigitada.equals(senhaCerta)) {
            if (checkboxManterLogado.isChecked()) {
                SharedPreferences.Editor editor = getSharedPreferences("loginPrefs", MODE_PRIVATE).edit();
                editor.putBoolean("manterLogado", true);
                editor.putString("email", emailDigitado);
                editor.putString("senha", senhaDigitada);
                editor.putInt("tipoUsuario", tipoUsuario);
                editor.apply();
            }

            Intent intent = new Intent(this, PainelAdmActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        } else {
            Toast.makeText(this, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        View dialogView = getLayoutInflater().inflate(R.layout.modal_confirmacao, null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        Button btnYes = dialogView.findViewById(R.id.btnYes);
        Button btnNo = dialogView.findViewById(R.id.btnNo);

        btnYes.setOnClickListener(v -> {
            dialog.dismiss();
            super.onBackPressed();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        btnNo.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }
}

