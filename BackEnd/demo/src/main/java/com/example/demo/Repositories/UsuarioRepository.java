package com.example.demo.Repositories;

import com.example.demo.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Boolean existsByemail(String email);
    Boolean existsBycpf(String email);
    Usuario findByemail(String email);
    Usuario findByEmailAndSenha(String email, String senha);
}
