package com.example.vinhedobravioapp.ui.components.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.components.CustomButtonComponent;
import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.dao.OrderDAO;
import com.example.vinhedobravioapp.database.model.OrderModel;
import com.example.vinhedobravioapp.ui.components.vinhos.EstoqueActivity;

public class PainelAdmActivity extends AppCompatActivity {

    private CustomButtonComponent estoque_btn;

    // Inicialização do banco
    DPOpenHelper dbHelper = new DPOpenHelper(this);
        dbHelper.getWritableDatabase();


    OrderDAO orderDAO = new OrderDAO(this);

    // Hard code: cria novo pedido
    OrderModel newOrder = new OrderModel();
        newOrder.setOrderId(1); // cuidado com ID duplicado
        newOrder.setCustomerId(123);
        newOrder.setDate("2025-06-16");
        newOrder.setStatus("Pendente");
        newOrder.setUserId(1);

    long result = orderDAO.insert(newOrder);
        Log.d("DB", "Inserção result: " + result);

    // Recupera todos e mostra no log
        for (OrderModel order : orderDAO.getAll()) {
        Log.d("DB", "Pedido #" + order.getOrderId() + ": Cliente " + order.getCustomerId() + ", Data " + order.getDate() + ", Status: " + order.getStatus());
    }




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.painel_administrativo);

        estoque_btn = findViewById(R.id.estoque_btn);

        estoque_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelAdmActivity.this, EstoqueActivity.class);
                startActivity(intent);
            }
        });
    }

}
