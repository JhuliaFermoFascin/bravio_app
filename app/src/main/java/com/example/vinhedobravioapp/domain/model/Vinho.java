package com.example.vinhedobravioapp.domain.model;

public class Vinho {
    public String nome;
    public int pedidos;
    public String comparacao;
    public boolean subiu;

    public Vinho(String nome, int pedidos, String comparacao, boolean subiu) {
        this.nome = nome;
        this.pedidos = pedidos;
        this.comparacao = comparacao;
        this.subiu = subiu;
    }
}