package com.example.vinhedobravioapp.ui.components.inicial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.ui.components.helper.ConfirmacaoHelper;
import com.example.vinhedobravioapp.ui.components.helper.MenuVisitanteHelper;
import com.example.vinhedobravioapp.ui.components.visitas.VisitasActivity;

public class HistoriaActivity extends AppCompatActivity {
    private int tipoUsuario = -1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_historia);

        int tipoUsuario = getIntent().getIntExtra(getString(R.string.tipo_usuario_input), -1);

        ImageView menu_suspenso = findViewById(R.id.menu_suspenso);

        menu_suspenso.setOnClickListener(v -> MenuVisitanteHelper.show(this, tipoUsuario));

    }

    @Override
    public void onBackPressed() {
        String destinoTexto = (tipoUsuario == 1 || tipoUsuario == 0) ?
                getString(R.string.confirmar_retorno_adm) :
                getString(R.string.confirmar_retorno_menu);

        String mensagem = getString(R.string.pergunta_saida, destinoTexto);

        ConfirmacaoHelper.mostrarConfirmacao(this, mensagem, () -> {
            Intent intent;

            if (tipoUsuario == 1) {
                intent = new Intent(this, DashboardAdmActivity.class);
            } else if (tipoUsuario == 0) {
                intent = new Intent(this, VisitasActivity.class);
            } else {
                SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preferencia_login), MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();

                intent = new Intent(this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            }

            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });
    }
}
