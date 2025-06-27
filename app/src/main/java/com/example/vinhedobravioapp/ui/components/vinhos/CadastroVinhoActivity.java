package com.example.vinhedobravioapp.ui.components.vinhos;

import android.content.Context;
import android.content.Intent;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;

import com.example.vinhedobravioapp.database.dao.FoodPairingDAO;
import com.example.vinhedobravioapp.database.dao.GeographicOriginDAO;
import com.example.vinhedobravioapp.database.dao.GrapeDAO;
import com.example.vinhedobravioapp.database.dao.TastingNoteDAO;
import com.example.vinhedobravioapp.database.dao.WineDAO;
import com.example.vinhedobravioapp.database.dao.WineFoodPairingDAO;
import com.example.vinhedobravioapp.database.dao.WineGrapeDAO;
import com.example.vinhedobravioapp.database.dao.WineImageDAO;
import com.example.vinhedobravioapp.database.dao.WineReviewDAO;
import com.example.vinhedobravioapp.database.dao.WineTastingNoteDAO;
import com.example.vinhedobravioapp.database.dao.WineryDAO;
import com.example.vinhedobravioapp.database.model.CommercialCategoryModel;
import com.example.vinhedobravioapp.database.model.CompositionTypeModel;
import com.example.vinhedobravioapp.database.model.FoodPairingModel;
import com.example.vinhedobravioapp.database.model.GeographicOriginModel;
import com.example.vinhedobravioapp.database.model.GrapeModel;
import com.example.vinhedobravioapp.database.model.TastingNoteModel;
import com.example.vinhedobravioapp.database.model.WineFoodPairingModel;
import com.example.vinhedobravioapp.database.model.WineGrapeModel;
import com.example.vinhedobravioapp.database.model.WineImageModel;
import com.example.vinhedobravioapp.database.model.WineModel;
import com.example.vinhedobravioapp.database.model.WineReviewModel;
import com.example.vinhedobravioapp.database.model.WineTastingNoteModel;
import com.example.vinhedobravioapp.database.model.WineTypeModel;
import com.example.vinhedobravioapp.database.model.WineryModel;
import com.example.vinhedobravioapp.ui.components.helper.CustomButtonHelper;
import com.example.vinhedobravioapp.ui.components.helper.HeaderHelper;
import com.example.vinhedobravioapp.ui.components.vinhos.helpers.GeographicOriginAutoCompleteHelper;
import com.example.vinhedobravioapp.ui.components.vinhos.helpers.SpinnerConfigHelper;
import com.example.vinhedobravioapp.ui.components.vinhos.helpers.WineDataHelper;
import com.example.vinhedobravioapp.ui.components.vinhos.helpers.WineImageHelper;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CadastroVinhoActivity extends AppCompatActivity {

    private MaterialButton winePhotoButton;
    private CustomButtonHelper save_btn;
    private Spinner wineComposition, wineType, addCommercialCategory;
    private EditText wineName, wineDescription, wineHarvest,
            alcoholContent, volume, acidity, temperature,
            storageTime, unitPrice, quantity;
    private EditText wineGrapes, tastingNotes, foodPairings, reviews;
    private AutoCompleteTextView wineryName, campoRegiao, countryOrigin;

    private String[] uvasDisponiveis;
    private boolean[] uvasSelecionadas;
    private final List<String> uvasEscolhidas = new ArrayList<>();

    private List<CommercialCategoryModel> listaCommercialCategoryModels = new ArrayList<>();
    private List<WineTypeModel> wineTypeList = new ArrayList<>();
    private List<CompositionTypeModel> listCompositionTypes = new ArrayList<>();

    private List<GrapeModel> grapeList;

    private Map<String, Long> grapeNameToIdMap = new HashMap<>();

    private Drawable selectedImageDrawable = null;
    private long editingWineId = -1;

    private List<String> selectedNotas = new ArrayList<>();
    private boolean[] checkedItemsNotas;

    private List<String> selecionadasHarmonias = new ArrayList<>();
    private boolean[] checkedItemsHarmonias;
    private boolean isEditing = false;

    private final List<String> notasDisponiveis = new ArrayList<>();
    private final List<String> harmoniasDisponiveis = new ArrayList<>();

    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estoque_cadastro_vinho);

        context = this;

        carregarUvasDoBanco();
        configurarCampoSafra();
        configurarCampoPais();

        int tipoUsuario = getIntent().getIntExtra(getString(R.string.tipo_usuario_input), -1);

        HeaderHelper.configurarHeader(this, getString(R.string.wine), tipoUsuario, true, false, false);
        CustomButtonHelper cancel = findViewById(R.id.cancel_btn);
        cancel.setOnClickListener(v -> finish());

        wineType = findViewById(R.id.wineType);
        wineComposition = findViewById(R.id.wineComposition);
        tastingNotes = findViewById(R.id.tastingNotes);
        wineGrapes = findViewById(R.id.wineGrapes);
        addCommercialCategory = findViewById(R.id.commercialCategory);

        listaCommercialCategoryModels = SpinnerConfigHelper.configurarCategoriaComercial(addCommercialCategory, this);
        wineTypeList = SpinnerConfigHelper.configurarTipoVinho(wineType, this);
        listCompositionTypes = SpinnerConfigHelper.configurarComposicao(wineComposition, this);

        configurarNotasDegustacao();
        configurarBotaoFoto();
        configurarComposicaoESelecaoUvas();
        configurarHarmonizacoes();

        wineName = findViewById(R.id.wineName);
        wineryName = findViewById(R.id.wineryName);
        wineDescription = findViewById(R.id.wineDescription);
        wineHarvest = findViewById(R.id.wineHarvest);
        countryOrigin = findViewById(R.id.countryOrigin);
        campoRegiao = findViewById(R.id.campoRegiao);
        alcoholContent = findViewById(R.id.alcoholContent);
        volume = findViewById(R.id.volume);
        acidity = findViewById(R.id.acidity);
        temperature = findViewById(R.id.temperature);
        storageTime = findViewById(R.id.storageTime);
        foodPairings = findViewById(R.id.foodPairings);
        reviews = findViewById(R.id.reviews);
        unitPrice = findViewById(R.id.unitPrice);
        quantity = findViewById(R.id.quantity);

        save_btn = findViewById(R.id.save_btn);

        Intent intent = getIntent();
        if (intent.hasExtra("wine_id")) {
            editingWineId = intent.getLongExtra("wine_id", -1);

            HeaderHelper.configurarHeader(this, getString(R.string.update_wine), tipoUsuario, true, false, false);
            ((CustomButtonHelper) save_btn).setBtnText(getString(R.string.update));
            carregarDadosParaEdicao(editingWineId);
        }

        WineryDAO wineryDAO = new WineryDAO(this);
        List<WineryModel> vinicolas = wineryDAO.getAll();

        List<String> nomes = new ArrayList<>();
        for (WineryModel w : vinicolas) {
            nomes.add(w.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, nomes
        );

        wineryName.setAdapter(adapter);
        wineryName.setThreshold(1);

        GeographicOriginAutoCompleteHelper.setup(this, countryOrigin, campoRegiao);

        save_btn.setOnClickListener(v -> {
            if (editingWineId >= 0) {
                updateWine();
            } else {
                saveWine();
            }
        });
    }

    private void saveWine() {
        WineModel wineModel = new WineModel();
        wineModel.setName(wineName.getText().toString());

        String winery = wineryName.getText().toString().trim();
        WineryDAO wineryDAO = new WineryDAO(this);
        long wineryId = wineryDAO.getIdByName(winery);
        if (wineryId == -1) {
            WineryModel newWinery = new WineryModel();
            newWinery.setName(winery);
            wineryId = wineryDAO.insert(newWinery);
        }
        wineModel.setWineryId(wineryId);

        if (addCommercialCategory.getSelectedItemPosition() >= 0) {
            long catComercialId = listaCommercialCategoryModels
                    .get(addCommercialCategory.getSelectedItemPosition())
                    .getCategoryId();
            wineModel.setCommercialCategoryId(catComercialId);
        } else {
            Toast.makeText(this, "Selecione uma categoria comercial válida!", Toast.LENGTH_SHORT).show();
            return;
        }

        String pais = countryOrigin.getText().toString().trim();
        String regiao = campoRegiao.getText().toString().trim();

        GeographicOriginDAO originDAO = new GeographicOriginDAO(this);
        long originId = originDAO.getIdByCountryAndRegion(pais, regiao);

        if (originId == -1) {
            GeographicOriginModel novaOrigem = new GeographicOriginModel();
            novaOrigem.setCountry(pais);
            novaOrigem.setRegion(regiao);
            originId = originDAO.insert(novaOrigem);
        }

        wineModel.setOriginId(originId);

        if (wineType.getSelectedItemPosition() >= 0) {
            long wineTypeId = wineTypeList.get(wineType.getSelectedItemPosition()).getWineTypeId();
            wineModel.setWineTypeId(wineTypeId);
        } else {
            Toast.makeText(this, "Selecione um tipo de vinho!", Toast.LENGTH_SHORT).show();
            return;
        }

        wineModel.setVintage(wineHarvest.getText().toString());
        wineModel.setDescription(wineDescription.getText().toString());

        if (wineComposition.getSelectedItemPosition() >= 0) {
            long compositionTypeId = listCompositionTypes.get(wineComposition.getSelectedItemPosition()).getCompositionTypeId();
            wineModel.setCompositionTypeId(compositionTypeId);
        } else {
            Toast.makeText(this, "Selecione um tipo de composição!", Toast.LENGTH_SHORT).show();
            return;
        }

        wineModel.setAlcoholContent(Double.parseDouble(alcoholContent.getText().toString()));
        wineModel.setVolume(Integer.parseInt(volume.getText().toString()));
        wineModel.setAcidity(acidity.getText().toString());
        wineModel.setIdealTemperatureCelsius(Double.parseDouble(temperature.getText().toString()));
        wineModel.setAgingPotential(storageTime.getText().toString());
        wineModel.setUnitPrice(Double.parseDouble(unitPrice.getText().toString()));

        WineDAO wineDAO = new WineDAO(this);
        long wineId = wineDAO.insert(wineModel);

        if (wineId == -1) {
            Toast.makeText(this, "Erro ao salvar vinho!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!uvasEscolhidas.isEmpty()) {
            WineGrapeDAO wineGrapeDAO = new WineGrapeDAO(this);
            for (String nomeUva : uvasEscolhidas) {
                Long grapeId = grapeNameToIdMap.get(nomeUva);
                if (grapeId != null) {
                    WineGrapeModel wg = new WineGrapeModel();
                    wg.setWineId(wineId);
                    wg.setGrapeId(grapeId);
                    long relId = wineGrapeDAO.insert(wg);
                    if (relId == -1) {
                        Toast.makeText(this,
                                "Vinho salvo, mas falha ao salvar uva " + nomeUva,
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        }

        String notasTexto = tastingNotes.getText().toString().trim();
        if (!notasTexto.isEmpty()) {
            List<String> notasSelecionadas = Arrays.asList(notasTexto.split(",\\s*"));

            TastingNoteDAO tastingNoteDAO = new TastingNoteDAO(this);
            WineTastingNoteDAO wineTastingNoteDAO = new WineTastingNoteDAO(this);

            for (String nota : notasSelecionadas) {
                long noteId = tastingNoteDAO.getIdByNoteText(nota);
                if (noteId != -1) {
                    WineTastingNoteModel rel = new WineTastingNoteModel();
                    rel.setWineId(wineId);
                    rel.setTastingNoteId(noteId);
                    long res = wineTastingNoteDAO.insert(rel);
                    if (res == -1) {
                        Toast.makeText(this, "Falha ao salvar nota: " + nota, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }


        if (selectedImageDrawable != null) {
            String imageBase64 = WineImageHelper.converterDrawableParaBase64Jpeg(selectedImageDrawable);
            if (imageBase64 != null && !imageBase64.isEmpty()) {
                WineImageModel imgModel = new WineImageModel();
                imgModel.setWineId(wineId);
                imgModel.setImageBase64(imageBase64);

                WineImageDAO imgDAO = new WineImageDAO(this);
                long imgId = imgDAO.insert(imgModel);
                if (imgId == -1) {
                    Toast.makeText(this,
                            "Vinho salvo, mas falha ao salvar imagem.",
                            Toast.LENGTH_LONG).show();
                }
            }
        }

        String reviewText = reviews.getText().toString().trim();
        if (!reviewText.isEmpty()) {
            WineReviewModel reviewModel = new WineReviewModel();
            reviewModel.setWineId(wineId);
            reviewModel.setReviewText(reviewText);
            WineReviewDAO wineReviewDAO = new WineReviewDAO(this);
            long reviewId = wineReviewDAO.insert(reviewModel);
            if (reviewId == -1) {
                Toast.makeText(this,
                        "Vinho salvo, mas falha ao salvar avaliação.",
                        Toast.LENGTH_LONG).show();
            }
        }

        Toast.makeText(this, "Cadastro concluído com sucesso!", Toast.LENGTH_SHORT).show();
        finish();

        String harmonizacoesTexto = foodPairings.getText().toString().trim();
        if (!harmonizacoesTexto.isEmpty()) {
            List<String> harmonizacoesSelecionadas = Arrays.asList(harmonizacoesTexto.split(",\\s*"));

            FoodPairingDAO foodPairingDAO = new FoodPairingDAO(this);
            WineFoodPairingDAO wineFoodPairingDAO = new WineFoodPairingDAO(this);

            for (String harmonizacao : harmonizacoesSelecionadas) {
                long harmonizacaoId = foodPairingDAO.getIdByName(harmonizacao);
                if (harmonizacaoId != -1) {
                    WineFoodPairingModel rel = new WineFoodPairingModel();
                    rel.setWineId(wineId);
                    rel.setFoodPairingId(harmonizacaoId);
                    long res = wineFoodPairingDAO.insert(rel);
                    if (res == -1) {
                        Toast.makeText(this, "Falha ao salvar harmonização: " + harmonizacao, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
    private void updateWine() {
        WineDAO wineDAO = new WineDAO(this);
        WineModel wineModel = wineDAO.getById(editingWineId);

        if (wineModel == null) {
            Toast.makeText(this, "Erro ao encontrar vinho para edição!", Toast.LENGTH_SHORT).show();
            return;
        }

        wineModel.setName(wineName.getText().toString());
        wineModel.setDescription(wineDescription.getText().toString());
        wineModel.setVintage(wineHarvest.getText().toString());

        WineryDAO wineryDAO = new WineryDAO(this);
        String winery = wineryName.getText().toString().trim();
        long wineryId = wineryDAO.getIdByName(winery);
        if (wineryId == -1) {
            WineryModel newWinery = new WineryModel();
            newWinery.setName(winery);
            wineryId = wineryDAO.insert(newWinery);
        }
        wineModel.setWineryId(wineryId);

        wineModel.setCommercialCategoryId(
                listaCommercialCategoryModels.get(addCommercialCategory.getSelectedItemPosition()).getCategoryId()
        );

        wineModel.setCompositionTypeId(
                listCompositionTypes.get(wineComposition.getSelectedItemPosition()).getCompositionTypeId()
        );

        wineModel.setWineTypeId(
                wineTypeList.get(wineType.getSelectedItemPosition()).getWineTypeId()
        );

        GeographicOriginDAO originDAO = new GeographicOriginDAO(this);
        String pais = countryOrigin.getText().toString().trim();
        String regiao = campoRegiao.getText().toString().trim();
        long originId = originDAO.getIdByCountryAndRegion(pais, regiao);
        if (originId == -1) {
            GeographicOriginModel novaOrigem = new GeographicOriginModel();
            novaOrigem.setCountry(pais);
            novaOrigem.setRegion(regiao);
            originId = originDAO.insert(novaOrigem);
        }
        wineModel.setOriginId(originId);

        wineModel.setAlcoholContent(Double.parseDouble(alcoholContent.getText().toString()));
        wineModel.setVolume(Integer.parseInt(volume.getText().toString()));
        wineModel.setAcidity(acidity.getText().toString());
        wineModel.setIdealTemperatureCelsius(Double.parseDouble(temperature.getText().toString()));
        wineModel.setAgingPotential(storageTime.getText().toString());
        wineModel.setUnitPrice(Double.parseDouble(unitPrice.getText().toString()));

        // Atualiza o vinho principal
        int updatedRows = wineDAO.update(wineModel);
        if (updatedRows > 0) {
            // LIMPA vínculos antigos
            WineGrapeDAO wineGrapeDAO = new WineGrapeDAO(this);
            wineGrapeDAO.deleteByWineId(wineModel.getWineId());

            WineTastingNoteDAO wineTastingNoteDAO = new WineTastingNoteDAO(this);
            wineTastingNoteDAO.deleteByWineId(wineModel.getWineId());

            WineFoodPairingDAO wineFoodPairingDAO = new WineFoodPairingDAO(this);
            wineFoodPairingDAO.deleteByWineId(wineModel.getWineId());

            // INSERE vínculos atualizados
            for (String nomeUva : uvasEscolhidas) {
                Long grapeId = grapeNameToIdMap.get(nomeUva);
                if (grapeId != null) {
                    WineGrapeModel wg = new WineGrapeModel();
                    wg.setWineId(wineModel.getWineId());
                    wg.setGrapeId(grapeId);
                    wineGrapeDAO.insert(wg);
                }
            }

            String notasTexto = tastingNotes.getText().toString().trim();
            if (!notasTexto.isEmpty()) {
                List<String> notasSelecionadas = Arrays.asList(notasTexto.split(",\\s*"));
                TastingNoteDAO tnDAO = new TastingNoteDAO(this);
                for (String nota : notasSelecionadas) {
                    long noteId = tnDAO.getIdByNoteText(nota);
                    if (noteId != -1) {
                        WineTastingNoteModel rel = new WineTastingNoteModel();
                        rel.setWineId(wineModel.getWineId());
                        rel.setTastingNoteId(noteId);
                        wineTastingNoteDAO.insert(rel);
                    }
                }
            }

            String harmonizacoesTexto = foodPairings.getText().toString().trim();
            if (!harmonizacoesTexto.isEmpty()) {
                List<String> harmonizacoesSelecionadas = Arrays.asList(harmonizacoesTexto.split(",\\s*"));
                FoodPairingDAO foodDAO = new FoodPairingDAO(this);
                for (String h : harmonizacoesSelecionadas) {
                    long id = foodDAO.getIdByName(h);
                    if (id != -1) {
                        WineFoodPairingModel rel = new WineFoodPairingModel();
                        rel.setWineId(wineModel.getWineId());
                        rel.setFoodPairingId(id);
                        wineFoodPairingDAO.insert(rel);
                    }
                }
            }

            Toast.makeText(this, "Vinho atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao atualizar vinho!", Toast.LENGTH_SHORT).show();
        }


        // Atualiza uvas (deleta tudo e insere novamente)
        WineGrapeDAO wineGrapeDAO = new WineGrapeDAO(this);
        wineGrapeDAO.deleteByWineId(editingWineId);
        for (String nomeUva : uvasEscolhidas) {
            Long grapeId = grapeNameToIdMap.get(nomeUva);
            if (grapeId != null) {
                WineGrapeModel wg = new WineGrapeModel();
                wg.setWineId(editingWineId);
                wg.setGrapeId(grapeId);
                wineGrapeDAO.insert(wg);
            }
        }

        // Atualiza notas de degustação
        WineTastingNoteDAO wineTastingNoteDAO = new WineTastingNoteDAO(this);
        wineTastingNoteDAO.deleteByWineId(editingWineId);

        TastingNoteDAO tastingNoteDAO = new TastingNoteDAO(this);
        String notasTexto = tastingNotes.getText().toString().trim();
        if (!notasTexto.isEmpty()) {
            List<String> notasSelecionadas = Arrays.asList(notasTexto.split(",\\s*"));
            for (String nota : notasSelecionadas) {
                long noteId = tastingNoteDAO.getIdByNoteText(nota);
                if (noteId != -1) {
                    WineTastingNoteModel rel = new WineTastingNoteModel();
                    rel.setWineId(editingWineId);
                    rel.setTastingNoteId(noteId);
                    wineTastingNoteDAO.insert(rel);
                }
            }
        }

        // Atualiza harmonizações
        WineFoodPairingDAO wineFoodPairingDAO = new WineFoodPairingDAO(this);
        wineFoodPairingDAO.deleteByWineId(editingWineId);

        FoodPairingDAO foodPairingDAO = new FoodPairingDAO(this);
        String harmonizacoesTexto = foodPairings.getText().toString().trim();
        if (!harmonizacoesTexto.isEmpty()) {
            List<String> harmonizacoesSelecionadas = Arrays.asList(harmonizacoesTexto.split(",\\s*"));
            for (String harmonizacao : harmonizacoesSelecionadas) {
                long harmonizacaoId = foodPairingDAO.getIdByName(harmonizacao);
                if (harmonizacaoId != -1) {
                    WineFoodPairingModel rel = new WineFoodPairingModel();
                    rel.setWineId(editingWineId);
                    rel.setFoodPairingId(harmonizacaoId);
                    wineFoodPairingDAO.insert(rel);
                }
            }
        }

        // Atualiza avaliação
        WineReviewDAO wineReviewDAO = new WineReviewDAO(this);
        wineReviewDAO.deleteByWineId(editingWineId);
        String reviewText = reviews.getText().toString().trim();
        if (!reviewText.isEmpty()) {
            WineReviewModel reviewModel = new WineReviewModel();
            reviewModel.setWineId(editingWineId);
            reviewModel.setReviewText(reviewText);
            wineReviewDAO.insert(reviewModel);
        }

        // Atualiza imagem (deleta anterior e salva nova se existir)
        if (selectedImageDrawable != null) {
            WineImageDAO imgDAO = new WineImageDAO(this);
            imgDAO.deleteByWineId(editingWineId);
            String imageBase64 = WineImageHelper.converterDrawableParaBase64Jpeg(selectedImageDrawable);
            if (imageBase64 != null && !imageBase64.isEmpty()) {
                WineImageModel imgModel = new WineImageModel();
                imgModel.setWineId(editingWineId);
                imgModel.setImageBase64(imageBase64);
                imgDAO.insert(imgModel);
            }
        }

        Toast.makeText(this, "Vinho atualizado com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }
    private void carregarUvasDoBanco() {
        GrapeDAO dao = new GrapeDAO(this);
        grapeList = dao.getAll();

        uvasDisponiveis = new String[grapeList.size()];
        grapeNameToIdMap = new HashMap<>();

        for (int i = 0; i < grapeList.size(); i++) {
            GrapeModel gm = grapeList.get(i);
            uvasDisponiveis[i] = gm.getName();
            grapeNameToIdMap.put(gm.getName(), gm.getGrapeId());
        }

        uvasSelecionadas = new boolean[uvasDisponiveis.length];
        Arrays.fill(uvasSelecionadas, false);
    }
    private void carregarDadosParaEdicao(long wineId) {
        isEditing = true;

        WineDAO wineDAO = new WineDAO(this);
        WineModel wine = wineDAO.getById(wineId);
        if (wine == null) return;

        WineDataHelper.WineCompleteData data = WineDataHelper.getCompleteData(this, wine);

        wineName.setText(data.wine.getName());
        wineDescription.setText(data.wine.getDescription());
        wineHarvest.setText(data.wine.getVintage());
        wineGrapes.setText(data.grapesConcatenated);
        alcoholContent.setText(String.valueOf(data.wine.getAlcoholContent()));
        volume.setText(String.valueOf(data.wine.getVolume()));
        acidity.setText(data.wine.getAcidity());
        temperature.setText(String.valueOf(data.wine.getIdealTemperatureCelsius()));
        storageTime.setText(data.wine.getAgingPotential());
        tastingNotes.setText(data.tastingNotesConcatenated);
        foodPairings.setText(data.foodPairingsConcatenated);
        reviews.setText(data.wineReview != null ? data.wineReview.getReviewText() : "");
        unitPrice.setText(String.valueOf(data.wine.getUnitPrice()));

        wineryName.setText(data.winery.getName());
        campoRegiao.setText(data.geographicOrigin.getRegion());
        countryOrigin.setText(data.geographicOrigin.getCountry());

        for (int i = 0; i < wineComposition.getCount(); i++) {
            if (listCompositionTypes.get(i).getCompositionTypeId() == data.compositionType.getCompositionTypeId()) {
                wineComposition.setSelection(i);
                break;
            }
        }

        for (int i = 0; i < addCommercialCategory.getCount(); i++) {
            if (listaCommercialCategoryModels.get(i).getCategoryId() == data.commercialCategory.getCategoryId()) {
                addCommercialCategory.setSelection(i);
                break;
            }
        }

        for (int i = 0; i < wineType.getCount(); i++) {
            if (wineTypeList.get(i).getWineTypeId() == data.wine.getWineTypeId()) {
                wineType.setSelection(i);
                break;
            }
        }

        if (data.wineImageBitmap != null) {
            selectedImageDrawable = new BitmapDrawable(getResources(), data.wineImageBitmap);
            winePhotoButton.setBackground(selectedImageDrawable);
        }

        uvasEscolhidas.clear();
        uvasEscolhidas.addAll(data.grapeNames);

        uvasSelecionadas = new boolean[uvasDisponiveis.length];
        for (int i = 0; i < uvasDisponiveis.length; i++) {
            if (uvasEscolhidas.contains(uvasDisponiveis[i])) {
                uvasSelecionadas[i] = true;
            }
        }

        if (notasDisponiveis.isEmpty()) {
            TastingNoteDAO dao = new TastingNoteDAO(this);
            List<TastingNoteModel> notasDB = dao.getAll();
            for (TastingNoteModel nota : notasDB) {
                notasDisponiveis.add(nota.getNote());
            }
        }

        if (harmoniasDisponiveis.isEmpty()) {
            FoodPairingDAO dao = new FoodPairingDAO(this);
            List<FoodPairingModel> harmonizacoesDB = dao.getAll();
            for (FoodPairingModel h : harmonizacoesDB) {
                harmoniasDisponiveis.add(h.getName());
            }
        }

        selectedNotas.clear();
        selectedNotas.addAll(data.tastingNotes);

        checkedItemsNotas = new boolean[notasDisponiveis.size()];
        for (int i = 0; i < notasDisponiveis.size(); i++) {
            checkedItemsNotas[i] = selectedNotas.contains(notasDisponiveis.get(i));
        }

        selecionadasHarmonias.clear();
        selecionadasHarmonias.addAll(data.foodPairings);

        checkedItemsHarmonias = new boolean[harmoniasDisponiveis.size()];
        for (int i = 0; i < harmoniasDisponiveis.size(); i++) {
            checkedItemsHarmonias[i] = selecionadasHarmonias.contains(harmoniasDisponiveis.get(i));
        }

        isEditing = false;
    }
    private void configurarComposicaoESelecaoUvas() {
        wineComposition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String tipoSelecionado = wineComposition.getSelectedItem().toString();
                if (!tipoSelecionado.equalsIgnoreCase("Blend")) {
                    if (!isEditing) {
                        wineGrapes.setText("");
                        uvasEscolhidas.clear();
                        uvasSelecionadas = new boolean[uvasDisponiveis.length];
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });



        wineGrapes.setOnClickListener(v -> {
            String tipoSelecionado = wineComposition.getSelectedItem().toString();
            if (tipoSelecionado.equalsIgnoreCase("Blend")) {
                abrirSelecaoMultipla();
            } else {
                abrirSelecaoUnica();
            }
        });
    }
    private void configurarNotasDegustacao() {
        tastingNotes = findViewById(R.id.tastingNotes);

        TastingNoteDAO dao = new TastingNoteDAO(context);
        List<TastingNoteModel> notasDB = dao.getAll();
        notasDisponiveis.clear();
        for (TastingNoteModel nota : notasDB) {
            notasDisponiveis.add(nota.getNote());
        }

        tastingNotes.setOnClickListener(v -> {
            checkedItemsNotas = new boolean[notasDisponiveis.size()];
            for (int i = 0; i < notasDisponiveis.size(); i++) {
                checkedItemsNotas[i] = selectedNotas.contains(notasDisponiveis.get(i));
            }

            String[] notasArray = notasDisponiveis.toArray(new String[0]);

            abrirCustomDialogList(
                    "Selecione as notas de degustação",
                    notasArray,
                    true,
                    checkedItemsNotas,
                    selectedNotas,
                    () -> tastingNotes.setText(TextUtils.join(", ", selectedNotas))
            );
        });
    }
    private void configurarHarmonizacoes() {
        foodPairings = findViewById(R.id.foodPairings);

        // Carrega do banco e popula a lista global
        FoodPairingDAO dao = new FoodPairingDAO(context);
        List<FoodPairingModel> harmonizacoesDB = dao.getAll();
        harmoniasDisponiveis.clear();
        for (FoodPairingModel h : harmonizacoesDB) {
            harmoniasDisponiveis.add(h.getName());
        }

        foodPairings.setOnClickListener(v -> {
            checkedItemsHarmonias = new boolean[harmoniasDisponiveis.size()];
            for (int i = 0; i < harmoniasDisponiveis.size(); i++) {
                checkedItemsHarmonias[i] = selecionadasHarmonias.contains(harmoniasDisponiveis.get(i));
            }

            String[] harmoniasArray = harmoniasDisponiveis.toArray(new String[0]);

            abrirCustomDialogList(
                    "Selecione as harmonizações",
                    harmoniasArray,
                    true,
                    checkedItemsHarmonias,
                    selecionadasHarmonias,
                    () -> foodPairings.setText(TextUtils.join(", ", selecionadasHarmonias))
            );
        });
    }
    private void configurarCampoSafra() {
        EditText wineHarvest = findViewById(R.id.wineHarvest);
        wineHarvest.setFocusable(false);
        wineHarvest.setClickable(true);

        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        List<String> anos = new ArrayList<>();
        for (int i = anoAtual; i >= 1980; i--) {
            anos.add(String.valueOf(i));
        }
        final String[] anosArray = anos.toArray(new String[0]);

        wineHarvest.setOnClickListener(v -> {
            new AlertDialog.Builder(this, R.style.HarvestDialog)
                    .setTitle("Selecione o ano da safra")
                    .setSingleChoiceItems(anosArray, -1, (dialog, which) -> {
                        wineHarvest.setText(anosArray[which]);
                        dialog.dismiss();
                    })
                    .show();
        });
    }
    private void configurarCampoPais() {
        EditText countryOriginEdit = findViewById(R.id.countryOrigin);
        EditText campoRegiaoEdit = findViewById(R.id.campoRegiao);
        countryOriginEdit.setFocusable(false);
        countryOriginEdit.setClickable(true);
        GeographicOriginDAO originDAO = new GeographicOriginDAO(this);
        List<GeographicOriginModel> allOrigins = originDAO.getAll();
        List<String> paises = new ArrayList<>();
        for (GeographicOriginModel origin : allOrigins) {
            if (!paises.contains(origin.getCountry())) {
                paises.add(origin.getCountry());
            }
        }
        if (paises.isEmpty()) {
            paises.add("Brasil"); // fallback
        }
        final String[] paisesArray = paises.toArray(new String[0]);

        countryOriginEdit.setOnClickListener(v -> {
            new AlertDialog.Builder(this, R.style.HarvestDialog)
                    .setTitle("Selecione o país")
                    .setSingleChoiceItems(paisesArray, -1, (dialog, which) -> {
                        String paisSelecionado = paisesArray[which];
                        countryOriginEdit.setText(paisSelecionado);
                        campoRegiaoEdit.setText(""); // Limpa região anterior
                        dialog.dismiss();
                        atualizarRegioesPorPais(paisSelecionado);
                    })
                    .show();
        });
    }

    private void atualizarRegioesPorPais(String paisSelecionado) {
        EditText campoRegiaoEdit = findViewById(R.id.campoRegiao);
        GeographicOriginDAO originDAO = new GeographicOriginDAO(this);
        List<String> regioes = originDAO.getRegionsByCountry(paisSelecionado);
        if (regioes == null || regioes.isEmpty()) {
            campoRegiaoEdit.setText("");
            Toast.makeText(this, "Nenhuma região encontrada para o país selecionado.", Toast.LENGTH_SHORT).show();
            return;
        }
        final String[] regioesArray = regioes.toArray(new String[0]);
        new AlertDialog.Builder(this, R.style.HarvestDialog)
                .setTitle("Selecione a região")
                .setSingleChoiceItems(regioesArray, -1, (dialog, which) -> {
                    campoRegiaoEdit.setText(regioesArray[which]);
                    dialog.dismiss();
                })
                .show();
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
    }
    private void abrirCustomDialogList(String titulo, String[] itens, boolean multiSelecao, boolean[] selecionadosBoolean, List<String> selecionadosLista, Runnable onConfirmar) {
        View dialogView = getLayoutInflater().inflate(R.layout.estoque_dialog_cadastro_vinhos, null);

        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        dialogTitle.setText(titulo);

        ListView listView = dialogView.findViewById(R.id.dialogList);
        listView.setChoiceMode(multiSelecao ? ListView.CHOICE_MODE_MULTIPLE : ListView.CHOICE_MODE_SINGLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, multiSelecao ? android.R.layout.simple_list_item_multiple_choice :
                android.R.layout.simple_list_item_single_choice, itens);

        listView.setAdapter(adapter);

        for (int i = 0; i < itens.length; i++) {
            if (selecionadosBoolean[i]) {
                listView.setItemChecked(i, true);
            }
        }

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

        CustomButtonHelper btnConfirmar = dialogView.findViewById(R.id.dialogConfirm);
        CustomButtonHelper btnCancelar = dialogView.findViewById(R.id.dialogCancel);

        btnConfirmar.setOnClickListener(v -> {
            onConfirmar.run();
            dialog.dismiss();
        });

        btnCancelar.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
    private void abrirSelecaoMultipla() {
        abrirCustomDialogList("Selecione as uvas do blend", uvasDisponiveis, true, uvasSelecionadas, uvasEscolhidas, () -> {
            if (!uvasEscolhidas.isEmpty()) {
                String todasUvas = TextUtils.join(", ", uvasEscolhidas);
                wineGrapes.setText(todasUvas);
            } else {
                Toast.makeText(context, "Selecione pelo menos uma uva", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void abrirSelecaoUnica() {
            List<String> uvaSelecionada = new ArrayList<>();
            if (!uvasEscolhidas.isEmpty()) {
                uvaSelecionada.add(uvasEscolhidas.get(0));
            }

            boolean[] checkedItems = new boolean[uvasDisponiveis.length];
            for (int i = 0; i < uvasDisponiveis.length; i++) {
                checkedItems[i] = uvaSelecionada.contains(uvasDisponiveis[i]);
            }

            abrirCustomDialogList("Selecione a uva", uvasDisponiveis, false, checkedItems, uvaSelecionada, () -> {
                if (!uvaSelecionada.isEmpty()) {
                    uvasEscolhidas.clear();
                    uvasEscolhidas.add(uvaSelecionada.get(0));
                    wineGrapes.setText(uvaSelecionada.get(0));
                } else {
                    Toast.makeText(context, "Nenhuma uva selecionada", Toast.LENGTH_SHORT).show();
                }
            });
        }
}
