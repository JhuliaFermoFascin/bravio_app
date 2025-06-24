package com.example.vinhedobravioapp.ui.components.vinhos.helpers;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.vinhedobravioapp.database.dao.GeographicOriginDAO;
import com.example.vinhedobravioapp.database.model.GeographicOriginModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GeographicOriginAutoCompleteHelper {
    public static void setup(Context context, AutoCompleteTextView countryOrigin, AutoCompleteTextView campoRegiao) {
        GeographicOriginDAO originDAO = new GeographicOriginDAO(context);
        List<GeographicOriginModel> origens = originDAO.getAll();

        Set<String> paisesSet = new HashSet<>();
        for (GeographicOriginModel o : origens) {
            paisesSet.add(o.getCountry());
        }
        List<String> paises = new ArrayList<>(paisesSet);

        ArrayAdapter<String> adapterPais = new ArrayAdapter<>(
                context,
                android.R.layout.simple_dropdown_item_1line,
                paises
        );

        countryOrigin.setAdapter(adapterPais);
        countryOrigin.setThreshold(1);

        countryOrigin.setOnItemClickListener((parent, view, position, id) -> {
            String paisSelecionado = (String) parent.getItemAtPosition(position);
            List<String> regioes = originDAO.getRegionsByCountry(paisSelecionado);

            ArrayAdapter<String> adapterRegiao = new ArrayAdapter<>(
                    context,
                    android.R.layout.simple_dropdown_item_1line,
                    regioes
            );

            campoRegiao.setAdapter(adapterRegiao);
            campoRegiao.setThreshold(1);
            campoRegiao.setText("");
        });
    }
}
