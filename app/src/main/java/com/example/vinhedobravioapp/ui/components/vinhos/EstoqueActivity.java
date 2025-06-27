package com.example.vinhedobravioapp.ui.components.vinhos;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.adapter.WineAdapter;
import com.example.vinhedobravioapp.database.dao.WineDAO;
import com.example.vinhedobravioapp.database.utils.GetAllFullWineModel;
import com.example.vinhedobravioapp.models.FullWineModel;
import com.example.vinhedobravioapp.ui.components.helper.HeaderHelper;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class EstoqueActivity extends AppCompatActivity {

    private WineAdapter wineAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estoque);

        ExtendedFloatingActionButton addWine_btn = findViewById(R.id.addWine_btn);
        RecyclerView recyclerView = findViewById(R.id.wine_recycleview);
        EditText searchEditText = findViewById(R.id.Filter);

        int tipoUsuario = getIntent().getIntExtra(getString(R.string.tipo_usuario_input), -1);
        boolean visitante = getIntent().getBooleanExtra("forcar_menu_visitante", false);

        if(tipoUsuario == 1){
            HeaderHelper.configurarHeader(this, getString(R.string.wine), tipoUsuario, true, true, visitante);
        }
        else{
            HeaderHelper.configurarHeader(this, getString(R.string.wine), tipoUsuario, false, true, visitante);
            addWine_btn.setVisibility(View.GONE);


        }

        addWine_btn.setOnClickListener(view -> {
            Intent intent = new Intent(EstoqueActivity.this, CadastroVinhoActivity.class);
            intent.putExtra(getString(R.string.tipo_usuario_input), tipoUsuario);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        wineAdapter = new WineAdapter(this, new ArrayList<>(),
                this::showDetailsDialog,
                this::editWine,
                this::deleteWine,
                tipoUsuario
        );
        recyclerView.setAdapter(wineAdapter);

        // --- IMPLEMENTAÇÃO DA BARRA DE PESQUISA DE VINHOS ---
        if (searchEditText != null) {
            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String query = s.toString();
                    List<FullWineModel> winesList = query.isEmpty() ? GetAllFullWineModel.getAll(EstoqueActivity.this) : GetAllFullWineModel.findByNameLike(EstoqueActivity.this, query);
                    wineAdapter.setWineList(winesList);
                }
                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
        // --- FIM DA IMPLEMENTAÇÃO DA BARRA DE PESQUISA DE VINHOS ---

//        ImageView config_icon = findViewById(R.id.config_icon);
//        config_icon.setOnClickListener(view -> {
//            Intent intent = new Intent(EstoqueActivity.this, ConfigEstoqueActivity.class);
//            startActivity(intent);
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<FullWineModel> winesList = GetAllFullWineModel.getAll(this);
        wineAdapter.setWineList(winesList);
    }

    private void showDetailsDialog(FullWineModel wine) {
        DetalhesVinhoActivity dialog = DetalhesVinhoActivity.newInstance(wine);
        dialog.show(getSupportFragmentManager(), "detalhes_vinho");
    }

    private void editWine(FullWineModel wine) {
        Intent intent = new Intent(EstoqueActivity.this, CadastroVinhoActivity.class);
        intent.putExtra("wine_id", wine.getWineId());
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void deleteWine(FullWineModel wine) {
        new AlertDialog.Builder(this).setTitle("Confirmar exclusão")
                .setMessage("Tem certeza que deseja excluir o vinho \"" + wine.getName() + "\"?")
                .setPositiveButton("Excluir", (dialog, which) -> {
                    WineDAO wineDAO = new WineDAO(this);
                    wineDAO.delete(wine.getWineId());

                    List<FullWineModel> winesList = GetAllFullWineModel.getAll(this);
                    wineAdapter.setWineList(winesList);
                }).setNegativeButton("Cancelar", null).show();
    }
}
