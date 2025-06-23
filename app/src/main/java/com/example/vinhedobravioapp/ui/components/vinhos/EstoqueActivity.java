package com.example.vinhedobravioapp.ui.components.vinhos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.adapter.WineAdapter;
import com.example.vinhedobravioapp.ui.components.helper.HeaderHelper;
import com.example.vinhedobravioapp.database.model.WineModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class EstoqueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estoque);

        int tipoUsuario = getIntent().getIntExtra(getString(R.string.tipo_usuario_input), -1);
        boolean isDashboard = (tipoUsuario == 1);

        HeaderHelper.configurarHeader(this, getString(R.string.wine), isDashboard, true, true);

        ExtendedFloatingActionButton addWine_btn = findViewById(R.id.addWine_btn);

        if (tipoUsuario == 2) {
            addWine_btn.setVisibility(View.GONE);
        }

        addWine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EstoqueActivity.this, CadastroVinhoActivity.class);
                intent.putExtra("isDashboard", isDashboard);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);}
        });


        RecyclerView recyclerView = findViewById(R.id.wine_recycleview);
        WineAdapter wineAdapter = new WineAdapter(this, new ArrayList<>(),
                wine -> showDetailsDialog(wine),  // clique no item
                wine -> editWine(wine),           // editar
                wine -> deleteWine(wine)
        );

        recyclerView.setAdapter(wineAdapter);

        List<WineModel> listaDeVinhos = new ArrayList<>();

        WineModel wine1 = new WineModel();
        wine1.setWineId(1);
        wine1.setName("Vinho Tinto Reserva");
        wine1.setWineryId(1L);
        wine1.setWineTypeId(2);
        wine1.setCommercialCategoryId(1L);
        wine1.setOriginId(1L);
        wine1.setVintage("2018");
        wine1.setDescription("Intenso e encorpado.");
        wine1.setCompositionType("Única");
        wine1.setTastingNoteId(1L);
        wine1.setFoodPairings("Carnes vermelhas");
        wine1.setAlcoholContent(13.5);
        wine1.setVolume(750);
        wine1.setGrapeId(1L);
        wine1.setAcidity("Média");
        wine1.setIdealTemperatureCelsius(18.0);
        wine1.setAgingPotential("8 anos");

        WineModel wine2 = new WineModel();
        wine2.setWineId(2);
        wine2.setName("Vinho Branco Suave");
        wine2.setWineryId(2L);
        wine2.setWineTypeId(1);
        wine2.setCommercialCategoryId(2L);
        wine2.setOriginId(2L);
        wine2.setVintage("2020");
        wine2.setDescription("Refrescante e leve.");
        wine2.setCompositionType("Blend");
        wine2.setTastingNoteId(2L);
        wine2.setFoodPairings("Peixes e saladas");
        wine2.setAlcoholContent(11.0);
        wine2.setVolume(750);
        wine2.setGrapeId(2L);
        wine2.setAcidity("Baixa");
        wine2.setIdealTemperatureCelsius(10.0);
        wine2.setAgingPotential("2 anos");

        WineModel wine3 = new WineModel();
        wine3.setWineId(3);
        wine3.setName("Vinho Rosé Floral");
        wine3.setWineTypeId(3);
        wine3.setVintage("1991");
        wine3.setDescription("Aromático e delicado.");
        wine3.setTastingNoteId(3L);
        wine3.setFoodPairings("Frutos do mar");
        wine3.setAlcoholContent(12.0);
        wine3.setVolume(750);

        WineModel wine4 = new WineModel();
        wine4.setWineId(4);
        wine4.setName("Espumante Brut");
        wine4.setWineTypeId(4);
        wine4.setVintage("2021");
        wine4.setDescription("Seco e efervescente.");
        wine4.setTastingNoteId(4L);
        wine4.setFoodPairings("Aperitivos e frutos do mar");
        wine4.setAlcoholContent(12.5);
        wine4.setVolume(750);

        WineModel wine5 = new WineModel();
        wine5.setWineId(5);
        wine5.setName("Vinho Tinto Reserva Especial");
        wine5.setWineTypeId(2);
        wine5.setVintage("2016");
        wine5.setDescription("Complexo, com taninos suaves.");
        wine5.setTastingNoteId(5L);
        wine5.setFoodPairings("Carnes assadas");
        wine5.setAlcoholContent(14.0);
        wine5.setVolume(750);

        WineModel wine6 = new WineModel();
        wine6.setWineId(6);
        wine6.setName("Vinho Branco Seco");
        wine6.setWineTypeId(1);
        wine6.setVintage("2021");
        wine6.setDescription("Cítrico e fresco.");
        wine6.setTastingNoteId(2L);
        wine6.setFoodPairings("Massas e peixes");
        wine6.setAlcoholContent(12.0);
        wine6.setVolume(750);



        listaDeVinhos.add(wine1);
        listaDeVinhos.add(wine2);
        listaDeVinhos.add(wine3);
        listaDeVinhos.add(wine4);
        listaDeVinhos.add(wine5);
        listaDeVinhos.add(wine6);

        wineAdapter.setWineList(listaDeVinhos);
    }

    private void showDetailsDialog(WineModel wine) {
        DetalhesVinhoActivity dialog = DetalhesVinhoActivity.newInstance(wine);
        dialog.show(getSupportFragmentManager(), "detalhes_vinho");
    }

    private void editWine(WineModel wine) {
    }

    private void deleteWine(WineModel wine) {
    }

}
