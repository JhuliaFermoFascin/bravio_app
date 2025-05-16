package com.example.vinhedobravioapp.ui.components.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinhedobravioapp.R;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        TextView botaoCustomizado = findViewById(R.id.botao_customizado);
        botaoCustomizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Botão clicado!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
