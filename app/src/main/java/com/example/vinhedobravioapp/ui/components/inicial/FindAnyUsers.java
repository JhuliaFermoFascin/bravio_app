package com.example.vinhedobravioapp.ui.components.inicial;

import android.content.Context;
import android.util.Log;
import com.example.vinhedobravioapp.database.dao.UserDAO;
import com.example.vinhedobravioapp.database.model.UserModel;
import java.util.List;

public class FindAnyUsers {
    public static void ensureDefaultUsers(Context context) {
        UserDAO userDAO = new UserDAO(context);
        List<UserModel> users = userDAO.getAll();

        Log.d("FindAnyUsers", "Antes de inserir: " + usersToString(users));
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
        Log.d("FindAnyUsers", "Depois de inserir: " + usersToString(usersAfter));
    }

    private static String usersToString(List<UserModel> users) {
        StringBuilder sb = new StringBuilder();
        for (UserModel user : users) {
            sb.append("[")
              .append(user.getUserId()).append(", ")
              .append(user.getName()).append(", ")
              .append(user.getEmail()).append("] ");
        }
        return sb.toString();
    }
}
