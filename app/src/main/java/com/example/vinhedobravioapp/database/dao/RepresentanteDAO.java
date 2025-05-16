package com.example.vinhedobravioapp.database.dao;

import android.content.ContentValues;
import android.content.Context;

import com.example.vinhedobravioapp.database.DPOpenHelper;
import com.example.vinhedobravioapp.database.model.RepresentanteModel;
public class RepresentanteDAO extends AbstrataDAO{

    public RepresentanteDAO(Context context){
        helper = new DPOpenHelper(context);
    }


    public long insert(final RepresentanteModel representanteModel){

        long result = -1;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(RepresentanteModel.COLUNA_NOME_REPRESENTANTE, representanteModel.getRepresentante());
            values.put(RepresentanteModel.COLUNA_TELEFONE, representanteModel.getTelefone());
            values.put(RepresentanteModel.COLUNA_EMAIL, representanteModel.getEmail());
            values.put(RepresentanteModel.COLUNA_REGIAO_ATUACAO, representanteModel.getRegiao_atuacao());
            values.put(RepresentanteModel.COLUNA_STATUS, representanteModel.getStatus());


            result = db.insert(RepresentanteModel.TABLE_NAME, null, values);
        }
        finally {
            Close();
        }
        return result;
    }
}
