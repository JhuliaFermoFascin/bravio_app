package com.example.vinhedobravioapp.ui.components.vinhos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

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

    private Spinner wineComposition, wineType;
    private EditText wineGrapes, tastingNotes;

    private final String[] uvasDisponiveis = {"Cabernet Sauvignon", "Merlot", "Syrah", "Chardonnay", "Pinot Noir"};
    private final boolean[] uvasSelecionadas = new boolean[uvasDisponiveis.length];
    private final List<String> uvasEscolhidas = new ArrayList<>();

    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_vinho);
        wineDAO = new WineDAO(this);

        context = this;

        // Header e botões principais
        CustomHeaderComponent.configurarHeader(this, getString(R.string.add_new_wine));
        CustomButtonComponent cancel = findViewById(R.id.cancel_btn);
        cancel.setOnClickListener(v -> finish());

        configurarSpinnerTipoVinho();
        configurarNotasDegustacao();
        configurarBotaoFoto();
        configurarComposicaoESelecaoUvas();
    }

    private void configurarSpinnerTipoVinho() {
        wineType = findViewById(R.id.wineType);
        List<String> tiposVinho = Arrays.asList("Tinto", "Branco", "Rosé");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tiposVinho);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wineType.setAdapter(adapter);
    }

    private void configurarNotasDegustacao() {
        EditText tastingNotes = findViewById(R.id.editTextTastingNotes);
        String[] notas = {"Frutado", "Amadeirado", "Herbal", "Especiarias", "Floral"};
        boolean[] checkedItems = new boolean[notas.length];
        List<String> selectedNotas = new ArrayList<>();

        tastingNotes.setOnClickListener(v -> {
            abrirCustomDialogList("Selecione as notas de degustação", notas, true, checkedItems, selectedNotas, () ->{
                tastingNotes.setText(TextUtils.join(", ", selectedNotas));
            });
        });
    }

    private void configurarBotaoFoto() {
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
                            winePhotoButton.setText("");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        winePhotoButton.setOnClickListener(v -> {
            if (selectedImageDrawable == null) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                photoPickerLauncher.launch(intent);
            } else {
                new AlertDialog.Builder(this)
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
            EditText nameEt = findViewById(R.id.editTextWineName);
            EditText wineryEt = findViewById(R.id.editTextWineryName);
            EditText categoryEt = findViewById(R.id.editTextCommercialCategory);
            EditText descriptionEt = findViewById(R.id.editTextDescription);
            EditText vintageEt = findViewById(R.id.editTextHarvest);
            EditText originEt = findViewById(R.id.editTextGeographicOrigin);
            Spinner wineTypeSp = findViewById(R.id.wineType);
            Spinner compositionEt = findViewById(R.id.wineComposition);
            EditText grapesEt = findViewById(R.id.editTextGrapes);
            EditText alcoholEt = findViewById(R.id.editTextAlcoholContent);
            EditText volumeEt = findViewById(R.id.editTextVolume);
            EditText acidityEt = findViewById(R.id.editTextAcidity);
            EditText tempEt = findViewById(R.id.editTextIdealTemperature);
            EditText agingEt = findViewById(R.id.editTextEstimatedStorageTime);
            EditText tastingNotesEt = findViewById(R.id.editTextTastingNotes);
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
//            wine.setCompositionType(compositionEt.getText().toString().trim());
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

    private void configurarComposicaoESelecaoUvas() {
        wineComposition = findViewById(R.id.wineComposition);
        wineGrapes = findViewById(R.id.editTextGrapes);

        List<String> opcoesComposicao = Arrays.asList("Varietal", "Blend");

        ArrayAdapter<String> composicaoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoesComposicao);
        composicaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wineComposition.setAdapter(composicaoAdapter);

        wineComposition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                wineGrapes.setText("");
                uvasEscolhidas.clear();
                Arrays.fill(uvasSelecionadas, false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        wineGrapes.setOnClickListener(v -> {
            String tipoSelecionado = wineComposition.getSelectedItem().toString();
            if (tipoSelecionado.equals("Blend")) abrirSelecaoMultipla();
            else abrirSelecaoUnica();
        });
    }

    private void abrirCustomDialogList(String titulo, String[] itens, boolean multiSelecao, boolean[] selecionadosBoolean, List<String> selecionadosLista, Runnable onConfirmar){
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_cadastro_vinhos, null);

        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        dialogTitle.setText(titulo);

        ListView listView = dialogView.findViewById(R.id.dialogList);
        listView.setChoiceMode(multiSelecao ? ListView.CHOICE_MODE_MULTIPLE : ListView.CHOICE_MODE_SINGLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, multiSelecao ? android.R.layout.simple_list_item_multiple_choice :
                android.R.layout.simple_list_item_single_choice,itens);

        listView.setAdapter(adapter);

        // Marca os itens que já estavam selecionados
        for(int i = 0; i < itens.length; i++){
            if(selecionadosBoolean[i]){
                listView.setItemChecked(i, true);
            }
        }

        // Atualiza a lista de selecionados conforme seleção do usuário
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (multiSelecao) {
                selecionadosBoolean[position] = listView.isItemChecked(position);
                if (selecionadosBoolean[position]) {
                    if (!selecionadosLista.contains(itens[position])) {
                        selecionadosLista.add(itens[position]);
                    }
                } else {
                    selecionadosLista.remove(itens[position]);
                }
            } else {
                Arrays.fill(selecionadosBoolean, false);
                selecionadosBoolean[position] = true;
                selecionadosLista.clear();
                selecionadosLista.add(itens[position]);
            }
        });

        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(dialogView);

        CustomButtonComponent btnConfirmar = dialogView.findViewById(R.id.dialogConfirm);
        CustomButtonComponent btnCancelar = dialogView.findViewById(R.id.dialogCancel);

        btnConfirmar.setOnClickListener(v -> {
            onConfirmar.run();
            dialog.dismiss();
        });

        btnCancelar.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void abrirSelecaoMultipla() {
        abrirCustomDialogList("Selecione as uvas do blend", uvasDisponiveis, true, uvasSelecionadas, uvasEscolhidas, () ->{
            if(!uvasEscolhidas.isEmpty()){
                String todasUvas = TextUtils.join(", ", uvasEscolhidas);
                wineGrapes.setText(todasUvas);
            }else{
                Toast.makeText(context, "Selecione pelo menos uma uva", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void abrirSelecaoUnica() {
        abrirCustomDialogList("Selecione a uva", uvasDisponiveis, false, new boolean[uvasDisponiveis.length], uvasEscolhidas, () -> {
            if(!uvasEscolhidas.isEmpty()){
                wineGrapes.setText(uvasEscolhidas.get(0));
            }else{
                Toast.makeText(context, "Nenhuma uva selecionada", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
