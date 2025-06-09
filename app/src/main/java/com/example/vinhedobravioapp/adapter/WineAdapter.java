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
    import com.example.vinhedobravioapp.database.model.WineModel;

    import java.util.List;

    public class WineAdapter extends RecyclerView.Adapter<WineAdapter.WineViewHolder> {

        private List<WineModel> wineList;
        private final Context context;

        // Interfaces para ações
        public interface OnItemClickListener {
            void onItemClick(WineModel wine);
        }

        public interface OnEditClickListener {
            void onEditClick(WineModel wine);
        }

        public interface OnDeleteClickListener {
            void onDeleteClick(WineModel wine);
        }

        private final OnItemClickListener itemClickListener;
        private final OnEditClickListener editClickListener;
        private final OnDeleteClickListener deleteClickListener;

        public WineAdapter(Context context, List<WineModel> wineList,  OnItemClickListener itemClickListener,
                           OnEditClickListener editClickListener, OnDeleteClickListener deleteClickListener){
            this.context = context;
            this.wineList = wineList;
            this.itemClickListener = itemClickListener;
            this.editClickListener = editClickListener;
            this.deleteClickListener = deleteClickListener;
        }

        @NonNull
        @Override
        public WineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_vinho, parent, false);
            return new WineViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull WineViewHolder holder, int position) {
            WineModel wine = wineList.get(position);

            holder.nomeVinho.setText(wine.getName());
            holder.tipoVinho.setText("Tipo: " + wine.getWineTypeId()); // ideal seria buscar o nome do tipo
            holder.safraVinho.setText("Safra: " + wine.getVintage());
            holder.quantidadeVinho.setText("Volume: " + wine.getVolume() + "ml");
            holder.valorVinho.setText("Teor: " + wine.getAlcoholContent() + "%");

            // Ações
            holder.itemView.setOnClickListener(v -> itemClickListener.onItemClick(wine));
            holder.btnEditar.setOnClickListener(v -> editClickListener.onEditClick(wine));
            holder.btnDeletar.setOnClickListener(v -> deleteClickListener.onDeleteClick(wine));
        }

        @Override
        public int getItemCount() {
            return wineList.size();
        }

        public void setWineList(List<WineModel> list) {
            this.wineList = list;
            notifyDataSetChanged();
        }

        static class WineViewHolder extends RecyclerView.ViewHolder {

            TextView nomeVinho, tipoVinho, safraVinho, quantidadeVinho, valorVinho;
            ImageView imagemVinho;
            ImageButton btnEditar, btnDeletar;

            public WineViewHolder(@NonNull View itemView) {
                super(itemView);
                nomeVinho = itemView.findViewById(R.id.nomeVinho);
                tipoVinho = itemView.findViewById(R.id.tipoVinho);
                safraVinho = itemView.findViewById(R.id.safraVinho);
                quantidadeVinho = itemView.findViewById(R.id.quantidadeVinho);
                valorVinho = itemView.findViewById(R.id.valorVinho);
                imagemVinho = itemView.findViewById(R.id.imagemVinho);
                btnEditar = itemView.findViewById(R.id.btnEditar);
                btnDeletar = itemView.findViewById(R.id.btnDeletar);
            }
        }

    }
