package com.example.vinhedobravioapp.ui.components.vinhos.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.vinhedobravioapp.database.dao.CommercialCategoryDAO;
import com.example.vinhedobravioapp.database.dao.CompositionTypeDAO;
import com.example.vinhedobravioapp.database.dao.GeographicOriginDAO;
import com.example.vinhedobravioapp.database.dao.GrapeDAO;
import com.example.vinhedobravioapp.database.dao.WineFoodPairingDAO;
import com.example.vinhedobravioapp.database.dao.WineGrapeDAO;
import com.example.vinhedobravioapp.database.dao.WineReviewDAO;
import com.example.vinhedobravioapp.database.dao.WineTastingNoteDAO;
import com.example.vinhedobravioapp.database.dao.WineryDAO;
import com.example.vinhedobravioapp.database.model.CommercialCategoryModel;
import com.example.vinhedobravioapp.database.model.CompositionTypeModel;
import com.example.vinhedobravioapp.database.model.GeographicOriginModel;
import com.example.vinhedobravioapp.database.model.GrapeModel;
import com.example.vinhedobravioapp.database.model.WineModel;
import com.example.vinhedobravioapp.database.model.WineReviewModel;
import com.example.vinhedobravioapp.database.model.WineryModel;

import java.util.ArrayList;
import java.util.List;

public class WineDataHelper {

    public static class WineCompleteData {
        public WineModel wine;
        public WineryModel winery;
        public CommercialCategoryModel commercialCategory;
        public GeographicOriginModel geographicOrigin;
        public CompositionTypeModel compositionType;
        public WineReviewModel wineReview;
        public List<String> grapeNames;
        public List<String> foodPairings;
        public List<String> tastingNotes;
        public String grapesConcatenated;
        public String foodPairingsConcatenated;
        public String tastingNotesConcatenated;
        public Bitmap wineImageBitmap;
    }

    public static WineCompleteData getCompleteData(Context context, WineModel wine) {
        WineCompleteData data = new WineCompleteData();

        data.wine = wine;

        WineryDAO wineryDAO = new WineryDAO(context);
        data.winery = wineryDAO.getById(wine.getWineryId());

        CommercialCategoryDAO commercialCategoryDAO = new CommercialCategoryDAO(context);
        data.commercialCategory = commercialCategoryDAO.getById(wine.getCommercialCategoryId());

        GeographicOriginDAO geographicOriginDAO = new GeographicOriginDAO(context);
        data.geographicOrigin = geographicOriginDAO.getById(wine.getOriginId());

        CompositionTypeDAO compositionTypeDAO = new CompositionTypeDAO(context);
        data.compositionType = compositionTypeDAO.getById(wine.getCompositionTypeId());

        WineReviewDAO wineReviewDAO = new WineReviewDAO(context);
        data.wineReview = wineReviewDAO.getByWineId(wine.getWineId());

        WineGrapeDAO wineGrapeDAO = new WineGrapeDAO(context);
        GrapeDAO grapeDAO = new GrapeDAO(context);
        List<Long> grapeIds = wineGrapeDAO.getGrapeIdsByWineId(wine.getWineId());
        data.grapeNames = new ArrayList<>();
        for (Long grapeId : grapeIds) {
            GrapeModel grapeModel = grapeDAO.getById(grapeId);
            if (grapeModel != null) {
                data.grapeNames.add(grapeModel.getName());
            }
        }
        data.grapesConcatenated = String.join(", ", data.grapeNames);

        WineFoodPairingDAO wineFoodPairingDAO = new WineFoodPairingDAO(context);
        data.foodPairings = wineFoodPairingDAO.getPairingNamesByWineId(wine.getWineId(), context);
        data.foodPairingsConcatenated = String.join(", ", data.foodPairings);

        WineTastingNoteDAO wineTastingNoteDAO = new WineTastingNoteDAO(context);
        data.tastingNotes = wineTastingNoteDAO.getNoteNamesByWineId(wine.getWineId(), context);
        data.tastingNotesConcatenated = String.join(", ", data.tastingNotes);

        data.wineImageBitmap = decodeBase64ToBitmap(wine.getImageBase64());

        return data;
    }

    private static Bitmap decodeBase64ToBitmap(String base64Str) {
        if (base64Str == null || base64Str.isEmpty()) return null;
        try {
            byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }
}