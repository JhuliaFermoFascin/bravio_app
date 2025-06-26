package com.example.vinhedobravioapp.ui.components.pedidos;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.database.dao.CustomerDAO;
import com.example.vinhedobravioapp.database.dao.OrderDAO;
import com.example.vinhedobravioapp.database.dao.OrderItemDAO;
import com.example.vinhedobravioapp.database.dao.UserDAO;
import com.example.vinhedobravioapp.database.dao.WineDAO;
import com.example.vinhedobravioapp.database.model.CustomerModel;
import com.example.vinhedobravioapp.database.model.OrderItemModel;
import com.example.vinhedobravioapp.database.model.OrderModel;
import com.example.vinhedobravioapp.database.model.UserModel;

import java.util.List;

public class DetalhesPedidoDialogFragment extends DialogFragment {
    private static final String ARG_ORDER_ID = "orderId";

    private Spinner spinnerStatus;
    private OrderModel orderModel;
    private OrderDAO orderDAO;

    public interface OnStatusUpdatedListener {
        void onStatusUpdated();
    }

    private OnStatusUpdatedListener listener;

    public void setOnStatusUpdatedListener(OnStatusUpdatedListener listener) {
        this.listener = listener;
    }

    public static DetalhesPedidoDialogFragment newInstance(long orderId) {
        DetalhesPedidoDialogFragment fragment = new DetalhesPedidoDialogFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ORDER_ID, orderId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pedidos_detalhes, container, false);

        orderDAO = new OrderDAO(requireContext());

        long orderId = getArguments().getLong(ARG_ORDER_ID, -1);
        if (orderId == -1) {
            dismiss();
            return view;
        }

        orderModel = orderDAO.getById(orderId);
        if (orderModel == null) {
            dismiss();
            return view;
        }

        // Preenche outros campos (data, total, itens, cliente, usuário) ...
        TextView txtData = view.findViewById(R.id.detail_data);
        TextView txtTotal = view.findViewById(R.id.detail_total);
        txtData.setText(getString(R.string.order_data) + " " + orderModel.getDate());
        txtTotal.setText(getString(R.string.total_details) + " R$" + String.valueOf(orderModel.getTotal()));

        TextView txtItens = view.findViewById(R.id.detail_item);
        OrderItemDAO orderItemDAO = new OrderItemDAO(requireContext());
        List<OrderItemModel> itensPedido = orderItemDAO.getByOrderId(orderModel.getOrderId());

        StringBuilder sb = new StringBuilder();
        WineDAO wineDAO = new WineDAO(requireContext());

        for (OrderItemModel item : itensPedido) {
            String nomeVinho = "ID " + item.getWineId();
            if (wineDAO.getById(item.getWineId()) != null) {
                nomeVinho = wineDAO.getById(item.getWineId()).getName();
            }

            double totalParcial = item.getQuantity() * item.getValue();
            sb.append(nomeVinho)
                    .append(" | ")
                    .append(item.getQuantity())
                    .append(" | R$ ")
                    .append(String.format("%.2f", totalParcial))
                    .append("\n");
        }

        txtItens.setText(sb.toString());

        TextView detail_cliente = view.findViewById(R.id.detail_cliente);
        TextView detail_user = view.findViewById(R.id.detail_user);

        CustomerDAO customerDAO = new CustomerDAO(requireContext());
        CustomerModel cliente = customerDAO.getById(orderModel.getCustomerId());
        if (cliente != null) {
            detail_cliente.setText(getString(R.string.customer_name) + " " + cliente.getNameCompanyName());
        } else {
            detail_cliente.setText(getString(R.string.customer_name) + " -");
        }

        UserDAO userDAO = new UserDAO(getContext());
        UserModel user = userDAO.getById(orderModel.getUserId());
        if (user != null) {
            detail_user.setText(getString(R.string.requester_name) + " " + user.getName());
        } else {
            detail_user.setText(getString(R.string.requester_name) + " -");
        }

        spinnerStatus = view.findViewById(R.id.order_status);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                getResources().getTextArray(R.array.status_orders)
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                String status = getItem(position).toString();
                applyStyleToTextView(view, status);
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                String status = getItem(position).toString();
                applyStyleToTextView(view, status);
                return view;
            }

            private void applyStyleToTextView(TextView view, String status) {
                if (status == null) return;

                // Cor do texto
                view.setTextColor(getColorByStatus(status));
                // Background
                view.setBackgroundResource(getBackgroundByStatus(status));
                // Estilo da fonte (exemplo: negrito para "ABERTO")
                if ("ABERTO".equalsIgnoreCase(status)) {
                    view.setTypeface(null, Typeface.BOLD);
                } else {
                    view.setTypeface(null, Typeface.NORMAL);
                }
                // Padding para garantir espaçamento do texto
                int padding = (int) (8 * view.getResources().getDisplayMetrics().density);
                view.setPadding(padding, padding / 2, padding, padding / 2);
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        int statusPos = adapter.getPosition(orderModel.getStatus());
        if (statusPos >= 0) {
            spinnerStatus.setSelection(statusPos);
            setSpinnerBackgroundByStatus(orderModel.getStatus());
        }

        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean firstCall = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstCall) {
                    firstCall = false;
                    return;
                }

                String novoStatus = parent.getItemAtPosition(position).toString();
                if (!orderModel.getStatus().equals(novoStatus)) {
                    orderModel.setStatus(novoStatus);
                    orderDAO.update(orderModel);
                    Toast.makeText(requireContext(), "Status atualizado para " + novoStatus, Toast.LENGTH_SHORT).show();

                    if (listener != null) {
                        listener.onStatusUpdated();
                    }
                }
                setSpinnerBackgroundByStatus(novoStatus);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerStatus.setBackgroundResource(R.drawable.bg_status_padrao);
            }
        });

        // Botão fechar
        view.findViewById(R.id.close_btn).setOnClickListener(v -> dismiss());

        return view;
    }

    private int getColorByStatus(String status) {
        return ContextCompat.getColor(requireContext(), R.color.background_text_primary);
    }

    private int getBackgroundByStatus(String status) {
        switch (status.toUpperCase()) {
            case "ABERTO":
                return R.drawable.bg_status_aberto;
            case "CONCLUIDO":
                return R.drawable.bg_status_concluido;
            case "CANCELADO":
                return R.drawable.bg_status_cancelado;
            default:
                return R.drawable.bg_status_padrao;
        }
    }

    private void setSpinnerBackgroundByStatus(String status) {
        switch (status.toUpperCase()) {
            case "ABERTO":
                spinnerStatus.setBackgroundResource(R.drawable.bg_status_aberto);
                break;
            case "CONCLUIDO":
                spinnerStatus.setBackgroundResource(R.drawable.bg_status_concluido);
                break;
            case "CANCELADO":
                spinnerStatus.setBackgroundResource(R.drawable.bg_status_cancelado);
                break;
            default:
                spinnerStatus.setBackgroundResource(R.drawable.bg_status_padrao);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
    }
}
