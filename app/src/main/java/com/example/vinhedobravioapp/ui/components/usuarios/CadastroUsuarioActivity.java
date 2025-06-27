package com.example.vinhedobravioapp.ui.components.usuarios;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.database.dao.UserDAO;
import com.example.vinhedobravioapp.database.model.UserModel;
import com.example.vinhedobravioapp.loginManager.LoginManager;
import com.example.vinhedobravioapp.ui.components.helper.CustomButtonHelper;
import com.example.vinhedobravioapp.ui.components.helper.HeaderHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText userName, userEmail, userPassword;
    private RadioGroup userType;
    private RadioButton radioAdm, radioRepresentative;
    private Spinner userStatus;
    private CustomButtonHelper cancel_btn, save_btn;

    private UserDAO userDAO;
    private UserModel usuarioAtual;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuarios_cadastrar_usuarios);

        userDAO = new UserDAO(this);

        int isDashboard = LoginManager.getInstance().getLoginStatus().isAdminInt();
        HeaderHelper.configurarHeader(this, getString(R.string.add_user_title), isDashboard);

        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        ImageView toggleSenha = findViewById(R.id.iconToggleSenha);
        final boolean[] senhaVisivel = {false};

        toggleSenha.setOnClickListener(v -> {
            if (senhaVisivel[0]) {
                userPassword.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
                toggleSenha.setImageResource(R.drawable.icon_eye_close);
            } else {
                userPassword.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());
                toggleSenha.setImageResource(R.drawable.icon_eye_open);
            }
            userPassword.setSelection(userPassword.getText().length());
            senhaVisivel[0] = !senhaVisivel[0];
        });

        userType = findViewById(R.id.userType);
        radioAdm = findViewById(R.id.radioAdm);
        radioRepresentative = findViewById(R.id.radioRepresentative);

        int corPrincipal = ContextCompat.getColor(this, R.color.border_button_purple_primary);
        int corSecundaria = ContextCompat.getColor(this, R.color.button_green_terciary);

        int[][] states = new int[][] {
                new int[] { android.R.attr.state_checked },
                new int[] { -android.R.attr.state_checked }
        };

        int[] colors = new int[] {
                corPrincipal,
                corSecundaria
        };

        ColorStateList colorStateList = new ColorStateList(states, colors);

        radioAdm.setButtonTintList(colorStateList);
        radioRepresentative.setButtonTintList(colorStateList);


        userStatus = findViewById(R.id.userStatus);
        save_btn = findViewById(R.id.save_btn);
        cancel_btn = findViewById(R.id.cancel_btn);

        cancel_btn.setOnClickListener(v -> finish());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.status_users,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userStatus.setAdapter(adapter);

        long userId = getIntent().getLongExtra("userId", -1L);
        if (userId != -1L) {
            carregarUsuario(userId);
        }

        save_btn.setOnClickListener(v -> saveUser());
    }

    private void carregarUsuario(long userId) {
        usuarioAtual = userDAO.getById(userId);
        if (usuarioAtual != null) {
            userName.setText(usuarioAtual.getName());
            userEmail.setText(usuarioAtual.getEmail());
            userPassword.setText(usuarioAtual.getPassword());

            if (usuarioAtual.getIsAdmin() == 1) {
                radioAdm.setChecked(true);
            } else {
                radioRepresentative.setChecked(true);
            }

            userStatus.setSelection(usuarioAtual.getStatus() == 1 ? 1 : 0);
        }
    }


    private void saveUser() {
        String nome = userName.getText().toString().trim();
        String email = userEmail.getText().toString().trim();
        String senha = userPassword.getText().toString().trim();
        int tipoUsuario = userType.getCheckedRadioButtonId() == R.id.radioAdm ? 1 : 0;

        if (nome.isEmpty()) {
            userName.setError("Campo obrigatório");
            userName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            userEmail.setError("Campo obrigatório");
            userEmail.requestFocus();
            return;
        }

        if (senha.isEmpty()) {
            userPassword.setError("Campo obrigatório");
            userPassword.requestFocus();
            return;
        }

        String statusSelecionado = userStatus.getSelectedItem().toString();
        int status = statusSelecionado.equalsIgnoreCase("ATIVO") ? 1 : 0;

        String dataAtual = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        if (usuarioAtual == null) {
            // Inserção
            UserModel novoUsuario = new UserModel();
            novoUsuario.setName(nome);
            novoUsuario.setEmail(email);
            novoUsuario.setPassword(senha);
            novoUsuario.setIsAdmin(tipoUsuario);
            novoUsuario.setStatus(status);
            novoUsuario.setCreatedAt(dataAtual);
            novoUsuario.setLastUpdate(null);
            novoUsuario.setLastLogin(null);

            long resultado = userDAO.insert(novoUsuario);

            if (resultado != -1) {
                Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show();
            }
        } else {
            usuarioAtual.setName(nome);
            usuarioAtual.setEmail(email);
            usuarioAtual.setPassword(senha);
            usuarioAtual.setIsAdmin(tipoUsuario);
            usuarioAtual.setStatus(status);
            usuarioAtual.setLastUpdate(dataAtual);

            int linhasAfetadas = userDAO.update(usuarioAtual);

            if (linhasAfetadas > 0) {
                Toast.makeText(this, "Usuário atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erro ao atualizar usuário", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
