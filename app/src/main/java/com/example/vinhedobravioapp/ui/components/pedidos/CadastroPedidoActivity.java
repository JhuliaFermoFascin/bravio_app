package com.example.vinhedobravioapp.ui.components.pedidos;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.database.dao.CustomerDAO;
import com.example.vinhedobravioapp.database.dao.OrderDAO;
import com.example.vinhedobravioapp.database.dao.UserDAO;
import com.example.vinhedobravioapp.database.dao.WineDAO;
import com.example.vinhedobravioapp.database.model.CustomerModel;
import com.example.vinhedobravioapp.database.model.OrderItemModel;
import com.example.vinhedobravioapp.database.model.OrderModel;
import com.example.vinhedobravioapp.database.model.UserModel;
import com.example.vinhedobravioapp.database.model.WineModel;
import com.example.vinhedobravioapp.ui.components.helper.CustomButtonHelper;
import com.example.vinhedobravioapp.ui.components.helper.HeaderHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CadastroPedidoActivity extends AppCompatActivity {

    private CustomButtonHelper addOrderItens_btn, cancel_order_btn, save_order_btn;
    private TextView textTotal, summarySelectedItens;
    private View viewTotal;
    private List<OrderItemModel> itensSelecionados = new ArrayList<>();
    private double totalPedido = 0.0;

    private long selectedCustomerId;
    private long selectedUserIdAuto;
    private AutoCompleteTextView orderCustomer;
    private List<CustomerModel> clientes;

    private AutoCompleteTextView orderUser;
    private List<UserModel> usuarios;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedidos_cadastrar_pedido);

        int tipoUsuario = getIntent().getIntExtra(getString(R.string.tipo_usuario_input), -1);
        HeaderHelper.configurarHeader(this, getString(R.string.order_title), tipoUsuario);

        addOrderItens_btn = findViewById(R.id.addOrderItens_btn);
        cancel_order_btn = findViewById(R.id.cancel_order_btn);
        save_order_btn = findViewById(R.id.save_order_btn);
        summarySelectedItens = findViewById(R.id.summarSelectedItens);
        textTotal = findViewById(R.id.textTotal);
        viewTotal = findViewById(R.id.viewTotal);

        orderCustomer = findViewById(R.id.orderCustomer);
        CustomerDAO customerDAO = new CustomerDAO(this);
        clientes = customerDAO.getAll();
        List<String> nomesClientes = new ArrayList<>();
        for (CustomerModel c : clientes) {
            nomesClientes.add(c.getNameCompanyName());
        }
        ArrayAdapter<String> adapterClientes = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                nomesClientes
        );
        orderCustomer.setAdapter(adapterClientes);
        orderCustomer.setOnItemClickListener((parent, view, position, id) -> {
            String nomeSelecionado = (String) parent.getItemAtPosition(position);
            for (CustomerModel c : clientes) {
                if (c.getNameCompanyName().equals(nomeSelecionado)) {
                    selectedCustomerId = c.getCustomerId();
                    break;
                }
            }
        });
        orderCustomer.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                selectedCustomerId = 0;
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        orderUser = findViewById(R.id.orderUser);
        UserDAO userDAO = new UserDAO(this);
        usuarios = userDAO.getAll();
        List<String> nomesUsuarios = new ArrayList<>();
        for (UserModel u : usuarios) {
            nomesUsuarios.add(u.getName());
        }
        ArrayAdapter<String> adapterUsuarios = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                nomesUsuarios
        );
        orderUser.setAdapter(adapterUsuarios);
        orderUser.setOnItemClickListener((parent, view, position, id) -> {
            String nomeSel = (String) parent.getItemAtPosition(position);
            for (UserModel u : usuarios) {
                if (u.getName().equals(nomeSel)) {
                    selectedUserIdAuto = u.getUserId();
                    break;
                }
            }
        });
        orderUser.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                selectedUserIdAuto = 0;
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        addOrderItens_btn.setOnClickListener(v -> abrirDialogoProdutos());
        cancel_order_btn.setOnClickListener(v -> finish());
        save_order_btn.setOnClickListener(v -> saveOrder());
    }

    private void abrirDialogoProdutos() {
        List<WineModel> vinhos = new WineDAO(this).getAll();
        ProdutoDialogFragment dialog = new ProdutoDialogFragment(vinhos);
        dialog.setListener((itens, total) -> {
            this.itensSelecionados = itens;
            this.totalPedido = total;
            atualizarResumoPedido();
        });
        dialog.show(getSupportFragmentManager(), "dialog_produtos");
    }

    private void atualizarResumoPedido() {
        if (summarySelectedItens == null || textTotal == null) return;

        StringBuilder resumo = new StringBuilder();
        for (OrderItemModel item : itensSelecionados) {
            WineModel vinho = new WineDAO(this).getById(item.getWineId());
            resumo.append(vinho.getName())
                    .append(" x").append(item.getQuantity())
                    .append(" = R$ ")
                    .append(String.format("%.2f", item.getValue() * item.getQuantity()))
                    .append("\n");
        }

        summarySelectedItens.setText(resumo.toString());
        textTotal.setText("Total: R$ " + String.format("%.2f", totalPedido));
        textTotal.setVisibility(View.VISIBLE);
        viewTotal.setVisibility(View.VISIBLE);
    }

    private void saveOrder() {
        if (itensSelecionados.isEmpty()) {
            Toast.makeText(this, "Selecione ao menos um item", Toast.LENGTH_SHORT).show();
            return;
        }

        OrderModel cabecalho = new OrderModel();
        cabecalho.setCustomerId(selectedCustomerId);
        cabecalho.setDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
        cabecalho.setStatus("ABERTO");
        cabecalho.setUserId(selectedUserIdAuto);
        cabecalho.setTotal(totalPedido);

        OrderDAO orderDAO = new OrderDAO(this);
        long pedidoId = orderDAO.inserirPedidoCompleto(cabecalho, itensSelecionados, selectedUserIdAuto, this);

        if (pedidoId > 0) {
            Toast.makeText(this, "Pedido #" + pedidoId + " cadastrado com sucesso!", Toast.LENGTH_LONG).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Erro ao cadastrar pedido", Toast.LENGTH_LONG).show();
        }
    }
}
