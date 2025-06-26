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
import com.example.vinhedobravioapp.database.model.CustomerModel;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private final Context context;
    private List<CustomerModel> customers;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(CustomerModel customer);
        void onDeleteClick(CustomerModel customer);
    }

    public CustomerAdapter(Context context, List<CustomerModel> customers, OnItemClickListener listener) {
        this.context = context;
        this.customers = customers;
        this.listener = listener;
    }

    public void setCustomers(List<CustomerModel> customers) {
        this.customers = customers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.clientes_item, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        CustomerModel customer = customers.get(position);
        holder.bind(customer, listener);
    }

    @Override
    public int getItemCount() {
        return customers != null ? customers.size() : 0;
    }

    static class CustomerViewHolder extends RecyclerView.ViewHolder {

        TextView name, cpfCnpj, address, region, phone, email;
        ImageButton btnEdit, btnDelete;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.customerName);
            cpfCnpj = itemView.findViewById(R.id.customerCpfCnpj);
            address = itemView.findViewById(R.id.customerAdress);
            region = itemView.findViewById(R.id.customerRegion);
            phone = itemView.findViewById(R.id.customerTelephone);
            email = itemView.findViewById(R.id.customerEmail);
            btnEdit = itemView.findViewById(R.id.btnEditar);
            btnDelete = itemView.findViewById(R.id.btnDeletar);
        }

        public void bind(CustomerModel customer, OnItemClickListener listener) {
            name.setText(customer.getNameCompanyName());
            cpfCnpj.setText(customer.getCpfCnpj());
            address.setText(customer.getAddress());
            region.setText(customer.getRegion());
            phone.setText(customer.getPhone());
            email.setText(customer.getEmail());

            btnEdit.setOnClickListener(v -> listener.onEditClick(customer));
            btnDelete.setOnClickListener(v -> listener.onDeleteClick(customer));
        }
    }
}
