package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public class TarefaChecklistDTO {

    private Long id_checklist;

    private String nome;
    private String descricao;
    private boolean check;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime Tempo_Reinicia;

    public Long getId_checklist() {
        return id_checklist;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isCheck() {
        return check;
    }

    public LocalTime getTempo_Reinicia() {
        return Tempo_Reinicia;
    }
}
