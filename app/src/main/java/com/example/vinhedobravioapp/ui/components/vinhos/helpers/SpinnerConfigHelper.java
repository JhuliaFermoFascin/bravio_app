package com.example.vinhedobravioapp.ui.components.vinhos.helpers;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.vinhedobravioapp.database.dao.CommercialCategoryDAO;
import com.example.vinhedobravioapp.database.dao.CompositionTypeDAO;
import com.example.vinhedobravioapp.database.dao.WineTypeDAO;
import com.example.vinhedobravioapp.database.model.CommercialCategoryModel;
import com.example.vinhedobravioapp.database.model.CompositionTypeModel;
import com.example.vinhedobravioapp.database.model.WineTypeModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class SpinnerConfigHelper {

    public static void configurarSpinner(Spinner spinner, Context context, List<String> opcoes) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, opcoes
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

        public static List<WineTypeModel> configurarTipoVinho(Spinner spinner, Context context) {
            WineTypeDAO dao = new WineTypeDAO(context);
            List<WineTypeModel> tipos = dao.getAll();
            List<String> nomes = getName(tipos, WineTypeModel::getTypeName);
            configurarSpinner(spinner, context, nomes);
            return tipos;
        }

        public static List<CompositionTypeModel> configurarComposicao(Spinner spinner, Context context) {
            CompositionTypeDAO dao = new CompositionTypeDAO(context);
            List<CompositionTypeModel> compositions = dao.getAll();
            List<String> opcoes = compositions.isEmpty()
                    ? Arrays.asList("Varietal", "Blend")
                    : getName(compositions, CompositionTypeModel::getCompositionName);
            configurarSpinner(spinner, context, opcoes);
            return compositions;
        }

    public static List<CommercialCategoryModel> configurarCategoriaComercial(Spinner spinner, Context context) {
        CommercialCategoryDAO dao = new CommercialCategoryDAO(context);
        List<CommercialCategoryModel> categorias = dao.getAll();
        List<String> nomes = getName(categorias, CommercialCategoryModel::getName);
        configurarSpinner(spinner, context, nomes);
        return categorias;
    }

    private static <T> List<String> getName(List<T> lista, Function<T, String> extrator) {
        List<String> nomes = new ArrayList<>();
        for (T item : lista) {
            nomes.add(extrator.apply(item));
        }
        return nomes;
    }
}
