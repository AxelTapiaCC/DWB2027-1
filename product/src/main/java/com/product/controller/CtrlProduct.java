package com.product.controller;

/*Equipo
    Irving Axel Tapia Anrubio
    Alan Vladimir Alonso Sanchez */

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.entity.Category;

@RestController
@RequestMapping("/category") 
public class CtrlProduct {

    @GetMapping
    public List<Category> getCategories() {
        
        List<Category> categories = new ArrayList<>();
        
        Category ropa = new Category("Ropa", "RP", null);
        ropa.setCategory_id(1);
        
        Category calzado = new Category("Calzado", "CLZD", 1);
        calzado.setCategory_id(2);
        
        categories.add(ropa);
        categories.add(calzado);
        
        return categories; 
    }
}