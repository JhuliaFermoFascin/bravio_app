package com.example.vinhedobravioapp.models;

import android.content.Context;
import com.example.vinhedobravioapp.database.dao.*;
import com.example.vinhedobravioapp.database.model.*;
import java.util.List;

public class FullWineModel  {
    private WineModel wine;
    private WineTypeModel wineType;
    private WineryModel winery;
    private CommercialCategoryModel commercialCategory;
    private GeographicOriginModel origin;
    private List<String> grapeNames;
    private List<String> tastingNotes;
    private List<String> foodPairings;
    private WineReviewModel wineReview;
    private String imageBase64;

    // Ramificação dos campos do WineModel
    private long wineId;
    private String name;
    private Long wineryId;
    private long wineTypeId;
    private Long commercialCategoryId;
    private Long originId;
    private String vintage;
    private String description;
    private long compositionTypeId;
    private Double alcoholContent;
    private Integer volume;
    private String acidity;
    private Double idealTemperatureCelsius;
    private String agingPotential;
    private Double unitPrice;

    public FullWineModel(Context context, long wineId) {
        WineDAO wineDAO = new WineDAO(context);
        WineTypeDAO wineTypeDAO = new WineTypeDAO(context);
        WineryDAO wineryDAO = new WineryDAO(context);
        CommercialCategoryDAO commercialCategoryDAO = new CommercialCategoryDAO(context);
        GeographicOriginDAO originDAO = new GeographicOriginDAO(context);
        WineGrapeDAO wineGrapeDAO = new WineGrapeDAO(context);
        GrapeDAO grapeDAO = new GrapeDAO(context);
        WineTastingNoteDAO wineTastingNoteDAO = new WineTastingNoteDAO(context);
        TastingNoteDAO tastingNoteDAO = new TastingNoteDAO(context);
        WineFoodPairingDAO wineFoodPairingDAO = new WineFoodPairingDAO(context);
        WineReviewDAO wineReviewDAO = new WineReviewDAO(context);
        WineImageDAO wineImageDAO = new WineImageDAO(context);

        this.wine = wineDAO.getById(wineId);
        if (wine != null) {
            this.wineType = wineTypeDAO.getById(wine.getWineTypeId());
            this.winery = wine.getWineryId() != null ? wineryDAO.getById(wine.getWineryId()) : null;
            this.commercialCategory = wine.getCommercialCategoryId() != null ? commercialCategoryDAO.getById(wine.getCommercialCategoryId()) : null;
            this.origin = wine.getOriginId() != null ? originDAO.getById(wine.getOriginId()) : null;
            this.grapeNames = wineGrapeDAO.getGrapeNamesByWineId(wineId, context);
            this.tastingNotes = wineTastingNoteDAO.getNoteNamesByWineId(wineId, context);
            this.foodPairings = wineFoodPairingDAO.getPairingNamesByWineId(wineId, context);
            this.wineReview = wineReviewDAO.getByWineId(wineId);
            WineImageModel img = wineImageDAO.getByWineId(wineId);
            this.imageBase64 = img != null ? img.getImageBase64() : null;

            // Ramificar campos do WineModel
            this.wineId = wine.getWineId();
            this.name = wine.getName();
            this.wineryId = wine.getWineryId();
            this.wineTypeId = wine.getWineTypeId();
            this.commercialCategoryId = wine.getCommercialCategoryId();
            this.originId = wine.getOriginId();
            this.vintage = wine.getVintage();
            this.description = wine.getDescription();
            this.compositionTypeId = wine.getCompositionTypeId();
            this.alcoholContent = wine.getAlcoholContent();
            this.volume = wine.getVolume();
            this.acidity = wine.getAcidity();
            this.idealTemperatureCelsius = wine.getIdealTemperatureCelsius();
            this.agingPotential = wine.getAgingPotential();
            this.unitPrice = wine.getUnitPrice();
        }
    }

    // Getters e setters dos campos ramificados
    public long getWineId() { return wineId; }
    public void setWineId(long wineId) { this.wineId = wineId; if (wine != null) wine.setWineId(wineId); }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; if (wine != null) wine.setName(name); }
    public Long getWineryId() { return wineryId; }
    public void setWineryId(Long wineryId) { this.wineryId = wineryId; if (wine != null) wine.setWineryId(wineryId); }
    public long getWineTypeId() { return wineTypeId; }
    public void setWineTypeId(long wineTypeId) { this.wineTypeId = wineTypeId; if (wine != null) wine.setWineTypeId(wineTypeId); }
    public Long getCommercialCategoryId() { return commercialCategoryId; }
    public void setCommercialCategoryId(Long commercialCategoryId) { this.commercialCategoryId = commercialCategoryId; if (wine != null) wine.setCommercialCategoryId(commercialCategoryId); }
    public Long getOriginId() { return originId; }
    public void setOriginId(Long originId) { this.originId = originId; if (wine != null) wine.setOriginId(originId); }
    public String getVintage() { return vintage; }
    public void setVintage(String vintage) { this.vintage = vintage; if (wine != null) wine.setVintage(vintage); }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; if (wine != null) wine.setDescription(description); }
    public long getCompositionTypeId() { return compositionTypeId; }
    public void setCompositionTypeId(long compositionTypeId) { this.compositionTypeId = compositionTypeId; if (wine != null) wine.setCompositionTypeId(compositionTypeId); }
    public Double getAlcoholContent() { return alcoholContent; }
    public void setAlcoholContent(Double alcoholContent) { this.alcoholContent = alcoholContent; if (wine != null) wine.setAlcoholContent(alcoholContent); }
    public Integer getVolume() { return volume; }
    public void setVolume(Integer volume) { this.volume = volume; if (wine != null) wine.setVolume(volume); }
    public String getAcidity() { return acidity; }
    public void setAcidity(String acidity) { this.acidity = acidity; if (wine != null) wine.setAcidity(acidity); }
    public Double getIdealTemperatureCelsius() { return idealTemperatureCelsius; }
    public void setIdealTemperatureCelsius(Double idealTemperatureCelsius) { this.idealTemperatureCelsius = idealTemperatureCelsius; if (wine != null) wine.setIdealTemperatureCelsius(idealTemperatureCelsius); }
    public String getAgingPotential() { return agingPotential; }
    public void setAgingPotential(String agingPotential) { this.agingPotential = agingPotential; if (wine != null) wine.setAgingPotential(agingPotential); }
    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; if (wine != null) wine.setUnitPrice(unitPrice); }

    // Getters and setters
    public WineModel getWine() { return wine; }
    public void setWine(WineModel wine) { this.wine = wine; }
    public WineTypeModel getWineType() { return wineType; }
    public void setWineType(WineTypeModel wineType) { this.wineType = wineType; }
    public WineryModel getWinery() { return winery; }
    public void setWinery(WineryModel winery) { this.winery = winery; }
    public CommercialCategoryModel getCommercialCategory() { return commercialCategory; }
    public void setCommercialCategory(CommercialCategoryModel commercialCategory) { this.commercialCategory = commercialCategory; }
    public GeographicOriginModel getOrigin() { return origin; }
    public void setOrigin(GeographicOriginModel origin) { this.origin = origin; }
    public List<String> getGrapeNames() { return grapeNames; }
    public void setGrapeNames(List<String> grapeNames) { this.grapeNames = grapeNames; }
    public List<String> getTastingNotes() { return tastingNotes; }
    public void setTastingNotes(List<String> tastingNotes) { this.tastingNotes = tastingNotes; }
    public List<String> getFoodPairings() { return foodPairings; }
    public void setFoodPairings(List<String> foodPairings) { this.foodPairings = foodPairings; }
    public WineReviewModel getWineReview() { return wineReview; }
    public void setWineReview(WineReviewModel wineReview) { this.wineReview = wineReview; }
    public String getImageBase64() { return imageBase64; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }

    // Save all changes to DB
    public void saveFullWine(Context context) {
        // Atualiza o objeto wine com os campos ramificados
        WineModel updatedWine = new WineModel();
        updatedWine.setWineId(this.wineId);
        updatedWine.setName(this.name);
        updatedWine.setWineryId(this.wineryId);
        updatedWine.setWineTypeId(this.wineTypeId);
        updatedWine.setCommercialCategoryId(this.commercialCategoryId);
        updatedWine.setOriginId(this.originId);
        updatedWine.setVintage(this.vintage);
        updatedWine.setDescription(this.description);
        updatedWine.setCompositionTypeId(this.compositionTypeId);
        updatedWine.setAlcoholContent(this.alcoholContent);
        updatedWine.setVolume(this.volume);
        updatedWine.setAcidity(this.acidity);
        updatedWine.setIdealTemperatureCelsius(this.idealTemperatureCelsius);
        updatedWine.setAgingPotential(this.agingPotential);
        updatedWine.setUnitPrice(this.unitPrice);
        this.wine = updatedWine;
        WineDAO wineDAO = new WineDAO(context);
        wineDAO.update(updatedWine);

        // Atualiza entidades relacionadas se existirem
        if (wineType != null) {
            WineTypeDAO wineTypeDAO = new WineTypeDAO(context);
            wineTypeDAO.update(wineType);
        }
        if (winery != null) {
            WineryDAO wineryDAO = new WineryDAO(context);
            wineryDAO.update(winery);
        }
        if (commercialCategory != null) {
            CommercialCategoryDAO commercialCategoryDAO = new CommercialCategoryDAO(context);
            commercialCategoryDAO.update(commercialCategory);
        }
        if (origin != null) {
            GeographicOriginDAO originDAO = new GeographicOriginDAO(context);
            originDAO.update(origin);
        }
        if (wineReview != null) {
            WineReviewDAO wineReviewDAO = new WineReviewDAO(context);
            wineReviewDAO.update(wineReview);
        }
        if (imageBase64 != null) {
            WineImageDAO wineImageDAO = new WineImageDAO(context);
            WineImageModel img = new WineImageModel();
            img.setWineId(updatedWine.getWineId());
            img.setImageBase64(imageBase64);
            wineImageDAO.update(img);
        }
        // Atualiza relações N:N (apaga e insere novamente)
        WineGrapeDAO wineGrapeDAO = new WineGrapeDAO(context);
        wineGrapeDAO.deleteByWineId(updatedWine.getWineId());
        if (grapeNames != null) {
            GrapeDAO grapeDAO = new GrapeDAO(context);
            for (String grapeName : grapeNames) {
                GrapeModel grape = grapeDAO.getByName(grapeName);
                if (grape != null) {
                    WineGrapeModel wg = new WineGrapeModel();
                    wg.setWineId(updatedWine.getWineId());
                    wg.setGrapeId(grape.getGrapeId());
                    wineGrapeDAO.insert(wg);
                }
            }
        }
        WineTastingNoteDAO wineTastingNoteDAO = new WineTastingNoteDAO(context);
        wineTastingNoteDAO.deleteByWineId(updatedWine.getWineId());
        if (tastingNotes != null) {
            TastingNoteDAO tastingNoteDAO = new TastingNoteDAO(context);
            for (String note : tastingNotes) {
                TastingNoteModel tnote = tastingNoteDAO.getByNoteText(note);
                if (tnote != null) {
                    WineTastingNoteModel wtn = new WineTastingNoteModel();
                    wtn.setWineId(updatedWine.getWineId());
                    wtn.setTastingNoteId(tnote.getTastingNoteId());
                    wineTastingNoteDAO.insert(wtn);
                }
            }
        }
        WineFoodPairingDAO wineFoodPairingDAO = new WineFoodPairingDAO(context);
        wineFoodPairingDAO.deleteByWineId(updatedWine.getWineId());
        if (foodPairings != null) {
            FoodPairingDAO foodPairingDAO = new FoodPairingDAO(context);
            for (String food : foodPairings) {
                FoodPairingModel fp = foodPairingDAO.getByName(food);
                if (fp != null) {
                    WineFoodPairingModel wfp = new WineFoodPairingModel();
                    wfp.setWineId(updatedWine.getWineId());
                    wfp.setFoodPairingId(fp.getFoodPairingId());
                    wineFoodPairingDAO.insert(wfp);
                }
            }
        }
    }
}
