package com.example.vinhedobravioapp.ui.components.vinhos;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.models.FullWineModel;
import com.example.vinhedobravioapp.ui.components.helper.CustomButtonHelper;

public class DetalhesVinhoActivity extends DialogFragment {

    private static final String ARG_WINE = "arg_wine";

    public static DetalhesVinhoActivity newInstance(FullWineModel wine) {
        DetalhesVinhoActivity fragment = new DetalhesVinhoActivity();
        Bundle args = new Bundle();
        args.putLong(ARG_WINE, wine.getWineId());
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        long wineId = getArguments().getLong(ARG_WINE);
        FullWineModel wine = new FullWineModel(getContext(), wineId);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.estoque_detalhes_vinho, null);

        TextView nome = view.findViewById(R.id.detail_nome);
        TextView winery = view.findViewById(R.id.detail_winery);
        TextView catCommercial = view.findViewById(R.id.detail_commercial_category);
        TextView safra = view.findViewById(R.id.detail_safra);
        TextView origin = view.findViewById(R.id.detail_origin);
        TextView tipo = view.findViewById(R.id.detail_tipo);
        TextView descricao = view.findViewById(R.id.detail_descricao);
        TextView quantidade = view.findViewById(R.id.detail_quantidade);
        TextView notas = view.findViewById(R.id.detail_notas);
        TextView harmonizacao = view.findViewById(R.id.detail_harmonizacao);
        TextView teor = view.findViewById(R.id.detail_teor);
        TextView volume = view.findViewById(R.id.detail_volume);
        TextView valor = view.findViewById(R.id.detail_valor);
        TextView composition = view.findViewById(R.id.detail_composition);
        TextView grapes = view.findViewById(R.id.detail_grapes);
        TextView acidity = view.findViewById(R.id.detail_acidity);
        TextView temperature = view.findViewById(R.id.detail_temperature);
        TextView time = view.findViewById(R.id.detail_time);
        TextView reviews = view.findViewById(R.id.detail_review);

        ImageView imagem = view.findViewById(R.id.detail_imagem);
        CustomButtonHelper closeBtn = view.findViewById(R.id.close_btn);

        closeBtn.setOnClickListener(v -> dismiss());

        nome.setText(getString(R.string.details_wine_name) + " " + wine.getName());
        winery.setText(getString(R.string.details_winery_name) + " " + (wine.getWinery() != null ? wine.getWinery().getName() : ""));
        catCommercial.setText(getString(R.string.details_commercial_category) + " " + (wine.getCommercialCategory() != null ? wine.getCommercialCategory().getName() : ""));
        descricao.setText(getString(R.string.details_description) + " " + wine.getDescription());
        safra.setText(getString(R.string.details_harvest) + " " + wine.getVintage());
        origin.setText(getString(R.string.details_geographic_origin) + " " + (wine.getOrigin() != null ? wine.getOrigin().getCountry() + ", " + wine.getOrigin().getRegion() : ""));
        tipo.setText(getString(R.string.details_wine_type) + " " + (wine.getWineType() != null ? wine.getWineType().getTypeName() : ""));
        composition.setText(getString(R.string.details_composition) + " " + (wine.getWine() != null && wine.getWine().getCompositionTypeId() != 0 ? String.valueOf(wine.getWine().getCompositionTypeId()) : ""));
        grapes.setText(getString(R.string.details_grapes) + " " + (wine.getGrapeNames() != null ? android.text.TextUtils.join(", ", wine.getGrapeNames()) : ""));
        teor.setText(getString(R.string.details_alcohol_content) + " " + (wine.getAlcoholContent() != null ? wine.getAlcoholContent() + "%" : ""));
        volume.setText(getString(R.string.details_volume) + " " + (wine.getVolume() != null ? wine.getVolume() : ""));
        acidity.setText(getString(R.string.detail_acidity) + " " + (wine.getAcidity() != null ? wine.getAcidity() + "pH" : ""));
        temperature.setText(getString(R.string.detail_ideal_temperature) + " " + (wine.getIdealTemperatureCelsius() != null ? wine.getIdealTemperatureCelsius() + "°C" : ""));
        time.setText(getString(R.string.detail_estimated_storage_time) + " " + (wine.getAgingPotential() != null ? wine.getAgingPotential() : ""));
        notas.setText(getString(R.string.details_tasting_notes) + " " + (wine.getTastingNotes() != null ? android.text.TextUtils.join(", ", wine.getTastingNotes()) : ""));
        harmonizacao.setText(getString(R.string.details_food_parings) + " " + (wine.getFoodPairings() != null ? android.text.TextUtils.join(", ", wine.getFoodPairings()) : ""));
        reviews.setText(getString(R.string.details_reviews) + " " + (wine.getWineReview() != null ? wine.getWineReview().getReviewText() : ""));
        valor.setText(getString(R.string.details_unit_price) + " R$" + (wine.getUnitPrice() != null ? wine.getUnitPrice() : ""));
        // quantidade: se houver método getQuantity() em FullWineModel, use, senão deixe vazio
        String quantidadeStr = "";
        try {
            java.lang.reflect.Method m = wine.getClass().getMethod("getQuantity");
            Object q = m.invoke(wine);
            if (q != null) quantidadeStr = String.valueOf(q);
        } catch (Exception e) {}
        quantidade.setText(getString(R.string.details_quantity) + " " + quantidadeStr);

        if (wine.getImageBase64() != null && !wine.getImageBase64().isEmpty()) {
            byte[] decodedBytes = Base64.decode(wine.getImageBase64(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            imagem.setImageBitmap(bitmap);
        } else {
            imagem.setImageResource(R.drawable.icon_photo);
        }

        return new AlertDialog.Builder(requireContext())
                .setView(view)
                .create();
    }
}