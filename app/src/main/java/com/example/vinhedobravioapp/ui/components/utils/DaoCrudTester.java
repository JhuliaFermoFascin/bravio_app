package com.example.vinhedobravioapp.ui.components.utils;

import android.content.Context;
import android.util.Log;

import com.example.vinhedobravioapp.database.dao.*;
import com.example.vinhedobravioapp.database.model.*;

import java.util.List;

public class DaoCrudTester {
    public static void testAllDaos(Context context) {
         // Ordem conforme DPOpenHelper
        // UserDAO
        UserDAO userDAO = new UserDAO(context);
        Log.d("DaoCrudTester", "User - getAll (antes): " + listToString(userDAO.getAll()));
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
        Log.d("DaoCrudTester", "User - getAll (após insert): " + listToString(userDAO.getAll()));
        UserModel loadedUser = userDAO.getById(userId);
        Log.d("DaoCrudTester", "User - getById: " + (loadedUser != null ? loadedUser.getName() : "null"));
        user.setUserId(userId);
        user.setName("Usuário Atualizado");
        userDAO.update(user);
        Log.d("DaoCrudTester", "User - getAll (após update): " + listToString(userDAO.getAll()));
        userDAO.delete(userId);
        Log.d("DaoCrudTester", "User - getAll (após delete): " + listToString(userDAO.getAll()));

        // CustomerDAO
        CustomerDAO customerDAO = new CustomerDAO(context);
        Log.d("DaoCrudTester", "Customer - getAll (antes): " + listToString(customerDAO.getAll()));
        CustomerModel customer = new CustomerModel();
        customer.setNameCompanyName("Empresa Teste");
        customer.setCpfCnpj("12345678900");
        customer.setAddress("Rua Teste, 123");
        customer.setRegion("Centro");
        customer.setPhone("(11) 99999-9999");
        customer.setEmail("teste@empresa.com");
        long customerId = customerDAO.insert(customer);
        Log.d("DaoCrudTester", "Customer - getAll (após insert): " + listToString(customerDAO.getAll()));
        CustomerModel loadedCustomer = customerDAO.getById(customerId);
        Log.d("DaoCrudTester", "Customer - getById: " + (loadedCustomer != null ? loadedCustomer.getNameCompanyName() : "null"));
        customer.setCustomerId(customerId);
        customer.setNameCompanyName("Empresa Atualizada");
        customerDAO.update(customer);
        Log.d("DaoCrudTester", "Customer - getAll (após update): " + listToString(customerDAO.getAll()));
        customerDAO.delete(customerId);
        Log.d("DaoCrudTester", "Customer - getAll (após delete): " + listToString(customerDAO.getAll()));

        // WineTypeDAO
        WineTypeDAO wineTypeDAO = new WineTypeDAO(context);
        Log.d("DaoCrudTester", "WineType - getAll (antes): " + listToString(wineTypeDAO.getAll()));
        WineTypeModel wineType = new WineTypeModel();
        wineType.setTypeName("Tipo Teste");
        long wineTypeId = wineTypeDAO.insert(wineType);
        Log.d("DaoCrudTester", "WineType - getAll (após insert): " + listToString(wineTypeDAO.getAll()));
        WineTypeModel loadedWineType = wineTypeDAO.getById(wineTypeId);
        Log.d("DaoCrudTester", "WineType - getById: " + (loadedWineType != null ? loadedWineType.getTypeName() : "null"));
        wineType.setWineTypeId(wineTypeId);
        wineType.setTypeName("Tipo Atualizado");
        wineTypeDAO.update(wineType);
        Log.d("DaoCrudTester", "WineType - getAll (após update): " + listToString(wineTypeDAO.getAll()));
        wineTypeDAO.delete(wineTypeId);
        Log.d("DaoCrudTester", "WineType - getAll (após delete): " + listToString(wineTypeDAO.getAll()));

        // WineryDAO
        WineryDAO wineryDAO = new WineryDAO(context);
        Log.d("DaoCrudTester", "Winery - getAll (antes): " + listToString(wineryDAO.getAll()));
        WineryModel winery = new WineryModel();
        winery.setName("Winery Teste");
        long wineryId = wineryDAO.insert(winery);
        Log.d("DaoCrudTester", "Winery - getAll (após insert): " + listToString(wineryDAO.getAll()));
        WineryModel loadedWinery = wineryDAO.getById(wineryId);
        Log.d("DaoCrudTester", "Winery - getById: " + (loadedWinery != null ? loadedWinery.getName() : "null"));
        winery.setWineryId(wineryId);
        winery.setName("Winery Atualizada");
        wineryDAO.update(winery);
        Log.d("DaoCrudTester", "Winery - getAll (após update): " + listToString(wineryDAO.getAll()));
        wineryDAO.delete(wineryId);
        Log.d("DaoCrudTester", "Winery - getAll (após delete): " + listToString(wineryDAO.getAll()));

        // CommercialCategoryDAO
        CommercialCategoryDAO commercialCategoryDAO = new CommercialCategoryDAO(context);
        Log.d("DaoCrudTester", "CommercialCategory - getAll (antes): " + listToString(commercialCategoryDAO.getAll()));
        CommercialCategoryModel category = new CommercialCategoryModel();
        category.setName("Categoria Teste");
        long categoryId = commercialCategoryDAO.insert(category);
        Log.d("DaoCrudTester", "CommercialCategory - getAll (após insert): " + listToString(commercialCategoryDAO.getAll()));
        CommercialCategoryModel loadedCategory = commercialCategoryDAO.getById(categoryId);
        Log.d("DaoCrudTester", "CommercialCategory - getById: " + (loadedCategory != null ? loadedCategory.getName() : "null"));
        category.setCategoryId(categoryId);
        category.setName("Categoria Atualizada");
        commercialCategoryDAO.update(category);
        Log.d("DaoCrudTester", "CommercialCategory - getAll (após update): " + listToString(commercialCategoryDAO.getAll()));
        commercialCategoryDAO.delete(categoryId);
        Log.d("DaoCrudTester", "CommercialCategory - getAll (após delete): " + listToString(commercialCategoryDAO.getAll()));

        // GeographicOriginDAO
        GeographicOriginDAO originDAO = new GeographicOriginDAO(context);
        Log.d("DaoCrudTester", "GeographicOrigin - getAll (antes): " + listToString(originDAO.getAll()));
        GeographicOriginModel origin = new GeographicOriginModel();
        origin.setCountry("Brasil");
        origin.setRegion("Sul");
        long originId = originDAO.insert(origin);
        Log.d("DaoCrudTester", "GeographicOrigin - getAll (após insert): " + listToString(originDAO.getAll()));
        GeographicOriginModel loadedOrigin = originDAO.getById(originId);
        Log.d("DaoCrudTester", "GeographicOrigin - getById: " + (loadedOrigin != null ? loadedOrigin.getCountry() : "null"));
        origin.setOriginId(originId);
        origin.setCountry("Argentina");
        origin.setRegion("Mendoza");
        originDAO.update(origin);
        Log.d("DaoCrudTester", "GeographicOrigin - getAll (após update): " + listToString(originDAO.getAll()));
        originDAO.delete(originId);
        Log.d("DaoCrudTester", "GeographicOrigin - getAll (após delete): " + listToString(originDAO.getAll()));

        // GrapeDAO
        GrapeDAO grapeDAO = new GrapeDAO(context);
        Log.d("DaoCrudTester", "Grape - getAll (antes): " + listToString(grapeDAO.getAll()));
        GrapeModel grape = new GrapeModel();
        grape.setName("Grape Teste");
        long grapeId = grapeDAO.insert(grape);
        Log.d("DaoCrudTester", "Grape - getAll (após insert): " + listToString(grapeDAO.getAll()));
        GrapeModel loadedGrape = grapeDAO.getById(grapeId);
        Log.d("DaoCrudTester", "Grape - getById: " + (loadedGrape != null ? loadedGrape.getName() : "null"));
        grape.setGrapeId(grapeId);
        grape.setName("Grape Atualizada");
        grapeDAO.update(grape);
        Log.d("DaoCrudTester", "Grape - getAll (após update): " + listToString(grapeDAO.getAll()));
        grapeDAO.delete(grapeId);
        Log.d("DaoCrudTester", "Grape - getAll (após delete): " + listToString(grapeDAO.getAll()));

        // WineDAO
        WineDAO wineDAO = new WineDAO(context);
        Log.d("DaoCrudTester", "Wine - getAll (antes): " + listToString(wineDAO.getAll()));
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
        Log.d("DaoCrudTester", "Wine - getAll (após insert): " + listToString(wineDAO.getAll()));
        WineModel loadedWine = wineDAO.getById(wineId);
        Log.d("DaoCrudTester", "Wine - getById: " + (loadedWine != null ? loadedWine.getName() : "null"));
        wine.setWineId(wineId);
        wine.setName("Vinho Atualizado");
        wineDAO.update(wine);
        Log.d("DaoCrudTester", "Wine - getAll (após update): " + listToString(wineDAO.getAll()));
        wineDAO.delete(wineId);
        Log.d("DaoCrudTester", "Wine - getAll (após delete): " + listToString(wineDAO.getAll()));

        // WineGrapeDAO
        WineGrapeDAO wineGrapeDAO = new WineGrapeDAO(context);
        Log.d("DaoCrudTester", "WineGrape - getAll (antes): " + listToString(wineGrapeDAO.getAll()));
        WineGrapeModel wineGrape = new WineGrapeModel();
        wineGrape.setWineId(1L);
        wineGrape.setGrapeId(1L);
        wineGrapeDAO.insert(wineGrape);
        Log.d("DaoCrudTester", "WineGrape - getAll (após insert): " + listToString(wineGrapeDAO.getAll()));
        WineGrapeModel loadedWineGrape = wineGrapeDAO.getById(1L, 1L);
        Log.d("DaoCrudTester", "WineGrape - getById: " + (loadedWineGrape != null ? loadedWineGrape.toString() : "null"));
        wineGrape.setWineId(1L);
        wineGrape.setGrapeId(1L);
        wineGrapeDAO.update(wineGrape);
        Log.d("DaoCrudTester", "WineGrape - getAll (após update): " + listToString(wineGrapeDAO.getAll()));
        wineGrapeDAO.delete(1L, 1L);
        Log.d("DaoCrudTester", "WineGrape - getAll (após delete): " + listToString(wineGrapeDAO.getAll()));

        // WineReviewDAO
        WineReviewDAO wineReviewDAO = new WineReviewDAO(context);
        Log.d("DaoCrudTester", "WineReview - getAll (antes): " + listToString(wineReviewDAO.getAll()));
        WineReviewModel wineReview = new WineReviewModel();
        wineReview.setWineId(1L);
        wineReview.setReviewText("Ótimo vinho!");
        wineReview.setRating(4.5);
        long wineReviewId = wineReviewDAO.insert(wineReview);
        Log.d("DaoCrudTester", "WineReview - getAll (após insert): " + listToString(wineReviewDAO.getAll()));
        // Supondo que tenha getById, se não, pode pular
        // WineReviewModel loadedWineReview = wineReviewDAO.getById(wineReviewId);
        // Log.d("DaoCrudTester", "WineReview - getById: " + (loadedWineReview != null ? loadedWineReview.toString() : "null"));
        wineReview.setReviewId(wineReviewId);
        wineReview.setReviewText("Excelente!");
        wineReviewDAO.update(wineReview);
        Log.d("DaoCrudTester", "WineReview - getAll (após update): " + listToString(wineReviewDAO.getAll()));
        wineReviewDAO.delete(wineReviewId);
        Log.d("DaoCrudTester", "WineReview - getAll (após delete): " + listToString(wineReviewDAO.getAll()));

        // TastingNoteDAO
        TastingNoteDAO tastingNoteDAO = new TastingNoteDAO(context);
        Log.d("DaoCrudTester", "TastingNote - getAll (antes): " + listToString(tastingNoteDAO.getAll()));
        TastingNoteModel note = new TastingNoteModel();
        note.setNote("Nota Teste");
        long noteId = tastingNoteDAO.insert(note);
        Log.d("DaoCrudTester", "TastingNote - getAll (após insert): " + listToString(tastingNoteDAO.getAll()));
        TastingNoteModel loadedNote = tastingNoteDAO.getById(noteId);
        Log.d("DaoCrudTester", "TastingNote - getById: " + (loadedNote != null ? loadedNote.getNote() : "null"));
        note.setTastingNoteId(noteId);
        note.setNote("Nota Atualizada");
        tastingNoteDAO.update(note);
        Log.d("DaoCrudTester", "TastingNote - getAll (após update): " + listToString(tastingNoteDAO.getAll()));
        tastingNoteDAO.delete(noteId);
        Log.d("DaoCrudTester", "TastingNote - getAll (após delete): " + listToString(tastingNoteDAO.getAll()));

        // OrderDAO
        OrderDAO orderDAO = new OrderDAO(context);
        Log.d("DaoCrudTester", "Order - getAll (antes): " + listToString(orderDAO.getAll()));
        OrderModel order = new OrderModel();
        order.setCustomerId(1L);
        order.setDate("2025-06-16");
        order.setStatus("Novo");
        order.setUserId(1L);
        long orderId = orderDAO.insert(order);
        Log.d("DaoCrudTester", "Order - getAll (após insert): " + listToString(orderDAO.getAll()));
        OrderModel loadedOrder = orderDAO.getById(orderId);
        Log.d("DaoCrudTester", "Order - getById: " + (loadedOrder != null ? loadedOrder.getStatus() : "null"));
        order.setOrderId(orderId);
        order.setStatus("Atualizado");
        orderDAO.update(order);
        Log.d("DaoCrudTester", "Order - getAll (após update): " + listToString(orderDAO.getAll()));
        orderDAO.delete(orderId);
        Log.d("DaoCrudTester", "Order - getAll (após delete): " + listToString(orderDAO.getAll()));

        // OrderItemDAO
        OrderItemDAO orderItemDAO = new OrderItemDAO(context);
        Log.d("DaoCrudTester", "OrderItem - getAll (antes): " + listToString(orderItemDAO.getAll()));
        OrderItemModel orderItem = new OrderItemModel();
        orderItem.setWineId(1L);
        orderItem.setValue(100.0);
        orderItem.setQuantity(2);
        orderItem.setOrderId(1L);
        long orderItemId = orderItemDAO.insert(orderItem);
        Log.d("DaoCrudTester", "OrderItem - getAll (após insert): " + listToString(orderItemDAO.getAll()));
        OrderItemModel loadedOrderItem = orderItemDAO.getById(orderItemId);
        Log.d("DaoCrudTester", "OrderItem - getById: " + (loadedOrderItem != null ? loadedOrderItem.getOrderItemId() : "null"));
        orderItem.setOrderItemId(orderItemId);
        orderItem.setValue(200.0);
        orderItemDAO.update(orderItem);
        Log.d("DaoCrudTester", "OrderItem - getAll (após update): " + listToString(orderItemDAO.getAll()));
        orderItemDAO.delete(orderItemId);
        Log.d("DaoCrudTester", "OrderItem - getAll (após delete): " + listToString(orderItemDAO.getAll()));

        // InventoryMovementDAO
        InventoryMovementDAO inventoryMovementDAO = new InventoryMovementDAO(context);
        Log.d("DaoCrudTester", "InventoryMovement - getAll (antes): " + listToString(inventoryMovementDAO.getAll()));
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
        Log.d("DaoCrudTester", "InventoryMovement - getAll (após insert): " + listToString(inventoryMovementDAO.getAll()));
        InventoryMovementModel loadedMovement = inventoryMovementDAO.getById(movementId);
        Log.d("DaoCrudTester", "InventoryMovement - getById: " + (loadedMovement != null ? loadedMovement.getMovementId() : "null"));
        movement.setMovementId(movementId);
        movement.setNotes("Nota atualizada");
        inventoryMovementDAO.update(movement);
        Log.d("DaoCrudTester", "InventoryMovement - getAll (após update): " + listToString(inventoryMovementDAO.getAll()));
        inventoryMovementDAO.delete(movementId);
        Log.d("DaoCrudTester", "InventoryMovement - getAll (após delete): " + listToString(inventoryMovementDAO.getAll()));

        // VisitDAO
        VisitDAO visitDAO = new VisitDAO(context);
        Log.d("DaoCrudTester", "Visit - getAll (antes): " + listToString(visitDAO.getAll()));
        VisitModel visit = new VisitModel();
        visit.setCustomerId(1L);
        visit.setDateTime("2025-06-16 10:00:00");
        visit.setLocation("Local Teste");
        visit.setWines("Vinho A, Vinho B");
        visit.setUserId(1L);
        long visitId = visitDAO.insert(visit);
        Log.d("DaoCrudTester", "Visit - getAll (após insert): " + listToString(visitDAO.getAll()));
        VisitModel loadedVisit = visitDAO.getById(visitId);
        Log.d("DaoCrudTester", "Visit - getById: " + (loadedVisit != null ? loadedVisit.getVisitId() : "null"));
        visit.setVisitId(visitId);
        visit.setLocation("Local Atualizado");
        visitDAO.update(visit);
        Log.d("DaoCrudTester", "Visit - getAll (após update): " + listToString(visitDAO.getAll()));
        visitDAO.delete(visitId);
        Log.d("DaoCrudTester", "Visit - getAll (após delete): " + listToString(visitDAO.getAll()));

        // WineImageDAO
        WineImageDAO wineImageDAO = new WineImageDAO(context);
        Log.d("DaoCrudTester", "WineImage - getAll (antes): " + listToString(wineImageDAO.getAll()));
        WineImageModel wineImage = new WineImageModel();
        wineImage.setWineId(1L);
        wineImage.setImageBase64("dGVzdGVpbWFnZQ=="); // "testeimage" em base64
        long wineImageId = wineImageDAO.insert(wineImage);
        Log.d("DaoCrudTester", "WineImage - getAll (após insert): " + listToString(wineImageDAO.getAll()));
        WineImageModel loadedWineImage = wineImageDAO.getByWineId(1L);
        Log.d("DaoCrudTester", "WineImage - getByWineId: " + (loadedWineImage != null ? loadedWineImage.toString() : "null"));
        wineImage.setImageBase64("dXBkYXRlaW1hZ2U="); // "updateimage" em base64
        wineImageDAO.update(wineImage); // Se não houver update, pode remover esta linha
        Log.d("DaoCrudTester", "WineImage - getAll (após update): " + listToString(wineImageDAO.getAll()));
        wineImageDAO.delete(1L);
        Log.d("DaoCrudTester", "WineImage - getAll (após delete): " + listToString(wineImageDAO.getAll()));
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
