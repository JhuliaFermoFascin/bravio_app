package com.example.vinhedobravioapp.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FeriadosUtils {

    public static class Feriado {
        public String data;
        public String nome;

        public Feriado(String data, String nome) {
            this.data = data;
            this.nome = nome;
        }
    }

    public static List<Feriado> carregarFeriados(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("feriados_brasil.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<String, String>>() {}.getType();
        Map<String, String> feriadoMap = gson.fromJson(json, mapType);

        List<Feriado> feriados = new ArrayList<>();
        for (Map.Entry<String, String> entry : feriadoMap.entrySet()) {
            feriados.add(new Feriado(entry.getKey(), entry.getValue()));
        }

        return feriados;
    }
}
