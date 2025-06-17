package com.example.vinhedobravioapp.ui.components.inicial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.ui.components.helper.MenuSuspensoHelper;

public class PainelRepresentanteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_representante);

        ImageView menu_suspenso = findViewById(R.id.menu_suspenso);

        menu_suspenso.setOnClickListener(v -> MenuSuspensoHelper.show(this, false));
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
            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preferencia_login), MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(PainelRepresentanteActivity.this, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });
        btnNo.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}
