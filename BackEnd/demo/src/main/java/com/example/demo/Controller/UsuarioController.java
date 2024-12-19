package com.example.demo.Controller;

import com.example.demo.Entities.Usuario;
import com.example.demo.DTO.UsuarioDTO;
import com.example.demo.Repositories.UsuarioRepository;
import com.example.demo.Util.ApiResponse;
import com.example.demo.Util.JwtUtil;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UsuarioController {
    @Autowired
    private UsuarioRepository rep;

    private void validarUsuario(UsuarioDTO user){
        if(user.getCpf() == null || user.getCpf().isBlank()){
            throw new IllegalArgumentException("Insira o CPF do usuário");
        };
        if(user.getEmail() == null || user.getEmail().isBlank()){
            throw new IllegalArgumentException("Insira o Email do usuário");
        };
        if(user.getDisplay_name() == null || user.getDisplay_name().isBlank()){
            throw new IllegalArgumentException("Insira o display Name do usuário");
        };
        if(user.getSenha() == null || user.getSenha().isBlank()){
            throw new IllegalArgumentException("Insira a Senha do usuário");
        };

        if(rep.existsByemail(user.getEmail())){
            throw new EntityExistsException("Usuário com email especificado já existe.");
        }
        if(rep.existsBycpf(user.getCpf())){
            throw new EntityExistsException("Usuário com cpf especificado já existe.");
        }
    }

    public static class LoginClass{
        public String email;
        public String senha;
    }

    @PostMapping("/new")
    public ResponseEntity<?> criarUsuario(@RequestBody UsuarioDTO usuario){
        validarUsuario(usuario);

        Usuario user = new Usuario();
        user.setCpf(usuario.getCpf());
        user.setEmail(usuario.getEmail());
        user.setSenha(usuario.getSenha());
        user.setDisplay_name(usuario.getDisplay_name());
        Usuario userSalvo = rep.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(userSalvo);
    }

    /*
    ----------- Remover ao terminar os debugs -----------------
    @GetMapping("/get/")
    public ResponseEntity<?> RetornarUsuárioPorEmail(@RequestParam String email){
        if(email == null){
            throw new IllegalArgumentException("Insira o email ou cpf do usuário");
        }

        if(!rep.existsByemail(email)){throw new NoSuchElementException("Não há nenhum usuário com esse email");
        }
        return ResponseEntity.status(HttpStatus.OK).body(rep.findByemail(email));
    }

    @GetMapping("/get")
    public ResponseEntity<?> RetornarTodosOsUsuários(){
        return ResponseEntity.ok(rep.findAll());
    }
    */

    @PostMapping("/login")
    public ResponseEntity<?> LoginUsuario(@RequestBody LoginClass login, HttpServletResponse response){
        Usuario user = rep.findByEmailAndSenha(login.email, login.senha);
        if(user == null) throw new IllegalArgumentException("Email ou senha incorretos");
        String token = JwtUtil.generateToken(user.getEmail(), user.getId());

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        ApiResponse.RecebeToken rT = new ApiResponse.RecebeToken();

        rT.code = "200";
        rT.token = token;

        return ResponseEntity.ok().body(rT);
    }

 }
