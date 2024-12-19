package com.example.demo.Repositories;

import com.example.demo.Entities.TarefaCheckList;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface TarefaChecklistRepository extends JpaRepository<TarefaCheckList, Long> {
    Optional<List<TarefaCheckList>> findAllByChecklistId(Long id);
    Optional<TarefaCheckList> findByChecklistId(Long id);
}
