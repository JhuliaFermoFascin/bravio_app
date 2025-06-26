package com.example.vinhedobravioapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.database.model.OrderModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    public interface OnDeleteClickListener {
        void onDeleteClick(OrderModel order);
    }

    public interface OnOrderClickListener {
        void onClick(OrderModel order);
    }

    private List<OrderModel> orders;
    private Context context;
    private OnDeleteClickListener deleteListener;
    private OnOrderClickListener orderClickListener;
    private Map<Long, String> customerNames;

    public OrderAdapter(Context context,
                        List<OrderModel> orders,
                        Map<Long, String> customerNames,
                        OnDeleteClickListener deleteListener,
                        OnOrderClickListener orderClickListener) {
        this.context = context;
        this.orders = orders;
        this.customerNames = customerNames;
        this.deleteListener = deleteListener;
        this.orderClickListener = orderClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context)
                .inflate(R.layout.pedido_item_list, parent, false);
        return new OrderViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int pos) {
        OrderModel order = orders.get(pos);

        String nomeCliente = customerNames.get(order.getCustomerId());
        if (nomeCliente != null) {
            holder.clienteName.setText(nomeCliente);
        } else {
            holder.clienteName.setText("— Cliente não encontrado —");
        }

        holder.orderData.setText(order.getDate());
        holder.statusOrder.setText(order.getStatus());

        switch (order.getStatus().toUpperCase()) {
            case "ABERTO":
                holder.statusOrder.setBackgroundResource(R.drawable.bg_status_aberto);
                break;
            case "CONCLUIDO":
                holder.statusOrder.setBackgroundResource(R.drawable.bg_status_concluido);
                break;
            case "CANCELADO":
                holder.statusOrder.setBackgroundResource(R.drawable.bg_status_cancelado);
                break;
            default:
                holder.statusOrder.setBackgroundResource(R.drawable.bg_status_padrao);
                break;
        }

        holder.totalOrder.setText(
                NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))
                        .format(order.getTotal())
        );

        holder.btnDeletar.setOnClickListener(v -> deleteListener.onDeleteClick(order));

        holder.itemView.setOnClickListener(v -> {
            if (orderClickListener != null) {
                orderClickListener.onClick(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView clienteName, orderData, totalOrder, statusOrder;
        ImageButton btnDeletar;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            clienteName  = itemView.findViewById(R.id.clienteName);
            orderData    = itemView.findViewById(R.id.orderData);
            statusOrder  = itemView.findViewById(R.id.statusOrder);
            totalOrder   = itemView.findViewById(R.id.totalOrder);
            btnDeletar   = itemView.findViewById(R.id.btnDeletar);
        }
    }

    public void updateList(List<OrderModel> newList) {
        this.orders = newList;
        notifyDataSetChanged();
    }
}
