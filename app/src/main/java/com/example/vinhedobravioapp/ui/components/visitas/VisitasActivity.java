package com.example.vinhedobravioapp.ui.components.visitas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.components.CustomHeaderComponent;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class VisitasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitas);

        CustomHeaderComponent.configurarHeader(this, getString(R.string.visit));

        ExtendedFloatingActionButton addWine_btn = findViewById(R.id.addVisit_btn);
        addWine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitasActivity.this, CadastroVisitasActivity.class);
                startActivity(intent);            }
        });
    }
}
