package com.example.vinhedobravioapp.domain.model;

public class Agenda {
    public String titulo;
    public String objetivo;
    public String data;
    public String horaInicio;
    public String horaFim;
    public String endereco;

    public Agenda(String titulo, String objetivo, String data, String horaInicio, String horaFim, String endereco) {
        this.titulo = titulo;
        this.objetivo = objetivo;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.endereco = endereco;
    }
}
