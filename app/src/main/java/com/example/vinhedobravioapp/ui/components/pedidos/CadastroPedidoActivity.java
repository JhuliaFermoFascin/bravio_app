package com.example.vinhedobravioapp.ui.components.pedidos;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.ui.components.helper.CustomButtonHelper;
import com.example.vinhedobravioapp.ui.components.helper.HeaderHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CadastroPedidoActivity extends AppCompatActivity {

    private CustomButtonHelper addOrderItens_btn;
    private TextView textTotal, summarySelectedItens;
    private View viewTotal;

    private Map<String, Integer> resumoMap = new HashMap<>();
    private Map<String, Double> produtos = new HashMap<>(); //Alterar quando tiver o banco
    private double totalGeral = 0.0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedidos_cadastrar_pedido);

        int tipoUsuario = getIntent().getIntExtra(getString(R.string.tipo_usuario_input), -1);

        HeaderHelper.configurarHeader(this, getString(R.string.order_title), tipoUsuario);

        addOrderItens_btn = findViewById(R.id.addOrderItens_btn);
        summarySelectedItens = findViewById(R.id.summarSelectedItens);
        textTotal = findViewById(R.id.textTotal);
        viewTotal = findViewById(R.id.viewTotal);

        //Exemplo produtos
        // Exemplo de produtos (nome → valor unitário)
        produtos.put("Vinho", 35.0);
        produtos.put("Queijo", 22.5);
        produtos.put("Pão", 5.0);

        atualizarResumo();

        addOrderItens_btn.setOnClickListener(v -> showAddItensDialog());
    }

    private void showAddItensDialog(){
        View dialogView = getLayoutInflater().inflate(R.layout.pedidos_dialog_add_product, null);
        EditText productName = dialogView.findViewById(R.id.productName);
        EditText productQuantity = dialogView.findViewById(R.id.productQuantity);
        TextView productUnitPrice = dialogView.findViewById(R.id.productUnitPrice);
        CustomButtonHelper saveItem_btn = dialogView.findViewById(R.id.saveItem_btn);

        AlertDialog dialog = new AlertDialog.Builder(this).setView(dialogView).create();

        productName.setOnClickListener(v -> {
            mostrarDialogoSelecaoProduto(productName, productUnitPrice);
        });

        saveItem_btn.setOnClickListener(v -> {
            String nome = productName.getText().toString();
            String qtdStr = productQuantity.getText().toString();

            if (!produtos.containsKey(nome) || qtdStr.isEmpty()) {
                Toast.makeText(this, "Dados inválidos!", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantidade = Integer.parseInt(qtdStr);
            double valorUnitario = produtos.get(nome);

            // Soma à quantidade existente se já tiver sido adicionado
            int quantidadeAtual = resumoMap.getOrDefault(nome, 0);
            resumoMap.put(nome, quantidadeAtual + quantidade);

            // Atualiza o total geral (apenas soma o novo total parcial)
            totalGeral += quantidade * valorUnitario;

            atualizarResumo();
            dialog.dismiss();
        });


        dialog.show();
    }

    private void atualizarResumo() {
        if (resumoMap.isEmpty()) {
            summarySelectedItens.setText("Nenhum item selecionado.");
            viewTotal.setVisibility(View.GONE);
            textTotal.setVisibility(View.GONE);
            return;
        }

        StringBuilder builder = new StringBuilder("Produtos Selecionados:\n\n");
        int i = 1;
        for (Map.Entry<String, Integer> entry : resumoMap.entrySet()) {
            String nome = entry.getKey();
            int quantidade = entry.getValue();
            double valorUnitario = produtos.get(nome);
            double totalParcial = quantidade * valorUnitario;

            builder.append(i++)
                    .append(". ")
                    .append(nome)
                    .append(" | ")
                    .append(quantidade)
                    .append(" | R$ ")
                    .append(String.format("%.2f", totalParcial))
                    .append("\n");
        }

        summarySelectedItens.setText(builder.toString());

        viewTotal.setVisibility(View.VISIBLE);
        textTotal.setVisibility(View.VISIBLE);
        textTotal.setText("Total: R$ " + String.format("%.2f", totalGeral));
    }


    private void mostrarDialogoSelecaoProduto(EditText productName, TextView productUnitPrice) {
        View dialogView = getLayoutInflater().inflate(R.layout.pedidos_select_product, null);
        EditText editTextSearch = dialogView.findViewById(R.id.searchItem);
        ListView listView = dialogView.findViewById(R.id.listView);

        List<String> produtosList = new ArrayList<>(produtos.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, produtosList);
        listView.setAdapter(adapter);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();

        dialog.show();

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String nomeSelecionado = adapter.getItem(position);
            productName.setText(nomeSelecionado);
            productUnitPrice.setText("Valor unitário: R$ " + produtos.get(nomeSelecionado));
            dialog.dismiss();
        });

        dialog.show();
    }


}
