package com.example.vinhedobravioapp.ui.components.inicial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.components.CustomButtonComponent;

public class BemVindoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_bem_vindo);

        CustomButtonComponent btnVoltar = findViewById(R.id.btnRetornar);

        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        });
    }
}
