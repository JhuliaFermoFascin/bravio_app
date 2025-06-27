package com.example.vinhedobravioapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.models.FullWineModel;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;


public class WineAdapter extends RecyclerView.Adapter<WineAdapter.WineViewHolder> {

    private List<FullWineModel> wineList;
    private final Context context;

    public interface OnItemClickListener {
        void onItemClick(FullWineModel wine);
    }

    public interface OnEditClickListener {
        void onEditClick(FullWineModel wine);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(FullWineModel wine);
    }

    private final OnItemClickListener itemClickListener;
    private final OnEditClickListener editClickListener;
    private final OnDeleteClickListener deleteClickListener;
    private final int tipoUsuario;

    public WineAdapter(Context context,
                       List<FullWineModel> wineList,
                       OnItemClickListener itemClickListener,
                       OnEditClickListener editClickListener,
                       OnDeleteClickListener deleteClickListener,
                       int tipoUsuario
    ) {
        this.context = context;
        this.wineList = wineList;
        this.itemClickListener = itemClickListener;
        this.editClickListener = editClickListener;
        this.deleteClickListener = deleteClickListener;
        this.tipoUsuario = tipoUsuario;
    }

    @NonNull
    @Override
    public WineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.estoque_item_vinho, parent, false);
        return new WineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WineViewHolder holder, int position) {
        FullWineModel wine = wineList.get(position);

        holder.nomeVinho.setText(wine.getName());
        holder.tipoVinho.setText("Tipo: " + (wine.getWineType() != null ? wine.getWineType().getTypeName() : ""));
        holder.safraVinho.setText("Safra: " + wine.getVintage());
        holder.valorVinho.setText("Valor: R$ " + (wine.getUnitPrice() != null ? wine.getUnitPrice() : "-"));

        if (wine.getImageBase64() != null && !wine.getImageBase64().isEmpty()) {
            byte[] decodedBytes = Base64.decode(wine.getImageBase64(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            holder.imagemVinho.setImageBitmap(bitmap);
        } else {
            holder.imagemVinho.setImageResource(R.drawable.icon_photo);
        }

        // If FullWineModel exposes quantity, use it. Otherwise, fallback to 0.
        int quantidade = wine.getQuantity();
        try {
            java.lang.reflect.Method m = wine.getClass().getMethod("getQuantity");
            Object q = m.invoke(wine);
            if (q instanceof Integer) quantidade = (Integer) q;
        } catch (Exception e) {
            // fallback or log
        }
        if (quantidade == 0) {
            holder.quantidadeVinho.setText("Esgotado");
            holder.badgeEsgotado.setVisibility(View.VISIBLE);
            holder.itemView.setAlpha(0.5f);
            holder.itemView.setClickable(false);
        } else {
            holder.quantidadeVinho.setText("Qtd: " + quantidade);
            holder.badgeEsgotado.setVisibility(View.GONE);
            holder.itemView.setAlpha(1f);
            holder.btnEditar.setEnabled(true);
            holder.btnDeletar.setEnabled(true);
        }

        if (tipoUsuario != 1) {
            holder.btnEditar.setVisibility(View.GONE);
            holder.btnDeletar.setVisibility(View.GONE);
        } else {
            holder.btnEditar.setVisibility(View.VISIBLE);
            holder.btnDeletar.setVisibility(View.VISIBLE);
        }

        final int quantidadeFinal = quantidade;

        holder.itemView.setOnClickListener(v -> {
            if (quantidadeFinal > 0) itemClickListener.onItemClick(wine);
        });
        holder.btnEditar.setOnClickListener(v -> {
            editClickListener.onEditClick(wine);
        });
        holder.btnDeletar.setOnClickListener(v -> deleteClickListener.onDeleteClick(wine));
    }

    @Override
    public int getItemCount() {
        return wineList.size();
    }

    public void setWineList(List<FullWineModel> list) {
        this.wineList = list;
        notifyDataSetChanged();
    }

    static class WineViewHolder extends RecyclerView.ViewHolder {
        TextView nomeVinho, tipoVinho, safraVinho, quantidadeVinho, valorVinho, badgeEsgotado;
        ImageView imagemVinho;
        ImageButton btnEditar, btnDeletar;

        public WineViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeVinho = itemView.findViewById(R.id.nomeVinho);
            tipoVinho = itemView.findViewById(R.id.tipoVinho);
            safraVinho = itemView.findViewById(R.id.safraVinho);
            quantidadeVinho = itemView.findViewById(R.id.quantidadeVinho);
            valorVinho = itemView.findViewById(R.id.valorVinho);
            badgeEsgotado = itemView.findViewById(R.id.badgeEsgotado);
            imagemVinho = itemView.findViewById(R.id.imagemVinho);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnDeletar = itemView.findViewById(R.id.btnDeletar);
        }
    }
}
