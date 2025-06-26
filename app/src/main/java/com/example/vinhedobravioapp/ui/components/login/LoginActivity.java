package com.example.vinhedobravioapp.ui.components.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.loginManager.LoginManager;
import com.example.vinhedobravioapp.ui.components.helper.CustomButtonHelper;
import com.example.vinhedobravioapp.database.dao.UserDAO;
import com.example.vinhedobravioapp.database.model.UserModel;
import com.example.vinhedobravioapp.ui.components.helper.ConfirmacaoHelper;
import com.example.vinhedobravioapp.ui.components.inicial.MenuActivity;
import com.example.vinhedobravioapp.ui.components.inicial.DashboardAdmActivity;
import com.example.vinhedobravioapp.ui.components.inicial.PainelRepresentanteActivity;
import com.example.vinhedobravioapp.ui.components.utils.LoginStatus;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int tipoUsuario = getIntent().getIntExtra(getString(R.string.tipo_usuario_input), -1);
        SharedPreferences prefs = getSharedPreferences(getString(R.string.preferencia_login), MODE_PRIVATE);
        LoginStatus status = LoginManager.getInstance().getLoginStatus();
        String UserTypeName = tipoUsuario == 1? getString(R.string.administrador) : tipoUsuario == 0? getString(R.string.representante): "erro";

        setContentView(R.layout.home_login);

        TextView tituloLogin = findViewById(R.id.login);
        TextView esqueceuSenha = findViewById(R.id.esqueceuSenha);
        CustomButtonHelper btnLogin = findViewById(R.id.btnLogin);
        CustomButtonHelper btnVoltar = findViewById(R.id.btnVoltar);
        EditText campoSenha = findViewById(R.id.senha);
        EditText campoEmail = findViewById(R.id.email);
        ImageView toggleSenha = findViewById(R.id.iconToggleSenha);
        CheckBox checkboxManterLogado = findViewById(R.id.checkboxManterLogado);

        String emailPreenchido = getIntent().getStringExtra(getString(R.string.email_input));
        String senhaPreenchida = getIntent().getStringExtra(getString(R.string.senha_input));
        if (emailPreenchido != null) campoEmail.setText(emailPreenchido);
        if (senhaPreenchida != null) campoSenha.setText(senhaPreenchida);

        final boolean[] senhaVisivel = {false};

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

        String textoFinal = getString(R.string.login_geral, UserTypeName);
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

            if (tipoUsuario == 1) {
                verificaLogin(tipoUsuario,email, senha, checkboxManterLogado);
            } else {
                verificaLogin(tipoUsuario, email, senha, checkboxManterLogado);
            }

        });

        esqueceuSenha.setOnClickListener(v -> {
            Intent intent = new Intent(this, EsqueceuSenhaActivity.class);
            intent.putExtra(getString(R.string.tipo_usuario_input), UserTypeName);
            intent.putExtra(getString(R.string.email_input), campoEmail.getText().toString().trim());
            intent.putExtra(getString(R.string.senha_input), campoSenha.getText().toString().trim());
            intent.putExtra(getString(R.string.senha_input), campoSenha.getText().toString().trim());
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        btnVoltar.setOnClickListener(v -> {
            mostrarConfirmacaoSaida();
        });
    }
    private void verificaLogin(int isAdmin, String emailDigitado, String senhaDigitada, CheckBox checkboxManterLogado) {
        UserDAO userDAO = new UserDAO(this);
        UserModel user = userDAO.findByEmailAndPassword(emailDigitado, senhaDigitada);
        boolean error = false;
        if (user != null) {

            if (user.getIsAdmin() == isAdmin) {
                String dataAtual = new SimpleDateFormat(getString(R.string.yyyy_mm_dd), Locale.getDefault()).format(new Date());
                LoginStatus status = new LoginStatus(
                        user.getUserId(),
                        user.getName(),
                        dataAtual,
                        user.getIsAdmin() == 1,
                        checkboxManterLogado.isChecked()
                );

                Gson gson = new Gson();
                String json = gson.toJson(status);

                SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preferencia_login), MODE_PRIVATE).edit();
                editor.putString(getString(R.string.login_status), json);
                editor.apply();

                Intent intent;
                if (user.getIsAdmin() == 1) {
                    intent = new Intent(this, DashboardAdmActivity.class);
                } else {
                    intent = new Intent(this, PainelRepresentanteActivity.class);
                }

                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }else {
                error = true;
            }
        } else {
            error =true;
        }
        if (error) {
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
