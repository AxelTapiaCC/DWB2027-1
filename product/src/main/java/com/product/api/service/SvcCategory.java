package com.product.api.service;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface SvcCategory {

    //public ResponseEntity<List<Category>> getCategories();

    public List<Category> findAll();
    public List<Category> findActive();
    public List<Category> findChilds(Integer id);

    public void create(DtoCategoryIn in);
    public void update(DtoCategoryIn in, Integer id);
    public void enable(Integer id);
    public void disable(Integer id);
    
}
