package com.example.vinhedobravioapp.ui.components.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.adapter.WineAdapter;
import com.example.vinhedobravioapp.database.model.WineModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class EstoqueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estoque);

        ImageView back_home = findViewById(R.id.return_home);
        back_home.setOnClickListener(v -> {
            Intent intent = new Intent(EstoqueActivity.this, PainelAdmActivity.class);
            intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        ExtendedFloatingActionButton addWine_btn = findViewById(R.id.addWine_btn);
        addWine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EstoqueActivity.this, CadastroVinhoActivity.class);
                startActivity(intent);            }
        });


        RecyclerView recyclerView = findViewById(R.id.wine_recycleview);
        WineAdapter wineAdapter = new WineAdapter(this, new ArrayList<>(),
                wine -> showDetailsDialog(wine),  // clique no item
                wine -> editWine(wine),           // editar
                wine -> deleteWine(wine)
        );

        recyclerView.setAdapter(wineAdapter);

        //List<WineModel> listaDeVinhos = buscarVinhos();
        //wineAdapter.setWineList(listaDeVinhos);

        List<WineModel> listaDeVinhos = new ArrayList<>();

        WineModel wine1 = new WineModel();
        wine1.setWineId(1);
        wine1.setName("Vinho Tinto Reserva");
        wine1.setWineTypeId(2);
        wine1.setVintage(2018);
        wine1.setDescription("Intenso e encorpado.");
        wine1.setTastingNoteId(1);
        wine1.setFoodPairings("Carnes vermelhas");
        wine1.setAlcoholContent(13.5);
        wine1.setVolume(750);

        WineModel wine2 = new WineModel();
        wine2.setWineId(2);
        wine2.setName("Vinho Branco Suave");
        wine2.setWineTypeId(1);
        wine2.setVintage(2020);
        wine2.setDescription("Refrescante e leve.");
        wine2.setTastingNoteId(2);
        wine2.setFoodPairings("Peixes e saladas");
        wine2.setAlcoholContent(11.0);
        wine2.setVolume(750);

        WineModel wine3 = new WineModel();
        wine3.setWineId(3);
        wine3.setName("Vinho Rosé Floral");
        wine3.setWineTypeId(3);
        wine3.setVintage(2019);
        wine3.setDescription("Aromático e delicado.");
        wine3.setTastingNoteId(3);
        wine3.setFoodPairings("Frutos do mar");
        wine3.setAlcoholContent(12.0);
        wine3.setVolume(750);

        WineModel wine4 = new WineModel();
        wine4.setWineId(4);
        wine4.setName("Espumante Brut");
        wine4.setWineTypeId(4);
        wine4.setVintage(2021);
        wine4.setDescription("Seco e efervescente.");
        wine4.setTastingNoteId(4);
        wine4.setFoodPairings("Aperitivos e frutos do mar");
        wine4.setAlcoholContent(12.5);
        wine4.setVolume(750);

        WineModel wine5 = new WineModel();
        wine5.setWineId(5);
        wine5.setName("Vinho Tinto Reserva Especial");
        wine5.setWineTypeId(2);
        wine5.setVintage(2016);
        wine5.setDescription("Complexo, com taninos suaves.");
        wine5.setTastingNoteId(5);
        wine5.setFoodPairings("Carnes assadas");
        wine5.setAlcoholContent(14.0);
        wine5.setVolume(750);

        WineModel wine6 = new WineModel();
        wine6.setWineId(6);
        wine6.setName("Vinho Branco Seco");
        wine6.setWineTypeId(1);
        wine6.setVintage(2019);
        wine6.setDescription("Cítrico e fresco.");
        wine6.setTastingNoteId(2);
        wine6.setFoodPairings("Massas e peixes");
        wine6.setAlcoholContent(12.0);
        wine6.setVolume(750);

        WineModel wine7 = new WineModel();
        wine7.setWineId(7);
        wine7.setName("Vinho Rosé Frutado");
        wine7.setWineTypeId(3);
        wine7.setVintage(2020);
        wine7.setDescription("Leve, com notas de morango.");
        wine7.setTastingNoteId(3);
        wine7.setFoodPairings("Saladas e pratos leves");
        wine7.setAlcoholContent(11.5);
        wine7.setVolume(750);

        WineModel wine8 = new WineModel();
        wine8.setWineId(8);
        wine8.setName("Espumante Demi-Sec");
        wine8.setWineTypeId(4);
        wine8.setVintage(2022);
        wine8.setDescription("Doce, ideal para sobremesas.");
        wine8.setTastingNoteId(4);
        wine8.setFoodPairings("Sobremesas e queijos suaves");
        wine8.setAlcoholContent(11.5);
        wine8.setVolume(750);

        WineModel wine9 = new WineModel();
        wine9.setWineId(9);
        wine9.setName("Vinho Tinto Jovem");
        wine9.setWineTypeId(2);
        wine9.setVintage(2021);
        wine9.setDescription("Frutado e vibrante.");
        wine9.setTastingNoteId(1);
        wine9.setFoodPairings("Carnes grelhadas");
        wine9.setAlcoholContent(13.0);
        wine9.setVolume(750);

        WineModel wine10 = new WineModel();
        wine10.setWineId(10);
        wine10.setName("Vinho Branco Aromático");
        wine10.setWineTypeId(1);
        wine10.setVintage(2021);
        wine10.setDescription("Notas florais e frutadas.");
        wine10.setTastingNoteId(2);
        wine10.setFoodPairings("Peixes e frutos do mar");
        wine10.setAlcoholContent(12.5);
        wine10.setVolume(750);

        listaDeVinhos.add(wine1);
        listaDeVinhos.add(wine2);
        listaDeVinhos.add(wine3);
        listaDeVinhos.add(wine4);
        listaDeVinhos.add(wine5);
        listaDeVinhos.add(wine6);
        listaDeVinhos.add(wine7);
        listaDeVinhos.add(wine8);
        listaDeVinhos.add(wine9);
        listaDeVinhos.add(wine10);


        // ✅ Atualiza o adapter com os vinhos mockados
        wineAdapter.setWineList(listaDeVinhos);
    }

    private void showDetailsDialog(WineModel wine) {
        DetalhesVinhoActivity dialog = DetalhesVinhoActivity.newInstance(wine);
        dialog.show(getSupportFragmentManager(), "detalhes_vinho");
    }

    private void editWine(WineModel wine) {
        // Ex: iniciar uma activity passando o ID do vinho
    }

    private void deleteWine(WineModel wine) {
        // Ex: exibir confirmação e excluir do banco
    }

}
