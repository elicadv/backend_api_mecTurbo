package com.example.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.api.model.Form;
import com.example.api.repository.FormRepository;

import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/mecTurbo/form")
public class FormController {

    @Autowired
    private FormRepository formRepository;

    @GetMapping
    public ResponseEntity<List<Form>> listarMensagens() {
        try {
            List<Form> mensagens = formRepository.findAll();
            return new ResponseEntity<>(mensagens, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Form> buscarMensagemPorId(@PathVariable Long id) {
        try {
            Form form = formRepository.findById(id).orElseThrow();
            return new ResponseEntity<>(form, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> criarMensagem(@Valid @RequestBody Form form) {
        try {
            Form novoForm = formRepository.save(form);
            return new ResponseEntity<>(novoForm, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarMensagem(@PathVariable Long id, @Valid @RequestBody Form form) {
        try {
            Form formAtualizado = formRepository.findById(id)
                .map(existingForm -> {
                    existingForm.setNome(form.getNome());
                    existingForm.setTel(form.getTel());
                    existingForm.setEmail(form.getEmail());
                    existingForm.setMensagem(form.getMensagem());
                    return formRepository.save(existingForm);
                })
                .orElseThrow();
            return new ResponseEntity<>(formAtualizado, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{idItem}")
    public ResponseEntity<Void> removeForm(@PathVariable Long idItem) {
        try {
            formRepository.deleteById(idItem);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

