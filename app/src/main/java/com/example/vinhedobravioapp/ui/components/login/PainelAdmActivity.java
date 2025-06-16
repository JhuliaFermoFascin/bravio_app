package com.example.vinhedobravioapp.ui.components.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.components.CustomButtonComponent;
import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.dao.OrderDAO;
import com.example.vinhedobravioapp.database.model.OrderModel;
import com.example.vinhedobravioapp.ui.components.inicial.MenuActivity;
import com.example.vinhedobravioapp.ui.components.vinhos.EstoqueActivity;

public class PainelAdmActivity extends AppCompatActivity {

    private CustomButtonComponent estoque_btn;
    private CustomButtonComponent exit_btn;

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
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        View dialogView = getLayoutInflater().inflate(R.layout.modal_confirmacao, null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        Button btnYes = dialogView.findViewById(R.id.btnYes);
        Button btnNo = dialogView.findViewById(R.id.btnNo);

        btnYes.setOnClickListener(v -> {
            dialog.dismiss();
            SharedPreferences.Editor editor = getSharedPreferences("loginPrefs", MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(PainelAdmActivity.this, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });
        btnNo.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}
