<<<<<<<< HEAD:app/src/main/java/com/example/vinhedobravioapp/database/dao/DaoCrudTester.java
package com.example.vinhedobravioapp.database.dao;
========
package com.example.vinhedobravioapp.ui.components.utils;
>>>>>>>> erick:app/src/main/java/com/example/vinhedobravioapp/ui/components/utils/DaoCrudTester.java

import android.content.Context;

import com.example.vinhedobravioapp.database.model.*;

import java.util.List;

public class DaoCrudTester {
    public static void testAllDaos(Context context) {
        // UserDAO
        UserDAO userDAO = new UserDAO(context);
        UserModel user = new UserModel();
        user.setName("Usuário Teste");
        user.setEmail("teste@user.com");
        user.setPassword("1234");
        user.setIsAdmin(0);
        user.setStatus(1);
        user.setCreatedAt("2025-06-16");
        user.setLastUpdate(null);
        user.setLastLogin(null);
        long userId = userDAO.insert(user);
        UserModel loadedUser = userDAO.getById(userId);
        user.setUserId(userId);
        user.setName("Usuário Atualizado");
        userDAO.update(user);
        userDAO.delete(userId);

        // CustomerDAO
        CustomerDAO customerDAO = new CustomerDAO(context);
        CustomerModel customer = new CustomerModel();
        customer.setNameCompanyName("Empresa Teste");
        customer.setCpfCnpj("12345678900");
        customer.setAddress("Rua Teste, 123");
        customer.setRegion("Centro");
        customer.setPhone("(11) 99999-9999");
        customer.setEmail("teste@empresa.com");
        long customerId = customerDAO.insert(customer);
        CustomerModel loadedCustomer = customerDAO.getById(customerId);
        customer.setCustomerId(customerId);
        customer.setNameCompanyName("Empresa Atualizada");
        customerDAO.update(customer);
        customerDAO.delete(customerId);

        // WineTypeDAO
        WineTypeDAO wineTypeDAO = new WineTypeDAO(context);
        WineTypeModel wineType = new WineTypeModel();
        wineType.setTypeName("Tipo Teste");
        long wineTypeId = wineTypeDAO.insert(wineType);
        WineTypeModel loadedWineType = wineTypeDAO.getById(wineTypeId);
        wineType.setWineTypeId(wineTypeId);
        wineType.setTypeName("Tipo Atualizado");
        wineTypeDAO.update(wineType);
        wineTypeDAO.delete(wineTypeId);

        // WineryDAO
        WineryDAO wineryDAO = new WineryDAO(context);
        WineryModel winery = new WineryModel();
        winery.setName("Winery Teste");
        long wineryId = wineryDAO.insert(winery);
        WineryModel loadedWinery = wineryDAO.getById(wineryId);
        winery.setWineryId(wineryId);
        winery.setName("Winery Atualizada");
        wineryDAO.update(winery);
        wineryDAO.delete(wineryId);

        // CommercialCategoryDAO
        CommercialCategoryDAO commercialCategoryDAO = new CommercialCategoryDAO(context);
        CommercialCategoryModel category = new CommercialCategoryModel();
        category.setName("Categoria Teste");
        long categoryId = commercialCategoryDAO.insert(category);
        CommercialCategoryModel loadedCategory = commercialCategoryDAO.getById(categoryId);
        category.setCategoryId(categoryId);
        category.setName("Categoria Atualizada");
        commercialCategoryDAO.update(category);
        commercialCategoryDAO.delete(categoryId);

        // GeographicOriginDAO
        GeographicOriginDAO originDAO = new GeographicOriginDAO(context);
        GeographicOriginModel origin = new GeographicOriginModel();
        origin.setCountry("Brasil");
        origin.setRegion("Sul");
        long originId = originDAO.insert(origin);
        GeographicOriginModel loadedOrigin = originDAO.getById(originId);
        origin.setOriginId(originId);
        origin.setCountry("Argentina");
        origin.setRegion("Mendoza");
        originDAO.update(origin);
        originDAO.delete(originId);

        // GrapeDAO
        GrapeDAO grapeDAO = new GrapeDAO(context);
        GrapeModel grape = new GrapeModel();
        grape.setName("Grape Teste");
        long grapeId = grapeDAO.insert(grape);
        GrapeModel loadedGrape = grapeDAO.getById(grapeId);
        grape.setGrapeId(grapeId);
        grape.setName("Grape Atualizada");
        grapeDAO.update(grape);
        grapeDAO.delete(grapeId);

        // WineDAO
        WineDAO wineDAO = new WineDAO(context);
        WineModel wine = new WineModel();
        wine.setName("Vinho Teste");
        wine.setWineryId(null);
        wine.setWineTypeId(1L);
        wine.setCommercialCategoryId(null);
        wine.setOriginId(null);
        wine.setVintage("2020");
        wine.setDescription("Descrição Teste");
        wine.setCompositionType("Composição Teste");
        wine.setTastingNoteId(null);
        wine.setFoodPairings("Carne");
        wine.setAlcoholContent(12.5);
        wine.setVolume(750);
        wine.setGrapeId(null);
        wine.setAcidity("Média");
        wine.setIdealTemperatureCelsius(16.0);
        wine.setAgingPotential("5 anos");
        long wineId = wineDAO.insert(wine);
        WineModel loadedWine = wineDAO.getById(wineId);
        wine.setWineId(wineId);
        wine.setName("Vinho Atualizado");
        wineDAO.update(wine);
        wineDAO.delete(wineId);

        // WineGrapeDAO
        WineGrapeDAO wineGrapeDAO = new WineGrapeDAO(context);
        WineGrapeModel wineGrape = new WineGrapeModel();
        wineGrape.setWineId(1L);
        wineGrape.setGrapeId(1L);
        wineGrapeDAO.insert(wineGrape);
        WineGrapeModel loadedWineGrape = wineGrapeDAO.getById(1L, 1L);
        wineGrape.setWineId(1L);
        wineGrape.setGrapeId(1L);
        wineGrapeDAO.update(wineGrape);
        wineGrapeDAO.delete(1L, 1L);

        // WineReviewDAO
        WineReviewDAO wineReviewDAO = new WineReviewDAO(context);
        WineReviewModel wineReview = new WineReviewModel();
        wineReview.setWineId(1L);
        wineReview.setReviewText("Ótimo vinho!");
        wineReview.setRating(4.5);
        long wineReviewId = wineReviewDAO.insert(wineReview);
        wineReview.setReviewId(wineReviewId);
        wineReview.setReviewText("Excelente!");
        wineReviewDAO.update(wineReview);
        wineReviewDAO.delete(wineReviewId);

        // TastingNoteDAO
        TastingNoteDAO tastingNoteDAO = new TastingNoteDAO(context);
        TastingNoteModel note = new TastingNoteModel();
        note.setNote("Nota Teste");
        long noteId = tastingNoteDAO.insert(note);
        TastingNoteModel loadedNote = tastingNoteDAO.getById(noteId);
        note.setTastingNoteId(noteId);
        note.setNote("Nota Atualizada");
        tastingNoteDAO.update(note);
        tastingNoteDAO.delete(noteId);

        // OrderDAO
        OrderDAO orderDAO = new OrderDAO(context);
        OrderModel order = new OrderModel();
        order.setCustomerId(1L);
        order.setDate("2025-06-16");
        order.setStatus("Novo");
        order.setUserId(1L);
        long orderId = orderDAO.insert(order);
        OrderModel loadedOrder = orderDAO.getById(orderId);
        order.setOrderId(orderId);
        order.setStatus("Atualizado");
        orderDAO.update(order);
        orderDAO.delete(orderId);

        // OrderItemDAO
        OrderItemDAO orderItemDAO = new OrderItemDAO(context);
        OrderItemModel orderItem = new OrderItemModel();
        orderItem.setWineId(1L);
        orderItem.setValue(100.0);
        orderItem.setQuantity(2);
        orderItem.setOrderId(1L);
        long orderItemId = orderItemDAO.insert(orderItem);
        OrderItemModel loadedOrderItem = orderItemDAO.getById(orderItemId);
        orderItem.setOrderItemId(orderItemId);
        orderItem.setValue(200.0);
        orderItemDAO.update(orderItem);
        orderItemDAO.delete(orderItemId);

        // InventoryMovementDAO
        InventoryMovementDAO inventoryMovementDAO = new InventoryMovementDAO(context);
        InventoryMovementModel movement = new InventoryMovementModel();
        movement.setWineId(1L);
        movement.setMovementType("Entrada");
        movement.setQuantity(10);
        movement.setUnitPrice(50.0);
        movement.setMovementDate("2025-06-16");
        movement.setDocumentReference("DOC123");
        movement.setUserId(1L);
        movement.setNotes("Nota teste");
        long movementId = inventoryMovementDAO.insert(movement);
        InventoryMovementModel loadedMovement = inventoryMovementDAO.getById(movementId);
        movement.setMovementId(movementId);
        movement.setNotes("Nota atualizada");
        inventoryMovementDAO.update(movement);
        inventoryMovementDAO.delete(movementId);

        // VisitDAO
        VisitDAO visitDAO = new VisitDAO(context);
        VisitModel visit = new VisitModel();
        visit.setCustomerId(1L);
        visit.setDateTime("2025-06-16 10:00:00");
        visit.setLocation("Local Teste");
        visit.setWines("Vinho A, Vinho B");
        visit.setUserId(1L);
        long visitId = visitDAO.insert(visit);
        VisitModel loadedVisit = visitDAO.getById(visitId);
        visit.setVisitId(visitId);
        visit.setLocation("Local Atualizado");
        visitDAO.update(visit);
        visitDAO.delete(visitId);

        // WineImageDAO
        WineImageDAO wineImageDAO = new WineImageDAO(context);
        WineImageModel wineImage = new WineImageModel();
        wineImage.setWineId(1L);
        wineImage.setImageBase64("dGVzdGVpbWFnZQ==");
        long wineImageId = wineImageDAO.insert(wineImage);
        WineImageModel loadedWineImage = wineImageDAO.getByWineId(1L);
        wineImage.setImageBase64("dXBkYXRlaW1hZ2U=");
        wineImageDAO.update(wineImage);
        wineImageDAO.delete(1L);
    }

        public static String listToString(List<?> list) {
        if (list == null || list.isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder();
        for (Object item : list) {
            sb.append(item.toString()).append("\n");
        }
        return sb.toString();
    }
    
}
