package com.example.demo.Repositories;

import com.example.demo.Entities.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
    Optional<List<Checklist>> findAllByUsuarioId(Long id);
}
