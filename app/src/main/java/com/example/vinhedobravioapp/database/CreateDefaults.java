package com.example.vinhedobravioapp.database;

import android.content.Context;
import android.util.Log;

import com.example.vinhedobravioapp.database.dao.CompositionTypeDAO;
import com.example.vinhedobravioapp.database.dao.InventoryMovementDAO;
import com.example.vinhedobravioapp.database.dao.UserDAO;
import com.example.vinhedobravioapp.database.dao.WineTypeDAO;
import com.example.vinhedobravioapp.database.dao.GeographicOriginDAO;
import com.example.vinhedobravioapp.database.dao.GrapeDAO;
import com.example.vinhedobravioapp.database.dao.TastingNoteDAO;
import com.example.vinhedobravioapp.database.dao.WineryDAO;
import com.example.vinhedobravioapp.database.dao.WineDAO;
import com.example.vinhedobravioapp.database.model.CompositionTypeModel;
import com.example.vinhedobravioapp.database.model.InventoryMovementModel;
import com.example.vinhedobravioapp.database.model.UserModel;
import com.example.vinhedobravioapp.database.model.WineTypeModel;
import com.example.vinhedobravioapp.database.model.GeographicOriginModel;
import com.example.vinhedobravioapp.database.model.GrapeModel;
import com.example.vinhedobravioapp.database.model.TastingNoteModel;
import com.example.vinhedobravioapp.database.model.WineryModel;
import com.example.vinhedobravioapp.database.model.WineModel;
import com.example.vinhedobravioapp.database.utils.GetAllFullWineModel;
import com.example.vinhedobravioapp.models.FullWineModel;

import java.util.List;

public class CreateDefaults {

    public static String listToString(List<?> list) {
        if (list == null || list.isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder();
        for (Object item : list) {
            sb.append(item.toString()).append("\n");
        }
        return sb.toString();
    }

    public static void Start(Context context) {
        ensureDefaultUsers(context);
        ensureDefaultCompositionTypes(context);
        ensureDefaultWineTypes(context);
        ensureDefaultGeographicOrigins(context);
        ensureDefaultGrapes(context);
        ensureDefaultTastingNotes(context);
        ensureDefaultWineries(context);
        ensureDefaultCommercialCategories(context);
        ensureDefaultWines(context);
        ensureDefaultInventoryMovements(context);
    }
public static void ensureDefaultCompositionTypes(Context context) {
    CompositionTypeDAO dao = new CompositionTypeDAO(context);
    List<CompositionTypeModel> list = dao.getAll();

    if (list.isEmpty()) {
        CompositionTypeModel tipo1 = new CompositionTypeModel();
        tipo1.setCompositionName("Varietal");
        dao.insert(tipo1);

        CompositionTypeModel tipo2 = new CompositionTypeModel();
        tipo2.setCompositionName("Blend");
        dao.insert(tipo2);

        CompositionTypeModel tipo3 = new CompositionTypeModel();
        tipo3.setCompositionName("Field Blend");
        dao.insert(tipo3);

        CompositionTypeModel tipo4 = new CompositionTypeModel();
        tipo4.setCompositionName("Cofermentado");
        dao.insert(tipo4);
    }
}

    public static void ensureDefaultUsers(Context context) {
        UserDAO userDAO = new UserDAO(context);
        List<UserModel> users = userDAO.getAll();
        if (users.size() < 2) {
            // Usuário Rep
            UserModel rep = new UserModel();
            rep.setName("Representante");
            rep.setEmail("rep@bravio.com");
            rep.setPassword("Rep");
            rep.setIsAdmin(0);
            rep.setStatus(1);
            rep.setCreatedAt(String.valueOf(System.currentTimeMillis()));
            rep.setLastUpdate(null);
            rep.setLastLogin(null);
            userDAO.insert(rep);

            // Usuário Adm
            UserModel adm = new UserModel();
            adm.setName("Administrador");
            adm.setEmail("adm@bravio.com");
            adm.setPassword("Adm");
            adm.setIsAdmin(1);
            adm.setStatus(1);
            adm.setCreatedAt(String.valueOf(System.currentTimeMillis()));
            adm.setLastUpdate(null);
            adm.setLastLogin(null);
            userDAO.insert(adm);
        }
        List<UserModel> usersAfter = userDAO.getAll();
    }

    public static void ensureDefaultWineTypes(Context context) {
        WineTypeDAO wineTypeDAO = new WineTypeDAO(context);
        List<WineTypeModel> wineTypes = wineTypeDAO.getAll();
        if (wineTypes == null || wineTypes.size() == 0) {
            WineTypeModel tinto = new WineTypeModel();
            tinto.setTypeName("Tinto");
            wineTypeDAO.insert(tinto);

            WineTypeModel seco = new WineTypeModel();
            seco.setTypeName("Seco");
            wineTypeDAO.insert(seco);
        }
        List<WineTypeModel> wineTypesAfter = wineTypeDAO.getAll();
    }

    public static void ensureDefaultGeographicOrigins(Context context) {
        GeographicOriginDAO originDAO = new GeographicOriginDAO(context);
        List<GeographicOriginModel> origins = originDAO.getAll();
        if (origins == null || origins.size() == 0) {
            GeographicOriginModel chileno = new GeographicOriginModel();
            chileno.setCountry("Chile");
            chileno.setRegion("");
            originDAO.insert(chileno);

            GeographicOriginModel brasileiro = new GeographicOriginModel();
            brasileiro.setCountry("Brasil");
            brasileiro.setRegion("");
            originDAO.insert(brasileiro);

            GeographicOriginModel argentino = new GeographicOriginModel();
            argentino.setCountry("Argentina");
            argentino.setRegion("");
            originDAO.insert(argentino);
        }
        List<GeographicOriginModel> originsAfter = originDAO.getAll();
    }

    public static void ensureDefaultGrapes(Context context) {
        GrapeDAO grapeDAO = new GrapeDAO(context);
        List<GrapeModel> grapes = grapeDAO.getAll();
        if (grapes == null || grapes.size() == 0) {
            GrapeModel cabernet = new GrapeModel();
            cabernet.setName("Cabernet Sauvignon");
            grapeDAO.insert(cabernet);

            GrapeModel merlot = new GrapeModel();
            merlot.setName("Merlot");
            grapeDAO.insert(merlot);
        }
        List<GrapeModel> grapesAfter = grapeDAO.getAll();
    }

    public static void ensureDefaultTastingNotes(Context context) {
        TastingNoteDAO tastingNoteDAO = new TastingNoteDAO(context);
        List<TastingNoteModel> notes = tastingNoteDAO.getAll();
        if (notes == null || notes.size() == 0) {
            TastingNoteModel frutasVermelhas = new TastingNoteModel();
            frutasVermelhas.setNote("Frutas vermelhas");
            tastingNoteDAO.insert(frutasVermelhas);

            TastingNoteModel frutasNegras = new TastingNoteModel();
            frutasNegras.setNote("Frutas negras");
            tastingNoteDAO.insert(frutasNegras);

            TastingNoteModel citricas = new TastingNoteModel();
            citricas.setNote("Cítricas");
            tastingNoteDAO.insert(citricas);
        }
        List<TastingNoteModel> notesAfter = tastingNoteDAO.getAll();
    }

    public static void ensureDefaultWineries(Context context) {
        WineryDAO wineryDAO = new WineryDAO(context);
        List<WineryModel> wineries = wineryDAO.getAll();
        if (wineries == null || wineries.size() == 0) {
            WineryModel margaux = new WineryModel();
            margaux.setName("Château Margaux");
            wineryDAO.insert(margaux);

            WineryModel vegaSicilia = new WineryModel();
            vegaSicilia.setName("Bodegas Vega Sicilia");
            wineryDAO.insert(vegaSicilia);

            WineryModel mondavi = new WineryModel();
            mondavi.setName("Robert Mondavi Winery");
            wineryDAO.insert(mondavi);

            WineryModel penfolds = new WineryModel();
            penfolds.setName("Penfolds");
            wineryDAO.insert(penfolds);
        }
        List<WineryModel> wineriesAfter = wineryDAO.getAll();
    }

    // Adiciona vinhos genéricos para testes ou inicialização
    public static void ensureDefaultWines(Context context) {
        WineDAO wineDAO = new WineDAO(context);
        List<WineModel> wines = wineDAO.getAll();
        Log.e("estoque1", "Total de vinhos: " + wines.size());

        List<FullWineModel> winesList = GetAllFullWineModel.getAll(context);

        Log.e("estoque1", "Total de vinhos: " + winesList.size());
        if (wines == null || wines.size() == 0) {
            WineModel vinho1 = new WineModel();
            vinho1.setName("Vinho Genérico 1");
            vinho1.setWineryId(1L); // Ajuste conforme IDs existentes
            vinho1.setWineTypeId(1L);
            vinho1.setCommercialCategoryId(1L);
            vinho1.setOriginId(1L);
            vinho1.setVintage("2020");
            vinho1.setDescription("Vinho de teste 1");
            vinho1.setCompositionTypeId(1L);
            vinho1.setAlcoholContent(13.5);
            vinho1.setVolume(750);
            vinho1.setAcidity("3.5");
            vinho1.setIdealTemperatureCelsius(16.0);
            vinho1.setAgingPotential("5 anos");
            vinho1.setUnitPrice(99.90);
            wineDAO.insert(vinho1);

            WineModel vinho2 = new WineModel();
            vinho2.setName("Vinho Genérico 2");
            vinho2.setWineryId(2L);
            vinho2.setWineTypeId(2L);
            vinho2.setCommercialCategoryId(1L);
            vinho2.setOriginId(2L);
            vinho2.setVintage("2021");
            vinho2.setDescription("Vinho de teste 2");
            vinho2.setCompositionTypeId(1L);
            vinho2.setAlcoholContent(12.0);
            vinho2.setVolume(750);
            vinho2.setAcidity("3.7");
            vinho2.setIdealTemperatureCelsius(14.0);
            vinho2.setAgingPotential("3 anos");
            vinho2.setUnitPrice(59.90);
            wineDAO.insert(vinho2);
        }
    }

    public static void ensureDefaultInventoryMovements(Context context) {
        WineDAO wineDAO = new WineDAO(context);
        List<WineModel> wines = wineDAO.getAll();
        if (wines.size() >= 2) {
            WineModel vinho1 = wines.get(0);
            WineModel vinho2 = wines.get(1);
            InventoryMovementDAO movementDAO = new InventoryMovementDAO(context);

            InventoryMovementModel mov1 = new InventoryMovementModel();
            mov1.setWineId(vinho1.getWineId());
            mov1.setMovementType("ENTRADA");
            mov1.setQuantity(50);
            mov1.setUnitPrice(99.90);
            mov1.setDocumentReference("NF-001");
            mov1.setUserId(1L);
            mov1.setNotes("Estoque inicial vinho 1");
            movementDAO.insert(mov1);

            InventoryMovementModel mov2 = new InventoryMovementModel();
            mov2.setWineId(vinho2.getWineId());
            mov2.setMovementType("ENTRADA");
            mov2.setQuantity(30);
            mov2.setUnitPrice(59.90);
            mov2.setDocumentReference("NF-002");
            mov2.setUserId(1L);
            mov2.setNotes("Estoque inicial vinho 2");
            movementDAO.insert(mov2);

            InventoryMovementModel movSaida = new InventoryMovementModel();
            movSaida.setWineId(vinho1.getWineId());
            movSaida.setMovementType("SAIDA");
            movSaida.setQuantity(5);
            movSaida.setUnitPrice(99.90);
            movSaida.setDocumentReference("VENDA-001");
            movSaida.setUserId(1L);
            movSaida.setNotes("Saída fictícia vinho 1");
            movementDAO.insert(movSaida);
            String movimentos = CreateDefaults.getAllInventoryMovementsAsString(context);
            Log.d("MovimentosEstoque", movimentos);
        }
    }

        public static String getAllInventoryMovementsAsString(Context context) {
            InventoryMovementDAO movementDAO = new InventoryMovementDAO(context);
            List<InventoryMovementModel> movements = movementDAO.getAll();
            if (movements == null || movements.isEmpty()) return "[]";
            StringBuilder sb = new StringBuilder();
            for (InventoryMovementModel mov : movements) {
                sb.append(mov.toString()).append("\n");
            }
            return sb.toString();
        }

    public static void ensureDefaultCommercialCategories(Context context) {
        com.example.vinhedobravioapp.database.dao.CommercialCategoryDAO dao = new com.example.vinhedobravioapp.database.dao.CommercialCategoryDAO(context);
        java.util.List<com.example.vinhedobravioapp.database.model.CommercialCategoryModel> list = dao.getAll();
        if (list == null || list.isEmpty()) {
            String[] categorias = {"Popular", "Intermediário", "Premium", "Superpremium"};
            for (String nome : categorias) {
                com.example.vinhedobravioapp.database.model.CommercialCategoryModel cat = new com.example.vinhedobravioapp.database.model.CommercialCategoryModel();
                cat.setName(nome);
                dao.insert(cat);
            }
        }
    }
}
