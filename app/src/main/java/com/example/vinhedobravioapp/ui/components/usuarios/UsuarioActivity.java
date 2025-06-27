package com.example.vinhedobravioapp.ui.components.usuarios;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.adapter.UserAdapter;
import com.example.vinhedobravioapp.database.dao.UserDAO;
import com.example.vinhedobravioapp.database.model.UserModel;
import com.example.vinhedobravioapp.loginManager.LoginManager;
import com.example.vinhedobravioapp.ui.components.helper.HeaderHelper;
import com.example.vinhedobravioapp.ui.components.utils.LoginStatus;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

public class UsuarioActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton addUser_btn;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private UserDAO userDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuarios);

        int isDashboard = LoginManager.getInstance().getLoginStatus().isAdminInt();
        HeaderHelper.configurarHeader(this, getString(R.string.user_title), isDashboard);

        userDAO = new UserDAO(this);

        recyclerView = findViewById(R.id.ocustomer_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(this, userDAO, new UserAdapter.OnUserClickListener() {
            @Override
            public void onEdit(UserModel user) {
                Intent intent = new Intent(UsuarioActivity.this, CadastroUsuarioActivity.class);
                intent.putExtra("userId", user.getUserId());
                startActivity(intent);
            }

            @Override
            public void onDelete(UserModel user) {
                new AlertDialog.Builder(UsuarioActivity.this)
                        .setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir o usuário \"" + user.getName() + "\"?")
                        .setPositiveButton("Sim", (dialog, which) -> {
                            new Thread(() -> {
                                userDAO.delete(user.getUserId());
                                runOnUiThread(() -> {
                                    Toast.makeText(UsuarioActivity.this, "Usuário excluído!", Toast.LENGTH_SHORT).show();
                                    loadUsers();
                                });
                            }).start();
                        })
                        .setNegativeButton("Não", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });
        recyclerView.setAdapter(userAdapter);

        loadUsers();

        addUser_btn = findViewById(R.id.addUser_btn);
        addUser_btn.setOnClickListener(v -> {
            Intent intent = new Intent(UsuarioActivity.this, CadastroUsuarioActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }

    private void loadUsers() {
        List<UserModel> users = userDAO.getAll();
        userAdapter.setUserList(users);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUsers();
    }
}
