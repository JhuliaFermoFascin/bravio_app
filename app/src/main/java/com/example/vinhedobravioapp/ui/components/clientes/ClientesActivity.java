package com.example.vinhedobravioapp.ui.components.clientes;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.adapter.CustomerAdapter;
import com.example.vinhedobravioapp.database.dao.CustomerDAO;
import com.example.vinhedobravioapp.database.model.CustomerModel;
import com.example.vinhedobravioapp.ui.components.helper.HeaderHelper;

import java.util.List;

public class ClientesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomerAdapter adapter;
    private CustomerDAO dao;

    private final int REQUEST_CODE_EDIT = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientes);

        boolean isDashboard = getIntent().getBooleanExtra("isDashboard", true);
        HeaderHelper.configurarHeader(this, getString(R.string.customer_title), isDashboard);

        dao = new CustomerDAO(this);

        recyclerView = findViewById(R.id.customers_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<CustomerModel> customers = dao.getAll();

        adapter = new CustomerAdapter(this, customers, new CustomerAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(CustomerModel customer) {
                Intent intent = new Intent(ClientesActivity.this, CadastroClientesActivity.class);
                intent.putExtra("customerId", customer.getCustomerId());
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
            @Override
            public void onDeleteClick(CustomerModel customer) {
                new AlertDialog.Builder(ClientesActivity.this)
                        .setTitle("Confirmação")
                        .setMessage("Deseja realmente excluir o cliente " + customer.getNameCompanyName() + "?")
                        .setPositiveButton("Sim", (dialog, which) -> {
                            boolean deleted = dao.delete(customer.getCustomerId()) > 0;
                            if (deleted) {
                                Toast.makeText(ClientesActivity.this, "Cliente excluído com sucesso!", Toast.LENGTH_SHORT).show();
                                loadCustomers();
                            } else {
                                Toast.makeText(ClientesActivity.this, "Erro ao excluir cliente.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Não", null)
                        .show();
            }
        });

        recyclerView.setAdapter(adapter);

        findViewById(R.id.addCustomer_btn).setOnClickListener(v -> {
            Intent intent = new Intent(ClientesActivity.this, CadastroClientesActivity.class);
            startActivityForResult(intent, REQUEST_CODE_EDIT);
        });
    }

    private void loadCustomers() {
        List<CustomerModel> customers = dao.getAll();
        adapter.setCustomers(customers);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT) {
            loadCustomers();
        }
    }
}
