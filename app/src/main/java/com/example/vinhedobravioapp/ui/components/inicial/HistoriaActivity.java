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
    private int origemAdm = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_historia);

        origemAdm = getIntent().getIntExtra(getString(R.string.tipo_usuario_input), 0);

        ImageView menu_suspenso = findViewById(R.id.menu_suspenso);

        menu_suspenso.setOnClickListener(v -> MenuVisitanteHelper.show(this, origemAdm));

    }

    @Override
    public void onBackPressed() {
        String destinoTexto = (origemAdm == 1 || origemAdm == 2) ?
                getString(R.string.confirmar_retorno_adm) :
                getString(R.string.confirmar_retorno_menu);

        String mensagem = getString(R.string.pergunta_saida, destinoTexto);

        ConfirmacaoHelper.mostrarConfirmacao(this, mensagem, () -> {
            Intent intent;

            if (origemAdm == 1) {
                intent = new Intent(this, DashboardAdmActivity.class);
            } else if (origemAdm == 2) {
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
