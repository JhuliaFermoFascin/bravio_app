package com.example.vinhedobravioapp.database.model;

public class VinhoModel {

    public static String TABLE_NAME = "tb_vinho";
    public static String
    COLUNA_ID_VINHO = "_id",
    COLUNA_NOME_VINHO = "nome",
    COLUNA_TIPO = "tipo",
    COLUNA_SAFRA = "safra",
    COLUNA_DESCRICAO = "descricao",
    COLUNA_NOTAS_DEGUSTACAO = "notas_degustacao",
    COLUNA_HARMONIZACOES = "harmonizacoes",
    COLUNA_TEOR_ALCOOLICO = "teor_alcoolico",
    COLUNA_VOLUME = "volum_ml",
    COLUNA_VALOR_UNITARIO = "valor_unitario",
    COLUNA_IMAGEM = "imagem_url";

    public static String CREATE_TABLE_VINHO =
    "CREATE TABLE "+TABLE_NAME
            +" ( "
            + COLUNA_ID_VINHO + " INTEGER PRIMARY KEY NOT NULL AUTOINCREMENT, "
            +COLUNA_NOME_VINHO +" TEXT NOT NULL, "
            +COLUNA_TIPO +" TEXT NOT NULL, "
            +COLUNA_SAFRA +" INTEGER NOT NULL, "
            +COLUNA_DESCRICAO +" TEXT NOT NULL, "
            +COLUNA_NOTAS_DEGUSTACAO +" TEXT NOT NULL, "
            +COLUNA_HARMONIZACOES +" TEXT NOT NULL, "
            +COLUNA_TEOR_ALCOOLICO +" DOUBLE NOT NULL, "
            +COLUNA_VOLUME +" INTEGER NOT NULL, "
            +COLUNA_VALOR_UNITARIO +" DOUBLE NOT NULL, "
            +COLUNA_IMAGEM +" TEXT, "
            + " );";

    public static String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE_NAME;

    private long id;
    private String vinho;
    private String tipo;
    private Integer safra;
    private String descricao;
    private String notas;
    private String harmonizacoes;
    private Double teor_alcoolico;
    private Integer volume;
    private Double valor;
    private String imagem;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVinho() {
        return vinho;
    }

    public void setVinho(String vinho) {
        this.vinho = vinho;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getSafra() {
        return safra;
    }

    public void setSafra(Integer safra) {
        this.safra = safra;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getHarmonizacoes() {
        return harmonizacoes;
    }

    public void setHarmonizacoes(String harmonizacoes) {
        this.harmonizacoes = harmonizacoes;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Double getTeor_alcoolico() {
        return teor_alcoolico;
    }

    public void setTeor_alcoolico(Double teor_alcoolico) {
        this.teor_alcoolico = teor_alcoolico;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
