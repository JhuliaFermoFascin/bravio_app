package com.example.vinhedobravioapp.ui.components.vinhos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.components.CustomButtonComponent;

public class EstoqueDialogConfigActivity extends DialogFragment {

    public interface OnItemSavedListener {
        void onItemSaved (String text, String type);
    }

    private String title, type;
    private OnItemSavedListener listener;

    public EstoqueDialogConfigActivity(String title, String type, OnItemSavedListener listener){
        this.title = title;
        this.type = type;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.estoque_config_dialog, null);

        TextView titleConfig = view.findViewById(R.id.titleConfig);
        EditText inputTextConfig = view.findViewById(R.id.inputTextConfig);
        CustomButtonComponent save_btn = view.findViewById(R.id.save_btn);
        CustomButtonComponent cancel_btn = view.findViewById(R.id.cancel_btn);

        titleConfig.setText(title);

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(view)
                .create();

        cancel_btn.setOnClickListener(v -> dialog.dismiss());

        save_btn.setOnClickListener(v -> {
            String text = inputTextConfig.getText().toString().trim();
            if (!text.isEmpty()) {
                listener.onItemSaved(text, type);
                dialog.dismiss();
            } else {
                inputTextConfig.setError("Campo obrigatório");
            }
        });

        return dialog;
    }
}

