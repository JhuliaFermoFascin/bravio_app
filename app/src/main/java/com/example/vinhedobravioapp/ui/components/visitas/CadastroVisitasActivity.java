package com.example.vinhedobravioapp.ui.components.visitas;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.database.dao.CustomerDAO;
import com.example.vinhedobravioapp.database.dao.VisitDAO;
import com.example.vinhedobravioapp.database.model.CustomerModel;
import com.example.vinhedobravioapp.database.model.VisitModel;
import com.example.vinhedobravioapp.ui.components.helper.CustomButtonHelper;
import com.example.vinhedobravioapp.ui.components.helper.HeaderHelper;
import com.example.vinhedobravioapp.ui.components.utils.LoginStatus;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CadastroVisitasActivity extends AppCompatActivity {

    private AutoCompleteTextView etCustomerName;
    private EditText etCpf, etContacts, etLocation, etDescription, etName, dateTimeVisit;
    private CustomButtonHelper btnSave;

    private CustomerModel clienteSelecionado = null;
    private List<CustomerModel> clientes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitas_cadastrar_visitas);

        HeaderHelper.configurarHeader(this, getString(R.string.add_new_visit), 0);

        etCustomerName = findViewById(R.id.customer_name);
        etCpf = findViewById(R.id.customer_cpf);
        etContacts = findViewById(R.id.customer_contacts);
        etLocation = findViewById(R.id.customer_address);
        etDescription = findViewById(R.id.visit_description);
        etName = findViewById(R.id.visit_name);
        btnSave = findViewById(R.id.save_btn);
        dateTimeVisit = findViewById(R.id.date_time_visit);

        // Carregar clientes do banco
        CustomerDAO customerDAO = new CustomerDAO(this);
        clientes = customerDAO.getAll();

        // Preencher o autocomplete com nomes
        List<String> nomesClientes = new ArrayList<>();
        for (CustomerModel c : clientes) {
            nomesClientes.add(c.getNameCompanyName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, nomesClientes);
        etCustomerName.setAdapter(adapter);

        // Quando selecionar cliente
        etCustomerName.setOnItemClickListener((parent, view, position, id) -> {
            String nomeSelecionado = (String) parent.getItemAtPosition(position);
            for (CustomerModel c : clientes) {
                if (c.getNameCompanyName().equals(nomeSelecionado)) {
                    clienteSelecionado = c;
                    etCpf.setText(c.getCpfCnpj());
                    etContacts.setText(c.getPhone());
                    etLocation.setText(c.getAddress());
                    break;
                }
            }
        });

        dateTimeVisit.setOnClickListener(v -> showDateTimePicker());

        btnSave.setOnClickListener(v -> {
            String nomeVisita = etName.getText().toString().trim();
            if (nomeVisita.isEmpty()) {
                Toast.makeText(this, "Informe o nome da visita", Toast.LENGTH_SHORT).show();
                return;
            }

            if (clienteSelecionado == null) {
                Toast.makeText(this, "Selecione um cliente válido", Toast.LENGTH_SHORT).show();
                return;
            }

            // Recuperar o userId do login
            SharedPreferences prefs = getSharedPreferences(getString(R.string.preferencia_login), MODE_PRIVATE);
            String json = prefs.getString(getString(R.string.login_status), null);

            if (json == null) {
                Toast.makeText(this, "Usuário não está logado", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            Gson gson = new Gson();
            LoginStatus status = gson.fromJson(json, LoginStatus.class);
            long userIdLogado = status.getIdUsuario();

            // Criar a visita
            VisitModel visit = new VisitModel();
            visit.setCustomerId(clienteSelecionado.getCustomerId());
            visit.setUserId(userIdLogado);
            visit.setName(nomeVisita);
            visit.setDateTime(dateTimeVisit.getText().toString());
            visit.setLocation(etLocation.getText().toString());
            visit.setWines(""); // ou adicionar lógica futura
            visit.setDescription(etDescription.getText().toString());

            VisitDAO visitDAO = new VisitDAO(this);
            long result = visitDAO.insert(visit);

            if (result != -1) {
                Toast.makeText(this, "Visita salva com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erro ao salvar visita", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDateTimePicker() {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view1, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                dateTimeVisit.setText(format.format(calendar.getTime()));
            },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true);
            timePickerDialog.show();
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
