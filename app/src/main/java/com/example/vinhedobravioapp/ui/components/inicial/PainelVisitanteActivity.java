package com.example.vinhedobravioapp.ui.components.inicial;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.ui.components.helper.CustomButtonHelper;
import com.example.vinhedobravioapp.ui.components.helper.ConfirmacaoHelper;

public class PainelVisitanteActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_visitante);
    }

    private void mostrarConfirmacaoSaida(int destinoValor) {
        String destinoTexto = (destinoValor == 1 || destinoValor == 2) ?
                getString(R.string.confirmar_retorno_adm) :
                getString(R.string.confirmar_retorno_menu);

        String mensagem = getString(R.string.pergunta_saida, destinoTexto);

        ConfirmacaoHelper.mostrarConfirmacao(this, mensagem, () -> {
            if (destinoValor == 1) {
                Intent intent = new Intent(BemVindoActivity.this, DashboardAdmActivity.class);
                startActivity(intent);
            } else if (destinoValor == 2) {
                Intent intent = new Intent(BemVindoActivity.this, PainelRepresentanteActivity.class);
                startActivity(intent);
            } else {
                SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preferencia_login), MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(BemVindoActivity.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        mostrarConfirmacaoSaida(origemAdm);
    }
}
