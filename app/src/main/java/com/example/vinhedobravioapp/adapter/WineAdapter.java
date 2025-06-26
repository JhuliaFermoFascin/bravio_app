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

    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.util.Base64;


    public class WineAdapter extends RecyclerView.Adapter<WineAdapter.WineViewHolder> {

        private List<WineModel> wineList;
        private final Context context;

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

        public WineAdapter(Context context,
                           List<WineModel> wineList,
                           OnItemClickListener itemClickListener,
                           OnEditClickListener editClickListener,
                           OnDeleteClickListener deleteClickListener) {
            this.context = context;
            this.wineList = wineList;
            this.itemClickListener = itemClickListener;
            this.editClickListener = editClickListener;
            this.deleteClickListener = deleteClickListener;
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
            WineModel wine = wineList.get(position);

            holder.nomeVinho.setText(wine.getName());

            holder.tipoVinho.setText("Tipo: " + wine.getWineTypeName());

            holder.safraVinho.setText("Safra: " + wine.getVintage());
            holder.valorVinho.setText("Valor: R$ " + wine.getUnit_price());

            if (wine.getImageBase64() != null && !wine.getImageBase64().isEmpty()) {
                byte[] decodedBytes = Base64.decode(wine.getImageBase64(), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                holder.imagemVinho.setImageBitmap(bitmap);
            } else {
                holder.imagemVinho.setImageResource(R.drawable.icon_photo);
            }

            int quantidade = wine.getQuantity();
            if (quantidade <= 0) {
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

            holder.itemView.setOnClickListener(v -> {
                if (quantidade > 0) itemClickListener.onItemClick(wine);
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

        public void setWineList(List<WineModel> list) {
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
