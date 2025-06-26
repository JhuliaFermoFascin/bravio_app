package com.example.vinhedobravioapp.ui.components.vinhos;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.adapter.WineAdapter;
import com.example.vinhedobravioapp.database.dao.WineDAO;
import com.example.vinhedobravioapp.database.model.WineModel;
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
        View header = findViewById(R.id.component_view_custom_header);

        int tipoUsuario = getIntent().getIntExtra(getString(R.string.tipo_usuario_input), -1);

        if(tipoUsuario == 1 ){
            HeaderHelper.configurarHeader(this, getString(R.string.wine), tipoUsuario, true, true);
        }
        else{
            HeaderHelper.configurarHeader(this, getString(R.string.wine), tipoUsuario, false, true);
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
                this::deleteWine
        );
        recyclerView.setAdapter(wineAdapter);

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
        WineDAO wineDAO = new WineDAO(this);
        List<WineModel> winesList = wineDAO.getItemInformations(this);
        wineAdapter.setWineList(winesList);
    }

    private void showDetailsDialog(WineModel wine) {
        DetalhesVinhoActivity dialog = DetalhesVinhoActivity.newInstance(wine);
        dialog.show(getSupportFragmentManager(), "detalhes_vinho");
    }

    private void editWine(WineModel wine) {
        Intent intent = new Intent(EstoqueActivity.this, CadastroVinhoActivity.class);
        intent.putExtra("wine_id", wine.getWineId());
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void deleteWine(WineModel wine) {
        new AlertDialog.Builder(this).setTitle("Confirmar exclusão")
                .setMessage("Tem certeza que deseja excluir o vinho \"" + wine.getName() + "\"?")
                .setPositiveButton("Excluir", (dialog, which) -> {
                    WineDAO wineDAO = new WineDAO(this);
                    wineDAO.delete(wine.getWineId());

                    List<WineModel> updatedList = wineDAO.getItemInformations(this);
                    wineAdapter.setWineList(updatedList);
                }).setNegativeButton("Cancelar", null).show();
    }
}
