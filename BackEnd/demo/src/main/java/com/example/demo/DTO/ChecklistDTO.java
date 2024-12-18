package com.example.demo.DTO;

public class ChecklistDTO {

    private String Descricao;
    private String nome;
    private boolean Equipe;

    public ChecklistDTO() {
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isEquipe() {
        return Equipe;
    }

    public void setEquipe(boolean equipe) {
        Equipe = equipe;
    }
}
