package br.edu.fateczl.crud.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Jogador {


    private int id;
    private String nome;
    private LocalDate dataNasc;
    private float altura;
    private float peso;
    private Time time;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }



    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = LocalDate.parse(dataNasc, formatter);
    }

    public String getDataNascString() {
        return dataNasc.format(formatter);
    }

    public String getDataNascAsString() {
        return dataNasc.format(formatter);
    }
    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getPeso() {
        return peso;
    }
    public void setPeso(float peso) {
        this.peso = peso;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return id + " - " + nome + " - " + dataNasc + " - " + altura + " - " +
                peso + " - " + (time != null ? time.getNome() : "Sem time");
    }

    }

