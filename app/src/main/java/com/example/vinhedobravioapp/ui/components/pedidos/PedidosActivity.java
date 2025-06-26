package com.example.vinhedobravioapp.ui.components.pedidos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.adapter.OrderAdapter;
import com.example.vinhedobravioapp.database.dao.CustomerDAO;
import com.example.vinhedobravioapp.database.dao.OrderDAO;
import com.example.vinhedobravioapp.database.model.CustomerModel;
import com.example.vinhedobravioapp.database.model.OrderModel;
import com.example.vinhedobravioapp.ui.components.helper.HeaderHelper;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PedidosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private OrderDAO orderDAO;
    private static final int REQ_CADASTRO_PEDIDO = 1001;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedidos);

        int tipoUsuario = getIntent().getIntExtra(getString(R.string.tipo_usuario_input), -1);
        HeaderHelper.configurarHeader(this, getString(R.string.order_title), tipoUsuario, false, true, false);

        recyclerView = findViewById(R.id.orders_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CustomerDAO customerDAO = new CustomerDAO(this);
        List<CustomerModel> customers = customerDAO.getAll();

        Map<Long, String> customerNames = new HashMap<>();
        for (CustomerModel c : customers) {
            customerNames.put(c.getCustomerId(), c.getNameCompanyName());
        }

        orderDAO = new OrderDAO(this);
        List<OrderModel> pedidos = orderDAO.getAll();

        adapter = new OrderAdapter(
                this,
                pedidos,
                customerNames,
                order -> {
                    new androidx.appcompat.app.AlertDialog.Builder(this)
                            .setTitle("Confirmar exclusão")
                            .setMessage("Deseja realmente excluir o pedido #" + order.getOrderId() + "?")
                            .setPositiveButton("Sim", (dialog, which) -> {
                                orderDAO.delete(order.getOrderId());
                                refreshList();
                                Toast.makeText(this, "Pedido #" + order.getOrderId() + " removido", Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton("Cancelar", null)
                            .show();
                },
                order -> {
                    DetalhesPedidoDialogFragment dialog = DetalhesPedidoDialogFragment.newInstance(order.getOrderId());
                    dialog.setOnStatusUpdatedListener(() -> refreshList());
                    dialog.show(getSupportFragmentManager(), "DetalhesPedidoDialog");
                }
        );

        recyclerView.setAdapter(adapter);

        ExtendedFloatingActionButton addOrder_btn = findViewById(R.id.addOrder_btn);

        addOrder_btn.setOnClickListener(v -> {
            Intent intent = new Intent(PedidosActivity.this, CadastroPedidoActivity.class);
            startActivityForResult(intent, REQ_CADASTRO_PEDIDO);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CADASTRO_PEDIDO && resultCode == RESULT_OK) {
            refreshList();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        List<OrderModel> novaLista = orderDAO.getAll();
        adapter.updateList(novaLista);
    }

}
