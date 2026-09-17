package com.product.api.service;

import com.product.api.repository.RepoCategory;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.entity.Category;

@Service
public class SvcCategoryImp implements SvcCategory {

    @Autowired 
    RepoCategory repo;

    SvcCategoryImp(RepoCategory repo) {
        this.repo = repo;
    }

    @Override
    public List<Category> getCategories() {
        return new ResponseEntity<>(repo.getCategories(), HttpStatus.OK);
    }

    @Override 
    public ResponseEntity<List<Category>> getActiveCategories() {
        return new ResponseEntity<>(repo.findByStatusOrderByCategory(1), HttpStatus.OK);
    }
    
}
