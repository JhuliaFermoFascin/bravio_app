package com.example.vinhedobravioapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.database.dao.InventoryMovementDAO;
import com.example.vinhedobravioapp.database.dao.WineImageDAO;
import com.example.vinhedobravioapp.database.model.OrderItemModel;
import com.example.vinhedobravioapp.database.model.WineImageModel;
import com.example.vinhedobravioapp.database.model.WineModel;
import com.example.vinhedobravioapp.ui.components.vinhos.helpers.WineDataHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ViewHolder> {

    private List<WineModel> lista;
    private final List<OrderItemModel> itensSelecionados = new ArrayList<>();
    private final Context context;

    public interface OnQuantidadeAlteradaListener {
        void onQuantidadeAlterada();
        void onItemClick(WineModel vinho);
    }

    private final OnQuantidadeAlteradaListener listener;

    public ProdutoAdapter(Context context, List<WineModel> lista, OnQuantidadeAlteradaListener listener) {
        this.context = context;
        this.lista = lista;
        this.listener = listener;
    }

    public List<OrderItemModel> getItensSelecionados() {
        return itensSelecionados;
    }

    public double getTotalPedido() {
        double total = 0.0;
        for (OrderItemModel item : itensSelecionados) {
            total += item.getValue() * item.getQuantity();
        }
        return total;
    }

    @NonNull
    @Override
    public ProdutoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pedidos_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WineModel wine = lista.get(position);
        InventoryMovementDAO im = new InventoryMovementDAO(context);
        int quantity = im.getAvailableQuantityByWineId(wine.getWineId());
        WineImageDAO wineImageDAO = new WineImageDAO(context);
        WineImageModel imgModel = wineImageDAO.getByWineId(wine.getWineId());
        Bitmap bitmap = null;
        if (imgModel != null && imgModel.getImageBase64() != null) {
            byte[] bytes = Base64.decode(imgModel.getImageBase64(), Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        holder.imagemVinho.setImageBitmap(bitmap != null
                ? bitmap
                : BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_photo)
        );
        holder.nomeVinho.setText(wine.getName());
        WineDataHelper.WineCompleteData data = WineDataHelper.getCompleteData(context, wine);
        holder.tipoVinho.setText(
                wine.getVintage() + " • " +
                        data.compositionType.getCompositionName() +
                        " • Qtd disp. " +quantity );

        String valorFmt = NumberFormat
                .getCurrencyInstance(new Locale("pt", "BR"))
                .format(wine.getUnitPrice());
        holder.precoVinho.setText(valorFmt);

        int estoqueDisponivel = quantity;

        if (estoqueDisponivel <= 0) {
            holder.labelEsgotado.setVisibility(View.VISIBLE);
            holder.btnMais.setEnabled(false);
            holder.btnMenos.setEnabled(false);
            holder.quantidadeSelecionada.setText("0");
            holder.itemView.setAlpha(0.5f);
            return;
        } else {
            holder.labelEsgotado.setVisibility(View.GONE);
            holder.itemView.setAlpha(1f);
        }

        OrderItemModel existente = encontrarItemPorId(wine.getWineId());
        holder.quantidadeSelecionada.setText(String.valueOf(
                existente != null ? existente.getQuantity() : 0
        ));

        holder.btnMais.setOnClickListener(v -> {
            OrderItemModel item = encontrarItemPorId(wine.getWineId());
            int atual = item != null ? item.getQuantity() : 0;
            if (atual < estoqueDisponivel) {
                int novaQtd = atual + 1;
                atualizarItem(wine, novaQtd);
                holder.quantidadeSelecionada.setText(String.valueOf(novaQtd));
                holder.btnMais.setEnabled(novaQtd < estoqueDisponivel);
                holder.btnMenos.setEnabled(true);
                listener.onQuantidadeAlterada();
            } else {
                Toast.makeText(context,
                        "Máximo disponível em estoque: " + estoqueDisponivel,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        holder.btnMenos.setOnClickListener(v -> {
            OrderItemModel item = encontrarItemPorId(wine.getWineId());
            int atual = item != null ? item.getQuantity() : 0;
            if (atual > 0) {
                int novaQtd = atual - 1;
                atualizarItem(wine, novaQtd);
                holder.quantidadeSelecionada.setText(String.valueOf(novaQtd));
                holder.btnMais.setEnabled(true);
                holder.btnMenos.setEnabled(novaQtd > 0);
                listener.onQuantidadeAlterada();
            }
        });

        holder.itemView.setOnClickListener(v -> listener.onItemClick(wine));
    }




    private OrderItemModel encontrarItemPorId(long wineId) {
        for (OrderItemModel item : itensSelecionados) {
            if (item.getWineId() == wineId) return item;
        }
        return null;
    }

    private void atualizarItem(WineModel wine, int quantidade) {
        OrderItemModel existente = encontrarItemPorId(wine.getWineId());

        if (quantidade == 0) {
            if (existente != null) itensSelecionados.remove(existente);
            return;
        }

        if (existente != null) {
            existente.setQuantity(quantidade);
        } else {
            OrderItemModel novo = new OrderItemModel();
            novo.setWineId(wine.getWineId());
            novo.setQuantity(quantidade);
            novo.setValue(wine.getUnitPrice());
            itensSelecionados.add(novo);
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imagemVinho;
        TextView nomeVinho, tipoVinho, precoVinho, quantidadeSelecionada, labelEsgotado, btnMais, btnMenos;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemVinho = itemView.findViewById(R.id.imagemVinho);
            nomeVinho = itemView.findViewById(R.id.nomeVinho);
            tipoVinho = itemView.findViewById(R.id.tipoVinho);
            precoVinho = itemView.findViewById(R.id.precoVinho);
            quantidadeSelecionada = itemView.findViewById(R.id.quantidadeSelecionada);
            btnMais = itemView.findViewById(R.id.btnMais);
            btnMenos = itemView.findViewById(R.id.btnMenos);
            labelEsgotado = itemView.findViewById(R.id.labelEsgotado);
        }
    }
}
