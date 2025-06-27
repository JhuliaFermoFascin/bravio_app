package com.example.vinhedobravioapp.ui.components.comissoes;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.loginManager.LoginManager;
import com.example.vinhedobravioapp.ui.components.helper.HeaderHelper;

public class ComissoesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comissoes);

        int tipoUsuario = LoginManager.getInstance().getLoginStatus().isAdminInt();
        HeaderHelper.configurarHeader(this, getString(R.string.commissions), tipoUsuario, false, true, false);
        // Ativa o menu suspenso ao clicar no header
        findViewById(R.id.component_view_custom_header).setOnClickListener(v -> {
            com.example.vinhedobravioapp.ui.components.helper.MenuSuspensoHelper.show(this, tipoUsuario);
        });
        // Aqui você pode adicionar lógica para carregar dados de comissões, se necessário
    }
}
