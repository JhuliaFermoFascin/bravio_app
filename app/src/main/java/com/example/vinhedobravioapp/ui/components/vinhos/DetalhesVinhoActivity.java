package com.example.vinhedobravioapp.ui.components.vinhos;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.ui.components.helper.CustomButtonHelper;
import com.example.vinhedobravioapp.database.model.WineModel;

public class DetalhesVinhoActivity  extends DialogFragment {

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

        View view = LayoutInflater.from(getContext()).inflate(R.layout.estoque_detalhes_vinho, null);

        TextView nome = view.findViewById(R.id.detail_nome);
        TextView safra = view.findViewById(R.id.detail_safra);
        TextView tipo = view.findViewById(R.id.detail_tipo);
        TextView descricao = view.findViewById(R.id.detail_descricao);
        TextView quantidade = view.findViewById(R.id.detail_quantidade);
        TextView notas = view.findViewById(R.id.detail_notas);
        TextView harmonizacao = view.findViewById(R.id.detail_harmonizacao);
        TextView teor = view.findViewById(R.id.detail_teor);
        TextView volume = view.findViewById(R.id.detail_volume);
        TextView valor = view.findViewById(R.id.detail_valor);
        ImageView imagem = view.findViewById(R.id.detail_imagem);
        CustomButtonHelper closeBtn = view.findViewById(R.id.close_btn);

        closeBtn.setOnClickListener(v -> dismiss());

        nome.setText("Nome: " + wine.getName());
        safra.setText("Safra: " + wine.getVintage());
        tipo.setText("Tipo: " + wine.getWineTypeId());
        descricao.setText("Descrição: " + wine.getDescription());
        //quantidade.setText("Quantidade: " + wine.getQuantity());
        notas.setText("Notas de degustação: " + wine.getTastingNoteId());
        harmonizacao.setText("Harmonizações: " + wine.getFoodPairings());
        teor.setText("Teor alcoólico: " + wine.getAlcoholContent() + "%");
        volume.setText("Volume (ml): " + wine.getVolume());

        return new AlertDialog.Builder(requireContext())
                .setView(view)
                .create();
    }
}
