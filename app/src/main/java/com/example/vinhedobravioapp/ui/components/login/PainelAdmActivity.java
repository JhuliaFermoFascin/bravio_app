package com.example.vinhedobravioapp.ui.components.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.components.CustomButtonComponent;
import com.example.vinhedobravioapp.ui.components.vinhos.EstoqueActivity;

public class PainelAdmActivity extends AppCompatActivity {

    private CustomButtonComponent estoque_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.painel_administrativo);

        estoque_btn = findViewById(R.id.estoque_btn);

        estoque_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelAdmActivity.this, EstoqueActivity.class);
                startActivity(intent);            }
        });
    }
}
