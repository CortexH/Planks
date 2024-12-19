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
import org.springframework.http.HttpStatus;
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

    private static class ApagarComSucesso{
        public String response;
        public String code;
    }

    @PatchMapping("/")
    public ResponseEntity<?> CheckTarefa(
            @RequestHeader("Authorization") String token,
            @RequestParam Long idTarefa,
            @RequestParam Long idChecklist,
            @RequestParam boolean onCheck
    ){
        Long userId = JwtUtil.userIdPorToken(token.replace("Bearer ", ""));

        Usuario user = user_rep.findById(userId).orElseThrow(() -> new NoSuchElementException("Usuário inexistente"));

        Checklist check = user.getChecklists().stream().filter(n -> n.getId().equals(idChecklist))
                .findFirst().orElseThrow(() -> new NoSuchElementException("Checklist não foi encontrada"));

        TarefaCheckList tarefa = check.getTarefaCheckList().stream().filter(n -> n.getId().equals(idTarefa))
                .findFirst().orElseThrow(() -> new NoSuchElementException("Checklist não foi encontrada"));

        if(tarefa.isChecked() == onCheck) return ResponseEntity.ok(tarefa);

        tarefa.setChecked(onCheck);
        TarefaCheckList savedTarefa = rep.save(tarefa);
        return ResponseEntity.ok(savedTarefa);
    }

    @PostMapping("/new")
    public ResponseEntity<?> CriarNovaTarefa(
            @RequestBody TarefaChecklistDTO tarefa,
            @RequestHeader("Authorization") String token
    ){
        Long userId = JwtUtil.userIdPorToken(token.replace("Bearer ", ""));
        Usuario user = user_rep.findById(userId).orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));

        Checklist checklist = user.getChecklists().stream().filter(n -> n.getId().equals(tarefa.getId_checklist()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Checklist não encontrada"));

        TarefaCheckList checkTarefa = new TarefaCheckList();

        checkTarefa.setChecklist(checklist);
        checkTarefa.setNome(tarefa.getNome());
        checkTarefa.setRestart(tarefa.getTempo_Reinicia());
        checkTarefa.setTarefa_desc(tarefa.getDescricao());

        TarefaCheckList tarefaSalva = rep.save(checkTarefa);

        return ResponseEntity.ok(tarefaSalva);
    }

    @GetMapping("/get/{checklist_id}/{tarefa_id}")
    public ResponseEntity<?> RetornarTarefa(
            @RequestHeader("Authorization") String token,
            @PathVariable("tarefa_id") Long tarefa_id,
            @PathVariable("checklist_id") Long checklist_id)
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
    @DeleteMapping("/apagar/{id1}/{id2}")
    public ResponseEntity<?> ApagarTarefa(
            @RequestHeader("Authorization") String token,
            @PathVariable("id1") Long id_Checklist,
            @PathVariable("id2") Long id_Tarefa
    ) {

        Long userID = JwtUtil.userIdPorToken(token.replace("Bearer ", ""));

        Usuario user = user_rep.findById(userID).orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));

        Checklist check = user.getChecklists().stream().filter((n) -> n.getId().equals(id_Checklist))
                .findFirst().orElseThrow(() -> new NoSuchElementException("Checklist não encontrada"));

        TarefaCheckList tarefa = check.getTarefaCheckList().stream().filter((n) -> n.getId().equals(id_Tarefa))
                .findFirst().orElseThrow(() -> new NoSuchElementException("Tarefa não encontrada"));

        check.getTarefaCheckList().remove(tarefa);
        rep.delete(tarefa);

        ApagarComSucesso apg = new ApagarComSucesso();
        apg.code = "200";
        apg.response = "Tarefa apagada com sucesso!";

        return ResponseEntity.status(HttpStatus.OK).body(apg);
    }
}
