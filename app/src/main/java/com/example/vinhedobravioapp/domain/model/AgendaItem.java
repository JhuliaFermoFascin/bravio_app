package com.example.vinhedobravioapp.domain.model;

public class AgendaItem {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_EVENT = 1;
    public static final int TYPE_NO_EVENT = 2;
    public static final int TYPE_MONTH_HEADER = 3;

    public int type;
    public String headerTitle;
    public Agenda agenda;

    public AgendaItem(int type, String headerTitle, Agenda agenda) {
        this.type = type;
        this.headerTitle = headerTitle;
        this.agenda = agenda;
    }
}
