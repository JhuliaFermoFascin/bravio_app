package com.example.vinhedobravioapp.ui.components.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vinhedobravioapp.R;

public class ConfirmacaoHelper {
    public interface ConfirmacaoCallback {
        void onConfirm();
    }

    public static void mostrarConfirmacao(Activity activity, String mensagem, ConfirmacaoCallback callback) {
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.modal_confirmacao, null);

        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        Button btnYes = dialogView.findViewById(R.id.btnYes);
        Button btnNo = dialogView.findViewById(R.id.btnNo);
        TextView txtMensagem = dialogView.findViewById(R.id.mensagem_confirmacao);

        txtMensagem.setText(mensagem);

        btnYes.setOnClickListener(v -> {
            dialog.dismiss();
            callback.onConfirm();
        });

        btnNo.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }
}
