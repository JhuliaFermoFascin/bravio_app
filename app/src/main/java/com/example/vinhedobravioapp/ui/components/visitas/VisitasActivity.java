package com.example.vinhedobravioapp.ui.components.visitas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.ui.components.helper.ConfirmacaoHelper;
import com.example.vinhedobravioapp.ui.components.helper.HeaderHelper;
import com.example.vinhedobravioapp.ui.components.inicial.MenuActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class VisitasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitas);

        HeaderHelper.configurarHeader(this, getString(R.string.visit), 2, false, true);

        ExtendedFloatingActionButton addWine_btn = findViewById(R.id.addVisit_btn);
        addWine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitasActivity.this, CadastroVisitasActivity.class);
                startActivity(intent);            }
        });
    }

    @Override
    public void onBackPressed() {
        String mensagem = getString(R.string.pergunta_saida, getString(R.string.confirmar_retorno_menu));

        ConfirmacaoHelper.mostrarConfirmacao(this, mensagem, () -> {
            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preferencia_login), MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(VisitasActivity.this, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });
    }
}
