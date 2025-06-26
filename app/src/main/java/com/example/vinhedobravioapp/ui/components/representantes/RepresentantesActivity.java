package com.example.vinhedobravioapp.ui.components.representantes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.adapter.UserAdapter;
import com.example.vinhedobravioapp.database.dao.UserDAO;
import com.example.vinhedobravioapp.database.model.UserModel;
import com.example.vinhedobravioapp.ui.components.usuarios.CadastroUsuarioActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

public class RepresentantesActivity extends AppCompatActivity {
    private ExtendedFloatingActionButton addRepresentative_btn;
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private UserDAO userDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.representantes);

        userDAO = new UserDAO(this);

        recyclerView = findViewById(R.id.orders_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UserAdapter(this, userDAO, new UserAdapter.OnUserClickListener() {
            @Override
            public void onEdit(UserModel user) {
                Intent intent = new Intent(RepresentantesActivity.this, CadastroUsuarioActivity.class);
                intent.putExtra("userId", user.getUserId());
                startActivity(intent);
            }

            @Override
            public void onDelete(UserModel user) {
                userDAO.delete(user.getUserId());
                loadRepresentantes();
            }
        });

        recyclerView.setAdapter(adapter);

        addRepresentative_btn = findViewById(R.id.addRepresentative_btn);
        addRepresentative_btn.setOnClickListener(v -> {
            Intent intent = new Intent(RepresentantesActivity.this, CadastroUsuarioActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }

    private void loadRepresentantes() {
        List<UserModel> representantes = userDAO.getRepresentantes();
        adapter.setUserList(representantes);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRepresentantes();
    }
}
