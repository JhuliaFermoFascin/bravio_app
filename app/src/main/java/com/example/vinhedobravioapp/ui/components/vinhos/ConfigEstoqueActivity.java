package com.example.vinhedobravioapp.ui.components.vinhos;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.components.CustomButtonComponent;
import com.example.vinhedobravioapp.components.CustomHeaderComponent;
import com.example.vinhedobravioapp.database.dao.CommercialCategoryDAO;
import com.example.vinhedobravioapp.database.dao.CompositionTypeDAO;
import com.example.vinhedobravioapp.database.dao.FoodPairingDAO;
import com.example.vinhedobravioapp.database.dao.GrapeDAO;
import com.example.vinhedobravioapp.database.dao.TastingNoteDAO;
import com.example.vinhedobravioapp.database.dao.WineTypeDAO;
import com.example.vinhedobravioapp.database.model.CommercialCategoryModel;
import com.example.vinhedobravioapp.database.model.CompositionTypeModel;
import com.example.vinhedobravioapp.database.model.FoodPairingModel;
import com.example.vinhedobravioapp.database.model.GrapeModel;
import com.example.vinhedobravioapp.database.model.TastingNoteModel;
import com.example.vinhedobravioapp.database.model.WineTypeModel;

public class ConfigEstoqueActivity extends AppCompatActivity {

    private CustomButtonComponent addCommercialCategory_btn, addGrapeComposition_btn, addFoodPairing_btn, addTastingNotes_btn, addWineType_btn, addWineGrapes_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estoque_config);

        CustomHeaderComponent.configurarHeader(this, getString(R.string.config_title));

        addCommercialCategory_btn = findViewById(R.id.addCommercialCategory_btn);
        addGrapeComposition_btn = findViewById(R.id.addGrapeComposition_btn);
        addFoodPairing_btn = findViewById(R.id.addFoodPairing_btn);
        addTastingNotes_btn = findViewById(R.id.addTastingNotes_btn);
        addWineType_btn = findViewById(R.id.addWineType_btn);
        addWineGrapes_btn = findViewById(R.id.addWineGrapes_btn);

        addCommercialCategory_btn.setOnClickListener(v -> openDialog(getString(R.string.commercial_category), "CATEGORIA"));
        addGrapeComposition_btn.setOnClickListener(v -> openDialog(getString(R.string.grapes_composition), "COMPOSICAO"));
        addFoodPairing_btn.setOnClickListener(v -> openDialog(getString(R.string.food_parings), "HARMONIZAÇÃO"));
        addTastingNotes_btn.setOnClickListener(v -> openDialog(getString(R.string.tasting_notes), "NOTA"));
        addWineType_btn.setOnClickListener(v -> openDialog(getString(R.string.wine_type), "TIPO"));
        addWineGrapes_btn.setOnClickListener(v -> openDialog(getString(R.string.grapes), "UVA"));
    }

    private void openDialog(String titulo, String tipo) {
        EstoqueDialogConfigActivity dialog = new EstoqueDialogConfigActivity(titulo, tipo, (texto, tipoRecebido) -> {
            switch (tipoRecebido) {
                case "CATEGORIA":
                    saveCommercialCategory(texto);
                    break;
                case "COMPOSICAO":
                    saveGrapeComposition(texto);
                    break;
                case "HARMONIZAÇÃO":
                    saveFoodPairiing(texto);
                    break;
                case "NOTA":
                    saveTastingNote(texto);
                    break;
                case "TIPO":
                    saveWineType(texto);
                    break;
                case "UVA":
                    saveGrape(texto);
                    break;
            }
        });

        dialog.show(getSupportFragmentManager(), "EstoqueDialogConfigActivity");
    }
    private void saveCommercialCategory(String category){
        CommercialCategoryModel model = new CommercialCategoryModel();
        model.setName(category);

        CommercialCategoryDAO dao = new CommercialCategoryDAO(this);
        long id = dao.insert(model);

        if(id != -1){
            Toast.makeText(this, "Categoria salva com sucesso!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Erro: Categoria já existe ou falha ao salvar.", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveGrapeComposition(String composition){
        CompositionTypeModel model = new CompositionTypeModel();
        model.setCompositionName(composition);

        CompositionTypeDAO dao = new CompositionTypeDAO(this);
        long id = dao.insert(model);

        if(id != -1){
            Toast.makeText(this, "Tipo de composição salvo com sucesso!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Erro: Tipo de composição já existe ou falha ao salvar.", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveFoodPairiing(String foodPairing){
        FoodPairingModel model = new FoodPairingModel();
        model.setName(foodPairing);

        FoodPairingDAO dao = new FoodPairingDAO(this);
        long id = dao.insert(model);

        if(id != -1){
            Toast.makeText(this, "Harmonização salva com sucesso!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Erro: Harmonização já existe ou falha ao salvar.", Toast.LENGTH_SHORT).show();
        }
    }
    private void saveTastingNote(String note){
        TastingNoteModel model = new TastingNoteModel();
        model.setNote(note);

        TastingNoteDAO dao = new TastingNoteDAO(this);
        long id = dao.insert(model);

        if(id != -1){
            Toast.makeText(this, "Nota de degustação salva com sucesso!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Erro: Nota de degustação já existe ou falha ao salvar.", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveWineType(String type){
        WineTypeModel model = new WineTypeModel();
        model.setTypeName(type);

        WineTypeDAO dao = new WineTypeDAO(this);
        long id = dao.insert(model);

        if(id != -1){
            Toast.makeText(this, "Tipo de vinho salvo com sucesso!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Erro: Tipo de vinho já existe ou falha ao salvar.", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveGrape(String grape){
        GrapeModel model = new GrapeModel();
        model.setName(grape);

        GrapeDAO dao = new GrapeDAO(this);
        long id = dao.insert(model);

        if(id != -1){
            Toast.makeText(this, "Uva salva com sucesso!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Erro: Uva já existe ou falha ao salvar.", Toast.LENGTH_SHORT).show();
        }
    }
}
