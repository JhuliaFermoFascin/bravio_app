package com.example.vinhedobravioapp.ui.components.pedidos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.adapter.ProdutoAdapter;
import com.example.vinhedobravioapp.database.model.OrderItemModel;
import com.example.vinhedobravioapp.database.model.WineModel;
import com.example.vinhedobravioapp.models.FullWineModel;
import com.example.vinhedobravioapp.ui.components.helper.CustomButtonHelper;
import com.example.vinhedobravioapp.ui.components.vinhos.DetalhesVinhoActivity;

import java.util.List;

public class ProdutoDialogFragment extends DialogFragment {

    private RecyclerView recyclerView;
    private CustomButtonHelper btnSalvar, btnCancelar;
    private ProdutoAdapter adapter;
    private List<WineModel> listaVinhos;

    public interface OnProdutosSelecionadosListener {
        void onSalvar(List<OrderItemModel> itens, double total);
    }

    private OnProdutosSelecionadosListener listener;

    public ProdutoDialogFragment(List<WineModel> listaVinhos) {
        this.listaVinhos = listaVinhos;
    }

    public void setListener(OnProdutosSelecionadosListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pedidos_dialog_produtos, container, false);

        recyclerView = view.findViewById(R.id.recyclerProdutos);
        btnSalvar = view.findViewById(R.id.btnSalvarProdutos);
        btnCancelar = view.findViewById(R.id.btnCancelarProdutos);

        adapter = new ProdutoAdapter(getContext(), listaVinhos, new ProdutoAdapter.OnQuantidadeAlteradaListener() {
            @Override
            public void onQuantidadeAlterada() { }

            @Override
            public void onItemClick(WineModel vinho) {
                DetalhesVinhoActivity detalhesVinhoActivity = DetalhesVinhoActivity.newInstance(new FullWineModel(getContext(), vinho.getWineId()));
                detalhesVinhoActivity.show(getParentFragmentManager(), "detalhes_vinho");
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        btnSalvar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSalvar(adapter.getItensSelecionados(), adapter.getTotalPedido());
            }
            dismiss();
        });

        btnCancelar.setOnClickListener(v -> dismiss());

        return view;
    }
}
