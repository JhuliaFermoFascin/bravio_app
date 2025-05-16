package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.VinhoModel;

public class VinhoDAO extends AbstrataDAO{

    public  VinhoDAO(Context context){
        helper = new DPOpenHelper(context);
    }

    public long insert(final VinhoModel vinhoModel){

        long result = -1;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(VinhoModel.COLUNA_NOME_VINHO, vinhoModel.getVinho());
            values.put(VinhoModel.COLUNA_TIPO, vinhoModel.getTipo());
            values.put(VinhoModel.COLUNA_SAFRA, vinhoModel.getSafra());
            values.put(VinhoModel.COLUNA_DESCRICAO, vinhoModel.getDescricao());
            values.put(VinhoModel.COLUNA_NOTAS_DEGUSTACAO, vinhoModel.getNotas());
            values.put(VinhoModel.COLUNA_HARMONIZACOES, vinhoModel.getHarmonizacoes());
            values.put(VinhoModel.COLUNA_TEOR_ALCOOLICO, vinhoModel.getTeor_alcoolico());
            values.put(VinhoModel.COLUNA_VOLUME, vinhoModel.getVolume());
            values.put(VinhoModel.COLUNA_VALOR_UNITARIO, vinhoModel.getValor());
            values.put(VinhoModel.COLUNA_IMAGEM, vinhoModel.getImagem());

            result = db.insert(VinhoModel.TABLE_NAME, null, values);
        }
        finally {
            Close();
        }



        return result;
    }
}
