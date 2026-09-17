package com.product.api.controller;

/*Equipo
    Irving Axel Tapia Anrubio
    Alan Vladimir Alonso Sanchez */
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;

@RestController
@RequestMapping("/category") 
public class CtrlCategory {

    @Autowired 
    SvcCategory svc;

    @GetMapping
    
    public List<Category> getCategories() {
        return svc.getCategories();
}
}