package com.example.vinhedobravioapp.ui.components.pedidos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.ui.components.helper.HeaderHelper;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class PedidosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedidos);

        int tipoUsuario = getIntent().getIntExtra(getString(R.string.tipo_usuario_input), -1);

        HeaderHelper.configurarHeader(this, getString(R.string.order_title), tipoUsuario, false, true);

        ExtendedFloatingActionButton addWine_btn = findViewById(R.id.addOrder_btn);
        addWine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PedidosActivity.this, CadastroPedidoActivity.class);
                intent.putExtra(getString(R.string.tipo_usuario_input), tipoUsuario);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
}
