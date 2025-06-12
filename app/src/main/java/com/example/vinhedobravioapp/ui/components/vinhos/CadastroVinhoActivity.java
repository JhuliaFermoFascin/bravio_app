package com.example.vinhedobravioapp.ui.components.vinhos;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.components.CustomButtonComponent;
import com.example.vinhedobravioapp.components.CustomHeaderComponent;
import com.example.vinhedobravioapp.database.dao.WineDAO;
import com.example.vinhedobravioapp.database.model.WineModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CadastroVinhoActivity extends AppCompatActivity {

    private MaterialButton winePhotoButton;
    private Drawable selectedImageDrawable = null;
    private WineDAO wineDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_vinho);
        wineDAO = new WineDAO(this);

        CustomHeaderComponent.configurarHeader(this, getString(R.string.add_new_wine));

        CustomButtonComponent cancel = findViewById(R.id.cancel_btn);
        cancel.setOnClickListener(v -> finish());

        // Setup do Spinner de tipo de vinho
        Spinner wineType = findViewById(R.id.wineType);
        List<String> tiposVinho = Arrays.asList("Tinto", "Branco", "Rosé");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                tiposVinho
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wineType.setAdapter(adapter);

        // Setup do campo de notas de degustação (multiseleção)
        EditText tastingNotes = findViewById(R.id.tastingNotes);

        String[] notas = {"Frutado", "Amadeirado", "Herbal", "Especiarias", "Floral"};
        boolean[] checkedItems = new boolean[notas.length];
        List<String> selectedNotas = new ArrayList<>();

        tastingNotes.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(CadastroVinhoActivity.this);
            builder.setTitle("Selecione as notas de degustação");

            builder.setMultiChoiceItems(notas, checkedItems, (dialog, which, isChecked) -> {
                if (isChecked) {
                    selectedNotas.add(notas[which]);
                } else {
                    selectedNotas.remove(notas[which]);
                }
            });

            builder.setPositiveButton("OK", (dialog, which) -> {
                tastingNotes.setText(TextUtils.join(", ", selectedNotas));
            });

            builder.setNegativeButton("Cancelar", null);
            builder.show();
        });

        // Botão para selecionar imagem (foto)
        winePhotoButton = findViewById(R.id.winePhoto);

        ActivityResultLauncher<Intent> photoPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImage = result.getData().getData();
                        try {
                            Drawable imageDrawable = Drawable.createFromStream(
                                    getContentResolver().openInputStream(selectedImage),
                                    selectedImage.toString()
                            );
                            selectedImageDrawable = imageDrawable;
                            winePhotoButton.setIcon(selectedImageDrawable);
                            winePhotoButton.setText(""); // remover texto quando mostrar imagem
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        winePhotoButton.setOnClickListener(v -> {
            if (selectedImageDrawable == null) {
                // Se não tem imagem selecionada, abre seletor
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                photoPickerLauncher.launch(intent);
            } else {
                // Se já tem imagem, pergunta se quer remover
                new AlertDialog.Builder(CadastroVinhoActivity.this)
                        .setTitle("Remover foto")
                        .setMessage("Deseja remover a foto selecionada?")
                        .setPositiveButton("Sim", (dialog, which) -> {
                            selectedImageDrawable = null;
                            winePhotoButton.setIcon(getResources().getDrawable(R.drawable.icon_photo, null));
                        })
                        .setNegativeButton("Não", null)
                        .show();
            }
        });

        CustomButtonComponent saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(v -> {
            Log.d("WineDAO", "Botão salvar clicado");
            // Recupera os campos do formulário
            EditText nameEt = findViewById(R.id.editTextWineName); // Adapte o id conforme seu XML
            EditText wineryEt = findViewById(R.id.editTextWineryName);
            EditText categoryEt = findViewById(R.id.editTextCommercialCategory);
            EditText descriptionEt = findViewById(R.id.editTextDescription);
            EditText vintageEt = findViewById(R.id.editTextHarvest);
            EditText originEt = findViewById(R.id.editTextGeographicOrigin);
            Spinner wineTypeSp = findViewById(R.id.wineType);
            EditText compositionEt = findViewById(R.id.editTextComposition);
            EditText grapesEt = findViewById(R.id.editTextGrapes);
            EditText alcoholEt = findViewById(R.id.editTextAlcoholContent);
            EditText volumeEt = findViewById(R.id.editTextVolume);
            EditText acidityEt = findViewById(R.id.editTextAcidity);
            EditText tempEt = findViewById(R.id.editTextIdealTemperature);
            EditText agingEt = findViewById(R.id.editTextEstimatedStorageTime);
            EditText tastingNotesEt = findViewById(R.id.tastingNotes);
            EditText foodPairingsEt = findViewById(R.id.editTextFoodPairings);
            // ... outros campos conforme necessário

            // Validação simples
            String name = nameEt.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Nome do vinho é obrigatório", Toast.LENGTH_SHORT).show();
                return;
            }

            // Monta o modelo (ajuste conforme seu construtor/getters/setters)
            WineModel wine = new WineModel();
            wine.setName(name);
            wine.setWineryId(0L); // Long
            wine.setWineTypeId((long) wineTypeSp.getSelectedItemPosition()); // long
            wine.setCommercialCategoryId(0L); // Long
            wine.setOriginId(0L); // Long
            wine.setVintage(vintageEt.getText().toString().trim());
            wine.setDescription(descriptionEt.getText().toString().trim());
            wine.setCompositionType(compositionEt.getText().toString().trim());
            wine.setTastingNoteId(0L); // Long
            wine.setFoodPairings(foodPairingsEt.getText().toString().trim());
            try {
                wine.setAlcoholContent(Double.valueOf(alcoholEt.getText().toString().trim()));
            } catch (Exception e) { wine.setAlcoholContent(null); }
            try {
                wine.setVolume(Integer.valueOf(volumeEt.getText().toString().trim()));
            } catch (Exception e) { wine.setVolume(null); }
            wine.setGrapeId(0L); // Long
            wine.setAcidity(acidityEt.getText().toString().trim());
            try {
                wine.setIdealTemperatureCelsius(Double.valueOf(tempEt.getText().toString().trim()));
            } catch (Exception e) { wine.setIdealTemperatureCelsius(null); }
            wine.setAgingPotential(agingEt.getText().toString().trim());
            // ... outros campos

            long id = wineDAO.insert(wine);
            if (id > 0) {
                Toast.makeText(this, "Vinho cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                // Log do getAll para debug
                List<WineModel> allWines = wineDAO.getAll();
                for (WineModel w : allWines) {
                    Log.d("WineDAO", "Wine: " + w.getWineId() + " - " + w.getName());
                }

                finish();
            } else {
                Toast.makeText(this, "Erro ao cadastrar vinho.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
