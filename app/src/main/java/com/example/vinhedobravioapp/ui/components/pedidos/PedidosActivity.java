package com.example.vinhedobravioapp.ui.components.pedidos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.components.CustomHeaderComponent;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class PedidosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        CustomHeaderComponent.configurarHeader(this, getString(R.string.order_title));

        ExtendedFloatingActionButton addWine_btn = findViewById(R.id.addOrder_btn);
        addWine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PedidosActivity.this, CadastroPedidoActivity.class);
                startActivity(intent);            }
        });
    }
}
