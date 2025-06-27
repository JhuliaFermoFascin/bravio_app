package com.example.vinhedobravioapp.ui.components.bemvindo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.ui.components.helper.CustomButtonHelper;

public class ContatoActivity extends AppCompatActivity {
    private EditText editContactName, editContactEmail, editContactMessage;
    private CustomButtonHelper cancelBtn, saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contato);

        editContactName = findViewById(R.id.editContactName);
        editContactEmail = findViewById(R.id.editContactEmail);
        editContactMessage = findViewById(R.id.editContactMessage);
        cancelBtn = findViewById(R.id.cancel_btn);
        saveBtn = findViewById(R.id.save_btn);

        cancelBtn.setOnClickListener(v -> finish());

        saveBtn.setOnClickListener(v -> {
            String nome = editContactName.getText().toString().trim();
            String email = editContactEmail.getText().toString().trim();
            String mensagem = editContactMessage.getText().toString().trim();

            if (nome.isEmpty() || email.isEmpty() || mensagem.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                return;
            }
            // Aqui você pode implementar o envio do contato (e-mail, API, etc)
            Toast.makeText(this, "Mensagem enviada com sucesso!", Toast.LENGTH_LONG).show();
            finish();
        });

        findViewById(R.id.arrow_back).setOnClickListener(v -> finish());
    }
}
