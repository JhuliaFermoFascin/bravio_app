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
import com.example.vinhedobravioapp.database.model.WineModel;

import com.example.vinhedobravioapp.ui.components.helper.CustomButtonHelper;
import com.example.vinhedobravioapp.ui.components.vinhos.helpers.WineDataHelper;

public class DetalhesVinhoActivity extends DialogFragment {

    private static final String ARG_WINE = "arg_wine";

    public static DetalhesVinhoActivity newInstance(WineModel wine) {
        DetalhesVinhoActivity fragment = new DetalhesVinhoActivity();
        Bundle args = new Bundle();
        args.putSerializable(ARG_WINE, wine);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        WineModel wine = (WineModel) getArguments().getSerializable(ARG_WINE);
        WineDataHelper.WineCompleteData data = WineDataHelper.getCompleteData(getContext(), wine);

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

        nome.setText(getString(R.string.details_wine_name) + " " + data.wine.getName());
        winery.setText(getString(R.string.details_winery_name) + " " + data.winery.getName());
        catCommercial.setText(getString(R.string.details_commercial_category) + " " + data.commercialCategory.getName());
        descricao.setText(getString(R.string.details_description) + " " + data.wine.getDescription());
        safra.setText(getString(R.string.details_harvest) + " " + data.wine.getVintage());
        origin.setText(getString(R.string.details_geographic_origin) + " " + data.geographicOrigin.getCountry() + ", " + data.geographicOrigin.getRegion());
        tipo.setText(getString(R.string.details_wine_type) + " " + data.wineType.getTypeName());
        composition.setText(getString(R.string.details_composition) + " " + data.compositionType.getCompositionName());
        grapes.setText(getString(R.string.details_grapes) + " " + data.grapesConcatenated);
        teor.setText(getString(R.string.details_alcohol_content) + " " + data.wine.getAlcoholContent() + "%");
        volume.setText(getString(R.string.details_volume) + " " + data.wine.getVolume());
        acidity.setText(getString(R.string.detail_acidity) + " " + data.wine.getAcidity() + "pH");
        temperature.setText(getString(R.string.detail_ideal_temperature) + " " + data.wine.getIdealTemperatureCelsius() + "°C");
        time.setText(getString(R.string.detail_estimated_storage_time) + " " + data.wine.getAgingPotential());
        notas.setText(getString(R.string.details_tasting_notes) + " " + data.tastingNotesConcatenated);
        harmonizacao.setText(getString(R.string.details_food_parings) + " " + data.foodPairingsConcatenated);
        reviews.setText(getString(R.string.details_reviews) + " " + (data.wineReview != null ? data.wineReview.getReviewText() : ""));
        valor.setText(getString(R.string.details_unit_price) + " R$" + data.wine.getUnit_price());
        quantidade.setText(getString(R.string.details_quantity) + " " + data.wine.getQuantity());

        if (data.wineImageBitmap != null) {
            imagem.setImageBitmap(data.wineImageBitmap);
        } else {
            imagem.setImageResource(R.drawable.icon_photo);
        }

        return new AlertDialog.Builder(requireContext())
                .setView(view)
                .create();
    }
}