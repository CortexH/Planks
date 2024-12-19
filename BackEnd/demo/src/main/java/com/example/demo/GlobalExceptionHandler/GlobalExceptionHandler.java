package com.example.demo.GlobalExceptionHandler;

import jakarta.persistence.EntityExistsException;
import org.apache.coyote.BadRequestException;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static class ErroGlobal{
        public String erro;
        public String status;
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<?> EntidadeExistente(EntityExistsException ex){

        ErroGlobal erroGlobal = new ErroGlobal();
        erroGlobal.erro = ex.getMessage();
        erroGlobal.status = "400";

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroGlobal);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> BadRequest(IllegalArgumentException ex){

        ErroGlobal erroGlobal = new ErroGlobal();
        erroGlobal.erro = ex.getMessage();
        erroGlobal.status = "400";

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroGlobal);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> ElementoNaoEncontrado(NoSuchElementException ex){

        ErroGlobal erroGlobal = new ErroGlobal();
        erroGlobal.erro = ex.getMessage();
        erroGlobal.status = "404";

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroGlobal);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<?> ChamadaNaoSuportada(UnsupportedOperationException ex){

        ErroGlobal erroGlobal = new ErroGlobal();
        erroGlobal.erro = ex.getMessage();
        erroGlobal.status = "400";

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroGlobal);
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<?> ChamadaNaoSuportada(InternalException ex){

        ErroGlobal erroGlobal = new ErroGlobal();
        erroGlobal.erro = ex.getMessage();
        erroGlobal.status = "500";

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erroGlobal);
    }


}
