package com.example.vinhedobravioapp.loginManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.ui.components.inicial.DashboardAdmActivity;
import com.example.vinhedobravioapp.ui.components.inicial.MenuActivity;
import com.example.vinhedobravioapp.ui.components.inicial.PainelRepresentanteActivity;
import com.example.vinhedobravioapp.ui.components.utils.LoginStatus;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class LoginManager {

    private static LoginManager instance;
    private LoginStatus loginStatus;

    private LoginManager() { }

    public static synchronized LoginManager getInstance() {
        if (instance == null) {
            instance = new LoginManager();
        }
        return instance;
    }

    public void loadLoginStatus(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getString(R.string.preferencia_login), Context.MODE_PRIVATE
        );
        String json = prefs.getString(context.getString(R.string.login_status), null);
        if (json != null) {
            loginStatus = new Gson().fromJson(json, LoginStatus.class);
            Log.d("LoginManager", "Status carregado: " + loginStatus.toString());
        }
    }

    public void saveLoginStatus(Context context) {
        if (loginStatus == null) return;

        SharedPreferences prefs = context.getSharedPreferences(
                context.getString(R.string.preferencia_login), Context.MODE_PRIVATE
        );
        prefs.edit()
                .putString(context.getString(R.string.login_status), new Gson().toJson(loginStatus))
                .apply();
        Log.d("LoginManager", "Status salvo.");
    }

    public LoginStatus getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(LoginStatus status) {
        this.loginStatus = status;
    }

    public Intent getNextActivity(Context context) {
        if (loginStatus == null) {
            loadLoginStatus(context);
        }

        if (loginStatus != null) {
            if (!loginStatus.isManterLogado()) {
                return new Intent(context, MenuActivity.class);
            }

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date dataLogin = sdf.parse(loginStatus.getDataLogin());
                long dias = TimeUnit.MILLISECONDS.toDays(new Date().getTime() - dataLogin.getTime());

                if (dias <= 7) {
                    return new Intent(context,
                            loginStatus.isAdmin() ? DashboardAdmActivity.class : PainelRepresentanteActivity.class
                    );
                }

            } catch (Exception e) {
                Log.e("LoginManager", "Erro ao processar data", e);
            }
        }

        return new Intent(context, MenuActivity.class);
    }
    public void clearLoginStatus(Context context) {
        // Limpa da memória
        loginStatus = null;

        // Limpa do SharedPreferences
        SharedPreferences prefs = context.getSharedPreferences(
                context.getString(R.string.preferencia_login), Context.MODE_PRIVATE
        );
        prefs.edit().remove(context.getString(R.string.login_status)).apply();

        Log.d("LoginManager", "Login status limpo com sucesso.");
    }

}
