package com.product.api.controller;

/*Equipo
    Irving Axel Tapia Anrubio
    Alan Vladimir Alonso Sanchez */
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/category") 
public class CtrlCategory {

    final SvcCategory svc;

    CtrlCategory(SvcCategory svc) {
        this.svc = svc;
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.ok(svc.findAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<Category>> findActive(){
        return ResponseEntity.ok(svc.findActive());
    }

    @GetMapping("/{id}/childs")
   public ResponseEntity<List<Category>> findChilds(@PathVariable Integer id){
        //svc.findChilds(id);
        return ResponseEntity.ok(svc.findChilds(id));
    }
    

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody DtoCategoryIn in){
        svc.create(in);
        return ResponseEntity.ok("La categoría ha sido registrada");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody DtoCategoryIn in, 
            @PathVariable Integer id){
        svc.update(in, id);
        return ResponseEntity.ok("La categoría ha sido actualizada");
    }

    @PatchMapping("/{id}/enable")
    public ResponseEntity<String> enable(@PathVariable Integer id){
        svc.enable(id);
        return ResponseEntity.ok("La categoría ha sido activada");
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<String> disable(@PathVariable Integer id){
        svc.disable(id);
        return ResponseEntity.ok("La categoría ha sido desactivada");
    }
}