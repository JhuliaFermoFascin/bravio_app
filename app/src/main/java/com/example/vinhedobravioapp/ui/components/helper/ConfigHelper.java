package com.example.vinhedobravioapp.ui.components.helper;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.database.dao.CommercialCategoryDAO;
import com.example.vinhedobravioapp.database.dao.CompositionTypeDAO;
import com.example.vinhedobravioapp.database.dao.FoodPairingDAO;
import com.example.vinhedobravioapp.database.dao.GrapeDAO;
import com.example.vinhedobravioapp.database.dao.TastingNoteDAO;
import com.example.vinhedobravioapp.database.dao.WineTypeDAO;
import com.example.vinhedobravioapp.database.model.CommercialCategoryModel;
import com.example.vinhedobravioapp.database.model.CompositionTypeModel;
import com.example.vinhedobravioapp.database.model.FoodPairingModel;
import com.example.vinhedobravioapp.database.model.GrapeModel;
import com.example.vinhedobravioapp.database.model.TastingNoteModel;
import com.example.vinhedobravioapp.database.model.WineTypeModel;
import com.example.vinhedobravioapp.ui.components.vinhos.EstoqueDialogConfigActivity;

public class ConfigHelper {
    public static void show(Activity activity) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View popupView = inflater.inflate(R.layout.component_view_config, null);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                true
        );

        popupWindow.showAtLocation(activity.findViewById(android.R.id.content), Gravity.NO_GRAVITY, 0, 0);

        View background = popupView.findViewById(R.id.background_click_area);
        View menu = popupView.findViewById(R.id.popup_menu);
        LinearLayout addCommercialCategory_btn = popupView.findViewById(R.id.addCommercialCategory_btn);
        LinearLayout addGrapeComposition_btn = popupView.findViewById(R.id.addGrapeComposition_btn);
        LinearLayout addFoodPairing_btn = popupView.findViewById(R.id.addFoodPairing_btn);
        LinearLayout addTastingNotes_btn = popupView.findViewById(R.id.addTastingNotes_btn);
        LinearLayout addWineType_btn = popupView.findViewById(R.id.addWineType_btn);
        LinearLayout addWineGrapes_btn = popupView.findViewById(R.id.addWineGrapes_btn);

        background.setOnClickListener(v -> popupWindow.dismiss());
        menu.setOnClickListener(v -> {
        });
        addCommercialCategory_btn.setOnClickListener(v -> openDialog(activity, activity.getString(R.string.commercial_category), "CATEGORIA"));
        addGrapeComposition_btn.setOnClickListener(v -> openDialog(activity, activity.getString(R.string.grapes_composition), "COMPOSICAO"));
        addFoodPairing_btn.setOnClickListener(v -> openDialog(activity, activity.getString(R.string.food_parings), "HARMONIZAÇÃO"));
        addTastingNotes_btn.setOnClickListener(v -> openDialog(activity, activity.getString(R.string.tasting_notes), "NOTA"));
        addWineType_btn.setOnClickListener(v -> openDialog(activity, activity.getString(R.string.wine_type), "TIPO"));
        addWineGrapes_btn.setOnClickListener(v -> openDialog(activity, activity.getString(R.string.grapes), "UVA"));
    }

    private static void openDialog(Activity activity, String titulo, String tipo) {
        FragmentActivity fragmentActivity = (FragmentActivity) activity;
        EstoqueDialogConfigActivity dialog = new EstoqueDialogConfigActivity(titulo, tipo, (texto, tipoRecebido) -> {
            switch (tipoRecebido) {
                case "CATEGORIA":
                    saveCommercialCategory(activity, texto);
                    break;
                case "COMPOSICAO":
                    saveGrapeComposition(activity, texto);
                    break;
                case "HARMONIZAÇÃO":
                    saveFoodPairiing(activity, texto);
                    break;
                case "NOTA":
                    saveTastingNote(activity, texto);
                    break;
                case "TIPO":
                    saveWineType(activity, texto);
                    break;
                case "UVA":
                    saveGrape(activity, texto);
                    break;
            }
        });

        dialog.show(fragmentActivity.getSupportFragmentManager(), "EstoqueDialogConfigActivity");
    }
    private static void saveCommercialCategory(Context context, String category){
        CommercialCategoryModel model = new CommercialCategoryModel();
        model.setName(category);

        CommercialCategoryDAO dao = new CommercialCategoryDAO(context);
        long id = dao.insert(model);

        if(id != -1){
            Toast.makeText(context, "Categoria salva com sucesso!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Erro: Categoria já existe ou falha ao salvar.", Toast.LENGTH_SHORT).show();
        }
    }

    private static void saveGrapeComposition(Context context, String composition){
        CompositionTypeModel model = new CompositionTypeModel();
        model.setCompositionName(composition);

        CompositionTypeDAO dao = new CompositionTypeDAO(context);
        long id = dao.insert(model);

        if(id != -1){
            Toast.makeText(context, "Tipo de composição salvo com sucesso!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Erro: Tipo de composição já existe ou falha ao salvar.", Toast.LENGTH_SHORT).show();
        }
    }

    private static void saveFoodPairiing(Context context, String foodPairing){
        FoodPairingModel model = new FoodPairingModel();
        model.setName(foodPairing);

        FoodPairingDAO dao = new FoodPairingDAO(context);
        long id = dao.insert(model);

        if(id != -1){
            Toast.makeText(context, "Harmonização salva com sucesso!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Erro: Harmonização já existe ou falha ao salvar.", Toast.LENGTH_SHORT).show();
        }
    }
    private static void saveTastingNote(Context context, String note){
        TastingNoteModel model = new TastingNoteModel();
        model.setNote(note);

        TastingNoteDAO dao = new TastingNoteDAO(context);
        long id = dao.insert(model);

        if(id != -1){
            Toast.makeText(context, "Nota de degustação salva com sucesso!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Erro: Nota de degustação já existe ou falha ao salvar.", Toast.LENGTH_SHORT).show();
        }
    }

    private static void saveWineType(Context context, String type){
        WineTypeModel model = new WineTypeModel();
        model.setTypeName(type);

        WineTypeDAO dao = new WineTypeDAO(context);
        long id = dao.insert(model);

        if(id != -1){
            Toast.makeText(context, "Tipo de vinho salvo com sucesso!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Erro: Tipo de vinho já existe ou falha ao salvar.", Toast.LENGTH_SHORT).show();
        }
    }

    private static void saveGrape(Context context, String grape){
        GrapeModel model = new GrapeModel();
        model.setName(grape);

        GrapeDAO dao = new GrapeDAO(context);
        long id = dao.insert(model);

        if(id != -1){
            Toast.makeText(context, "Uva salva com sucesso!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Erro: Uva já existe ou falha ao salvar.", Toast.LENGTH_SHORT).show();
        }
    }
}
