package com.example.vinhedobravioapp.ui.components.clientes;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.database.dao.CustomerDAO;
import com.example.vinhedobravioapp.database.model.CustomerModel;
import com.example.vinhedobravioapp.ui.components.helper.CustomButtonHelper;
import com.example.vinhedobravioapp.ui.components.helper.HeaderHelper;

public class CadastroClientesActivity extends AppCompatActivity {
    private EditText customerName, customerCpfCnpj, customerAddress, customerRegion, customerTelephone, customerEmail;
    private CustomButtonHelper cancel_btn, save_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientes_cadastrar_clientes);

        boolean isDashboard = getIntent().getBooleanExtra("isDashboard", false);
        HeaderHelper.configurarHeader(this, getString(R.string.add_customer_title), isDashboard);

        customerName = findViewById(R.id.customerName);
        customerCpfCnpj = findViewById(R.id.customerCpfCnpj);
        customerAddress = findViewById(R.id.customerAddress);
        customerRegion = findViewById(R.id.customerRegion);
        customerTelephone = findViewById(R.id.customerTelephone);
        customerEmail = findViewById(R.id.customerEmail);

        cancel_btn = findViewById(R.id.cancel_btn);
        save_btn = findViewById(R.id.save_btn);

        cancel_btn.setOnClickListener(v -> finish());

        long customerId = getIntent().getLongExtra("customerId", -1);
        if (customerId != -1) {
            loadCustomer(customerId);
        }


        save_btn.setOnClickListener(v -> saveCustomer());
    }

    private void loadCustomer(long id) {
        CustomerDAO dao = new CustomerDAO(this);
        CustomerModel customer = dao.getById(id);
        if (customer != null) {
            customerName.setText(customer.getNameCompanyName());
            customerCpfCnpj.setText(customer.getCpfCnpj());
            customerAddress.setText(customer.getAddress());
            customerRegion.setText(customer.getRegion());
            customerTelephone.setText(customer.getPhone());
            customerEmail.setText(customer.getEmail());
        }
    }

    private void saveCustomer() {
        String name = customerName.getText().toString().trim();
        String cpfCnpj = customerCpfCnpj.getText().toString().trim();
        String address = customerAddress.getText().toString().trim();
        String region = customerRegion.getText().toString().trim();
        String phone = customerTelephone.getText().toString().trim();
        String email = customerEmail.getText().toString().trim();

        if (name.isEmpty() || cpfCnpj.isEmpty() || address.isEmpty() || region.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        long customerId = getIntent().getLongExtra("customerId", -1);

        CustomerModel customer = new CustomerModel();
        if (customerId != -1) {
            customer.setCustomerId(customerId);
        }
        customer.setNameCompanyName(name);
        customer.setCpfCnpj(cpfCnpj);
        customer.setAddress(address);
        customer.setRegion(region);
        customer.setPhone(phone);
        customer.setEmail(email);

        CustomerDAO dao = new CustomerDAO(this);

        if (customerId == -1) {
            long result = dao.insert(customer);
            if (result > 0) {
                Toast.makeText(this, "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Erro ao cadastrar cliente.", Toast.LENGTH_SHORT).show();
            }
        } else {
            int rows = dao.update(customer);
            if (rows > 0) {
                Toast.makeText(this, "Cliente atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else if (rows == 0) {
                Toast.makeText(this, "Nenhuma alteração foi feita.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Erro ao atualizar cliente.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
