package com.example.vinhedobravioapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
        ensureDefaultFoodPairings(context);
        ensureDefaultCustomers(context);
        ensureDefaultWines(context);
        ensureDefaultInventoryMovements(context);
        ensureDefaultWineReviews(context);
    }
public static void ensureDefaultCompositionTypes(Context context) {
    CompositionTypeDAO dao = new CompositionTypeDAO(context);
    List<CompositionTypeModel> list = dao.getAll();
    if (list == null || list.isEmpty()) {
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
        if (users == null || users.size() < 2) {
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
        if (wineTypes == null || wineTypes.isEmpty()) {
            String[] tipos = {"Tinto", "Seco", "Branco", "Rosé", "Espumante"};
            for (String tipo : tipos) {
                WineTypeModel model = new WineTypeModel();
                model.setTypeName(tipo);
                wineTypeDAO.insert(model);
            }
        }
    }

    public static void ensureDefaultGeographicOrigins(Context context) {
        GeographicOriginDAO originDAO = new GeographicOriginDAO(context);
        List<GeographicOriginModel> origins = originDAO.getAll();
        if (origins == null || origins.isEmpty()) {
            // Brasil
            originDAO.insert(new GeographicOriginModel("Brasil", "Serra Gaúcha"));
            originDAO.insert(new GeographicOriginModel("Brasil", "Serra Mineira"));
            originDAO.insert(new GeographicOriginModel("Brasil", "Campanha Gaúcha"));
            originDAO.insert(new GeographicOriginModel("Brasil", "Vale dos Vinhedos"));
            originDAO.insert(new GeographicOriginModel("Brasil", "Planalto Catarinense"));
            // Chile
            originDAO.insert(new GeographicOriginModel("Chile", "Valle do Maipo"));
            originDAO.insert(new GeographicOriginModel("Chile", "Colchagua"));
            originDAO.insert(new GeographicOriginModel("Chile", "Casablanca"));
            originDAO.insert(new GeographicOriginModel("Chile", "Aconcágua"));
            // Argentina
            originDAO.insert(new GeographicOriginModel("Argentina", "Mendoza"));
            originDAO.insert(new GeographicOriginModel("Argentina", "Patagônia"));
            originDAO.insert(new GeographicOriginModel("Argentina", "Salta"));
            originDAO.insert(new GeographicOriginModel("Argentina", "San Juan"));
        }
    }

    public static void ensureDefaultGrapes(Context context) {
        GrapeDAO grapeDAO = new GrapeDAO(context);
        List<GrapeModel> grapes = grapeDAO.getAll();
        if (grapes == null || grapes.isEmpty()) {
            String[] uvas = {
                "Cabernet Sauvignon", "Merlot", "Malbec", "Chardonnay", "Syrah",
                "Pinot Noir", "Tannat", "Carmenère", "Sauvignon Blanc", "Tempranillo"
            };
            for (String uva : uvas) {
                GrapeModel model = new GrapeModel();
                model.setName(uva);
                grapeDAO.insert(model);
            }
        }
    }

    public static void ensureDefaultTastingNotes(Context context) {
        TastingNoteDAO tastingNoteDAO = new TastingNoteDAO(context);
        List<TastingNoteModel> notes = tastingNoteDAO.getAll();
        if (notes == null || notes.isEmpty()) {
            String[] notas = {
                "Frutas vermelhas", "Frutas negras", "Cítricas", "Especiarias", "Baunilha",
                "Chocolate", "Amadeirado", "Florais", "Herbáceo", "Tostado"
            };
            for (String nota : notas) {
                TastingNoteModel model = new TastingNoteModel();
                model.setNote(nota);
                tastingNoteDAO.insert(model);
            }
        }
    }

    public static void ensureDefaultWineries(Context context) {
        WineryDAO wineryDAO = new WineryDAO(context);
        List<WineryModel> wineries = wineryDAO.getAll();
        if (wineries == null || wineries.isEmpty()) {
            String[] vinicolas = {
                "Château Margaux", "Bodegas Vega Sicilia", "Robert Mondavi Winery", "Penfolds", "Miolo",
                "Concha y Toro", "Catena Zapata", "Salton", "Aurora", "Casa Valduga"
            };
            for (String nome : vinicolas) {
                WineryModel model = new WineryModel();
                model.setName(nome);
                wineryDAO.insert(model);
            }
        }
    }

    // Adiciona vinhos genéricos para testes ou inicialização
    public static void ensureDefaultWines(Context context) {
        WineDAO wineDAO = new WineDAO(context);
        List<WineModel> wines = wineDAO.getAll();
        Log.e("estoque1", "Total de vinhos: " + wines.size());

        if (wines == null || wines.isEmpty()) {
            String[] nomes = {
                "Vinho Genérico 1", "Vinho Genérico 2", "Vinho Genérico 3", "Vinho Genérico 4", "Vinho Genérico 5",
                "Vinho Genérico 6", "Vinho Genérico 7", "Vinho Genérico 8", "Vinho Genérico 9", "Vinho Genérico 10"
            };
            String[] descricoes = {
                "Vinho de teste 1", "Vinho de teste 2", "Vinho de teste 3", "Vinho de teste 4", "Vinho de teste 5",
                "Vinho de teste 6", "Vinho de teste 7", "Vinho de teste 8", "Vinho de teste 9", "Vinho de teste 10"
            };
            double[] teorAlcoolico = {13.5, 12.0, 14.0, 13.0, 12.5, 13.8, 14.2, 12.8, 13.1, 13.9};
            String[] acidez = {"3.5", "3.7", "3.6", "3.8", "3.4", "3.9", "3.3", "3.5", "3.6", "3.7"};
            double[] temperatura = {16.0, 14.0, 15.0, 16.5, 15.5, 14.5, 15.8, 16.2, 15.3, 14.8};
            String[] safra = {"2020", "2021", "2019", "2018", "2022", "2020", "2021", "2017", "2016", "2015"};
            double[] preco = {99.90, 59.90, 79.90, 89.90, 109.90, 69.90, 119.90, 129.90, 139.90, 149.90};

            for (int i = 0; i < 10; i++) {
                WineModel vinho = new WineModel();
                vinho.setName(nomes[i]);
                vinho.setWineryId((i % 5) + 1L); // alterna entre 5 vinícolas
                vinho.setWineTypeId(((i % 5) + 1L)); // alterna entre 5 tipos
                vinho.setCommercialCategoryId(((i % 5) + 1L));
                vinho.setOriginId(((i % 10) + 1L)); // alterna entre 10 origens
                vinho.setVintage(safra[i]);
                vinho.setDescription(descricoes[i]);
                vinho.setCompositionTypeId(((i % 4) + 1L));
                vinho.setAlcoholContent(teorAlcoolico[i]);
                vinho.setVolume(750);
                vinho.setAcidity(acidez[i]);
                vinho.setIdealTemperatureCelsius(temperatura[i]);
                vinho.setAgingPotential((3 + i) + " anos");
                vinho.setUnitPrice(preco[i]);
                wineDAO.insert(vinho);
            }
        }
    }

    public static void ensureDefaultInventoryMovements(Context context) {
        WineDAO wineDAO = new WineDAO(context);
        List<WineModel> wines = wineDAO.getAll();
        if (wines != null && wines.size() >= 10) {
            InventoryMovementDAO movementDAO = new InventoryMovementDAO(context);
            long userId = 1L;
            String[] docsEntrada = {"NF-100", "NF-101", "NF-102", "NF-103", "NF-104", "NF-105", "NF-106", "NF-107", "NF-108", "NF-109"};
            String[] docsSaida = {"VENDA-200", "VENDA-201", "VENDA-202", "VENDA-203", "VENDA-204", "VENDA-205", "VENDA-206", "VENDA-207", "VENDA-208", "VENDA-209"};
            double[] precosEntrada = {49.90, 39.90, 59.90, 44.90, 54.90, 42.90, 51.90, 47.90, 53.90, 45.90};
            double[] precosSaida = {89.90, 69.90, 99.90, 79.90, 109.90, 74.90, 95.90, 85.90, 105.90, 92.90};

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
            java.util.Calendar cal = java.util.Calendar.getInstance();

            for (int i = 0; i < 10; i++) {
                long wineId = wines.get(i).getWineId();

                // Entradas: uma por mês, 8 meses atrás até agora
                for (int m = 8; m >= 1; m--) {
                    cal.setTime(new java.util.Date());
                    cal.add(java.util.Calendar.MONTH, -m);
                    String dataEntrada = sdf.format(cal.getTime());

                    InventoryMovementModel entrada = new InventoryMovementModel();
                    entrada.setWineId(wineId);
                    entrada.setMovementType("ENTRADA");
                    entrada.setQuantity(30 + (i % 5) * 5 + m); // Quantidade variada
                    entrada.setUnitPrice(precosEntrada[i]);
                    entrada.setMovementDate(dataEntrada);
                    entrada.setDocumentReference(docsEntrada[i]);
                    entrada.setUserId(userId);
                    entrada.setNotes("Entrada estoque mês " + m + " vinho " + (i + 1));
                    movementDAO.insert(entrada);

                    // Saída: metade da quantidade da entrada, valor maior, data alguns dias após entrada
                    cal.add(java.util.Calendar.DAY_OF_MONTH, 10 + (i % 3));
                    String dataSaida = sdf.format(cal.getTime());

                    InventoryMovementModel saida = new InventoryMovementModel();
                    saida.setWineId(wineId);
                    saida.setMovementType("SAIDA");
                    saida.setQuantity((entrada.getQuantity() / 2) + (i % 2)); // Quantidade variada
                    saida.setUnitPrice(precosSaida[i]);
                    saida.setMovementDate(dataSaida);
                    saida.setDocumentReference(docsSaida[i]);
                    saida.setUserId(userId);
                    saida.setNotes("Venda fictícia mês " + m + " vinho " + (i + 1));
                    movementDAO.insert(saida);
                }
            }
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
            String[] categorias = {"Popular", "Intermediário", "Premium", "Superpremium", "Ultra Premium"};
            for (String nome : categorias) {
                com.example.vinhedobravioapp.database.model.CommercialCategoryModel cat = new com.example.vinhedobravioapp.database.model.CommercialCategoryModel();
                cat.setName(nome);
                dao.insert(cat);
            }
        }
    }

    public static void ensureDefaultWineReviews(Context context) {
        com.example.vinhedobravioapp.database.dao.WineReviewDAO reviewDAO = new com.example.vinhedobravioapp.database.dao.WineReviewDAO(context);
        java.util.List<com.example.vinhedobravioapp.database.model.WineReviewModel> reviews = reviewDAO.getAll();
        if (reviews == null || reviews.isEmpty()) {
            com.example.vinhedobravioapp.database.dao.WineDAO wineDAO = new com.example.vinhedobravioapp.database.dao.WineDAO(context);
            java.util.List<com.example.vinhedobravioapp.database.model.WineModel> wines = wineDAO.getAll();
            if (wines != null && !wines.isEmpty()) {
                String[] textos = {
                    "Excelente vinho, aromas intensos e final longo.",
                    "Muito equilibrado, ótimo custo-benefício.",
                    "Sabor marcante, taninos macios.",
                    "Boa acidez, combina bem com carnes.",
                    "Agradável ao paladar, recomendo para iniciantes."
                };
                double[] ratings = {4.5, 4.0, 3.8, 4.2, 3.5};
                int idx = 0;
                for (com.example.vinhedobravioapp.database.model.WineModel wine : wines) {
                    com.example.vinhedobravioapp.database.model.WineReviewModel review = new com.example.vinhedobravioapp.database.model.WineReviewModel();
                    review.setWineId(wine.getWineId());
                    review.setReviewText(textos[idx % textos.length]);
                    review.setRating(ratings[idx % ratings.length]);
                    reviewDAO.insert(review);
                    idx++;
                }
            }
        }
    }

    public static void ensureDefaultFoodPairings(Context context) {
        com.example.vinhedobravioapp.database.dao.FoodPairingDAO dao = new com.example.vinhedobravioapp.database.dao.FoodPairingDAO(context);
        java.util.List<com.example.vinhedobravioapp.database.model.FoodPairingModel> list = dao.getAll();
        if (list == null || list.isEmpty()) {
            String[] harmonizacoes = {
                "Carnes vermelhas", "Carnes brancas", "Massas", "Queijos", "Peixes",
                "Frutos do mar", "Risotos", "Sobremesas", "Aves", "Charcutaria"
            };
            for (String nome : harmonizacoes) {
                com.example.vinhedobravioapp.database.model.FoodPairingModel model = new com.example.vinhedobravioapp.database.model.FoodPairingModel();
                model.setName(nome);
                dao.insert(model);
            }
        }
    }

    public static void ensureDefaultCustomers(Context context) {
        com.example.vinhedobravioapp.database.dao.CustomerDAO dao = new com.example.vinhedobravioapp.database.dao.CustomerDAO(context);
        java.util.List<com.example.vinhedobravioapp.database.model.CustomerModel> list = dao.getAll();
        if (list == null || list.isEmpty()) {
            String[] nomes = {
                "Restaurante Sabor & Arte", "Adega do Vinho Fino", "Empório Gourmet Brasil", "Supermercado Bom Preço", "Bar do Zé",
                "Restaurante Bella Itália", "Mercado Central", "Bistrô Paris", "Cantina do Sul", "Churrascaria Gaúcha"
            };
            String[] cpfs = {
                "12.345.678/0001-90", "23.456.789/0001-01", "34.567.890/0001-12", "45.678.901/0001-23", "56.789.012/0001-34",
                "67.890.123/0001-45", "78.901.234/0001-56", "89.012.345/0001-67", "90.123.456/0001-78", "01.234.567/0001-89"
            };
            String[] enderecos = {
                "Rua das Flores, 123, Centro, Florianópolis",
                "Av. Central, 456, Centro, Blumenau",
                "Rua Gourmet, 789, Centro, Joinville",
                "Av. Brasil, 1010, Centro, Itajaí",
                "Rua do Lazer, 202, Centro, Balneário Camboriú",
                "Rua Itália, 321, Centro, Curitiba",
                "Praça XV, 100, Centro, Porto Alegre",
                "Rua Paris, 88, Centro, São Paulo",
                "Rua Sul, 55, Centro, Pelotas",
                "Av. Churrasco, 77, Centro, Caxias do Sul"
            };
            String[] regioes = {
                "Sul", "Vale do Itajaí", "Norte", "Litoral", "Litoral Norte",
                "Centro", "Sul", "Sudeste", "Sul", "Sul"
            };
            String[] telefones = {
                "(48) 99999-1111", "(47) 98888-2222", "(47) 97777-3333", "(47) 96666-4444", "(47) 95555-5555",
                "(41) 94444-6666", "(51) 93333-7777", "(11) 92222-8888", "(53) 91111-9999", "(54) 90000-0000"
            };
            String[] emails = {
                "contato@saborearte.com", "vendas@adegadovinhofino.com", "contato@emporiogourmet.com", "compras@bompreco.com", "bar@zebar.com",
                "contato@bellaitalia.com", "contato@mercadocentral.com", "contato@bistroparis.com", "contato@cantinadosul.com", "contato@churrascariagaucha.com"
            };

            for (int i = 0; i < 10; i++) {
                com.example.vinhedobravioapp.database.model.CustomerModel c = new com.example.vinhedobravioapp.database.model.CustomerModel();
                c.setNameCompanyName(nomes[i]);
                c.setCpfCnpj(cpfs[i]);
                c.setAddress(enderecos[i]);
                c.setRegion(regioes[i]);
                c.setPhone(telefones[i]);
                c.setEmail(emails[i]);
                dao.insert(c);
            }
        }
    }
}
