package com.example.demo.Entities;

import com.example.demo.Entities.Checklist;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "tarefa_Checklist")
public class TarefaCheckList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarefa_Check")
    private Long id;

    @Column(name = "nome_Tarefa")
    private String nome;

    @Column(name = "descricao_da_Tarefa")
    private String tarefa_desc;

    @Column(name = "Checked")
    private boolean Checked;

    @ManyToOne
    @JoinColumn(name = "tarefa_id")
    @JsonBackReference
    private Checklist checklist;

    @Column(name = "tempo_Reinicia")
    private LocalTime Restart;

    public TarefaCheckList() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTarefa_desc() {
        return tarefa_desc;
    }

    public void setTarefa_desc(String tarefa_desc) {
        this.tarefa_desc = tarefa_desc;
    }

    public boolean isChecked() {
        return Checked;
    }

    public void setChecked(boolean checked) {
        Checked = checked;
    }

    public Checklist getChecklist() {
        return checklist;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    public LocalTime getRestart() {
        return Restart;
    }

    public void setRestart(LocalTime restart) {
        Restart = restart;
    }
}
