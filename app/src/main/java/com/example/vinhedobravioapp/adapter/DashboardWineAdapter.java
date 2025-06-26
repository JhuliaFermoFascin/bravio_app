package com.example.vinhedobravioapp.adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.domain.model.Vinho;

import java.util.List;

public class DashboardWineAdapter extends RecyclerView.Adapter<DashboardWineAdapter.ViewHolder> {

    private List<Vinho> vinhoList;

    public DashboardWineAdapter(List<Vinho> vinhoList) {
        this.vinhoList = vinhoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.component_view_card_vinho, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vinho vinho = vinhoList.get(position);
        holder.nome.setText(vinho.nome);
        holder.pedidos.setText(vinho.pedidos + " pedidos no mês de Junho");
        holder.comparacao.setText(vinho.comparacao + " em comparação ao mês de Maio");

        int cor = vinho.subiu
                ? ContextCompat.getColor(holder.itemView.getContext(), R.color.button_green_terciary)
                : ContextCompat.getColor(holder.itemView.getContext(), R.color.text_red_primary);

        holder.comparacao.setTextColor(cor);

        if ("Novo!".equalsIgnoreCase(vinho.comparacao)) {
            holder.iconArrow.setImageResource(R.drawable.icon_plus);
        } else {
            int iconRes = vinho.subiu
                    ? R.drawable.icon_up
                    : R.drawable.icon_down;
            holder.iconArrow.setImageResource(iconRes);
        }
    }

    @Override
    public int getItemCount() {
        return vinhoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome, pedidos, comparacao;
        ImageView iconArrow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nome_vinho);
            pedidos = itemView.findViewById(R.id.vendas_mes);
            comparacao = itemView.findViewById(R.id.comparacao);
            iconArrow = itemView.findViewById(R.id.icon_arrow);
        }
    }
}
