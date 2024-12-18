package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String display_name;
    private String email;
    private String senha;
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "id_equipe")
    private Equipe Equipe;

    @OneToMany(mappedBy = "usuario", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Checklist> checklists;

    @Column(name = "nivel_Permissao_Equipe")
    private int nivelPermissaoEquipe;

    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Checklist> getChecklists() {
        return checklists;
    }

    public void setChecklists(List<Checklist> checklists) {
        this.checklists = checklists;
    }

    public int getNivelPermissaoEquipe() {
        return nivelPermissaoEquipe;
    }

    public void setNivelPermissaoEquipe(int nivelPermissaoEquipe) {
        this.nivelPermissaoEquipe = nivelPermissaoEquipe;
    }
}
