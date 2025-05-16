package com.example.vinhedobravioapp.database.model;

public class VinhoModel {

    public static String TABLE_NAME = "tb_vinho";
    public static String
    WINE_ID_COLUMN = "wine_id",
    NAME_COLUMN = "name",
    TYPE_COLUMN = "wine_type",
    VINTAGE_COLUMN = "vintage",
    DESCRIPTION_COLUM = "description",
    TASTING_NOTE_COLUMN = "tasting_note",
    FOOD_PARINGS_COLUMN = "food_parings",
    ALCOHOL_CONTENT_COLUMN = "alcohol_content",
    VOLUME_COLUMN = "volume",
    IMAGE_COLUM = "id_image";

    public static String CREATE_TABLE_VINHO =
    "CREATE TABLE "+TABLE_NAME
            +" ( "
            + WINE_ID_COLUMN + " INTEGER PRIMARY KEY NOT NULL AUTOINCREMENT, "
            +NAME_COLUMN +" TEXT NOT NULL, "
            +TYPE_COLUMN +" TEXT NOT NULL, "
            +VINTAGE_COLUMN +" INTEGER NOT NULL, "
            +DESCRIPTION_COLUM +" TEXT NOT NULL, "
            +TASTING_NOTE_COLUMN +" TEXT NOT NULL, "
            +FOOD_PARINGS_COLUMN +" TEXT NOT NULL, "
            +ALCOHOL_CONTENT_COLUMN +" DOUBLE NOT NULL, "
            +VOLUME_COLUMN +" INTEGER NOT NULL, "
            +IMAGE_COLUM +" TEXT, "
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
