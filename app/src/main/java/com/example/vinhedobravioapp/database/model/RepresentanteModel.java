package com.example.vinhedobravioapp.database.model;

public class RepresentanteModel {

    public static String TABLE_NAME = "tb_representante";

    public static String
    COLUNA_ID_REPRESENTANTE = "_id",
    COLUNA_NOME_REPRESENTANTE = "nome_completo",
    COLUNA_TELEFONE = "telefone",
    COLUNA_EMAIL = "email",
    COLUNA_REGIAO_ATUACAO = "regiao_atuacao",
    COLUNA_STATUS= "status";

    public static String CREATE_TABLE_VINHO =
    "CREATE TABLE "+TABLE_NAME
            +" ( "
            +COLUNA_ID_REPRESENTANTE + " INTEGER PRIMARY KEY NOT NULL AUTOINCREMENT, "
            +COLUNA_NOME_REPRESENTANTE +" TEXT NOT NULL, "
            +COLUNA_TELEFONE +" TEXT NOT NULL, "
            +COLUNA_EMAIL +" TEXT NOT NULL, "
            +COLUNA_REGIAO_ATUACAO +" TEXT NOT NULL, "
            +COLUNA_STATUS +" TEXT NOT NULL, "
            + " );";

    public static String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE_NAME;

    private long id;
    private String representante;
    private String telefone;
    private Integer email;
    private String regiao_atuacao;
    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Integer getEmail() {
        return email;
    }

    public void setEmail(Integer email) {
        this.email = email;
    }

    public String getRegiao_atuacao() {
        return regiao_atuacao;
    }

    public void setRegiao_atuacao(String regiao_atuacao) {
        this.regiao_atuacao = regiao_atuacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
