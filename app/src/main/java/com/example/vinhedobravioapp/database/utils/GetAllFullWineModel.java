package com.example.vinhedobravioapp.database.utils;

import android.content.Context;
import com.example.vinhedobravioapp.models.FullWineModel;
import com.example.vinhedobravioapp.database.dao.WineDAO;
import com.example.vinhedobravioapp.database.model.WineModel;
import java.util.List;
import java.util.ArrayList;

public class GetAllFullWineModel {
    public static List<FullWineModel> getAll(Context context) {
        WineDAO wineDAO = new WineDAO(context);
        List<WineModel> wineModels = wineDAO.getAll();
        List<FullWineModel> fullWineList = new ArrayList<>();
        for (WineModel wine : wineModels) {
            fullWineList.add(new FullWineModel(context, wine.getWineId()));
        }
        return fullWineList;
    }
}
