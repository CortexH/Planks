package com.example.demo.Controller;

import com.example.demo.DTO.TarefaChecklistDTO;
import com.example.demo.Entities.Checklist;
import com.example.demo.Entities.TarefaCheckList;
import com.example.demo.Entities.Usuario;
import com.example.demo.Repositories.ChecklistRepository;
import com.example.demo.Repositories.TarefaChecklistRepository;
import com.example.demo.Repositories.UsuarioRepository;
import com.example.demo.Util.JwtUtil;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/checklist/tarefa")
public class TarefaChecklistController {
    @Autowired
    TarefaChecklistRepository rep;
    @Autowired
    ChecklistRepository check_rep;
    @Autowired
    UsuarioRepository user_rep;

    @PostMapping("/new")
    public ResponseEntity<?> CriarNovaTarefa(
            @RequestBody TarefaChecklistDTO tarefa,
            @RequestHeader("Authorization") String token
    ){
        try{
            Long userId = JwtUtil.userIdPorToken(token.replace("Bearer: ", ""));
            Usuario user = user_rep.findById(userId).orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));



        }catch (Exception e){

        }



        return null;
    }


    @GetMapping("/get/{checklist_id}/{tarefa_id}")
    public ResponseEntity<?> RetornarTarefa(
            @RequestHeader("Authorization") String token,
            @PathVariable("tarefa_id") Long tarefa_id,
            @PathVariable("checklist_id") Long checklist_id
    )
    {
        try{
            Long userId = JwtUtil.userIdPorToken(token.replace("Bearer ", ""));

            Optional<Usuario> _user = user_rep.findById(userId);
            if(_user.isEmpty()) throw new NoSuchElementException("Usuário inexistente.");

            Usuario user = _user.get();
            List<Checklist> checklists = user.getChecklists();
            Optional<Checklist> _check = checklists.stream().filter(i -> i.getId().equals(checklist_id)).findFirst();

            if(_check.isEmpty()) throw new NoSuchElementException("checklist não encontrada");
            Checklist check = _check.get();

            List<TarefaCheckList> listaTarefas = check.getTarefaCheckList();

            Optional<TarefaCheckList> _tarefa = listaTarefas.stream().filter(n -> n.getId().equals(tarefa_id)).findFirst();
            if(_tarefa.isEmpty()) throw new NoSuchElementException("Tarefa não encontrada");

            TarefaCheckList tarefa = _tarefa.get();

            return ResponseEntity.ok(tarefa);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
