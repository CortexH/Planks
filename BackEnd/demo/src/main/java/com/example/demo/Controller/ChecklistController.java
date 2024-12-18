package com.example.demo.Controller;

import com.example.demo.Entities.Checklist;
import com.example.demo.Entities.TarefaCheckList;
import com.example.demo.Entities.Usuario;
import com.example.demo.DTO.ChecklistDTO;
import com.example.demo.Repositories.ChecklistRepository;
import com.example.demo.Repositories.TarefaChecklistRepository;
import com.example.demo.Repositories.UsuarioRepository;
import com.example.demo.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/checklist")
public class ChecklistController {

    @Autowired
    private ChecklistRepository rep;
    @Autowired
    private UsuarioRepository user_rep;

    public void validarChecklist(ChecklistDTO check){
        if(check.getNome() == null || check.getNome().isBlank()){
            throw new IllegalArgumentException("Insira o nome da Checklist");
        }
        if(check.getDescricao() == null || check.getDescricao().isBlank()){
            throw new IllegalArgumentException("Insira a descrição da Checklist");
        }
    }

    @PostMapping("/new")
    public ResponseEntity<?> criarChecklist(
            @RequestBody ChecklistDTO check,
            @RequestHeader("Authorization") String token
    ){

        try{
            Long userId = JwtUtil.userIdPorToken(token.replace("Bearer ", ""));

            if(check.isEquipe()){
                throw new UnsupportedOperationException("Isso ainda não está implementado!");
            }
            validarChecklist(check);

            Checklist userCheck = new Checklist();
            userCheck.setDescricao(check.getDescricao());
            userCheck.setNome(check.getNome());

            Optional<Usuario> user = user_rep.findById(userId);

            if(user.isEmpty()){
                throw new NoSuchElementException("Usuário não encontrado");
            }
            userCheck.setUsuario(user.get());

            Checklist savedChecklist = rep.save(userCheck);

            return ResponseEntity.ok(savedChecklist);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> RetornarChecklistsPorIdUsuario(@PathVariable Long id){
        if(!user_rep.existsById(id)){throw new NoSuchElementException("Usuário inválido");}

        Optional<List<Checklist>> ListaChecklists = rep.findAllByUsuarioId(id);
        if(ListaChecklists.isEmpty()){
            throw new NoSuchElementException("O usuário não tem nenhuma checklist");
        }

        return ResponseEntity.status(HttpStatus.OK).body(ListaChecklists.get());
    }

}
