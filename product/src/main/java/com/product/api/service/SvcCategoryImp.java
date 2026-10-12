package com.product.api.service;

import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

import java.util.List;


import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.entity.Category;
import com.product.api.dto.DtoCategoryIn;

@Service
public class SvcCategoryImp implements SvcCategory {

    RepoCategory repo;

    SvcCategoryImp(RepoCategory repo) {
        this.repo = repo;
    }

    /* 
   @Override
    public ResponseEntity<List<Category>> getCategories() {
        try {
            return new ResponseEntity<>(repo.getCategories(), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar las categorías");
        }
    }
    */

    @Override
    public List<Category> findAll() {
        try {
            return repo.getAll();
        } catch(DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public List<Category> findActive() {
        try {
            return repo.findActive();
        } catch(DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public List<Category> findChilds(Integer id) {
        try{
            return repo.findChilds(id);
        } catch(DataAccessException e) {
            throw new DBAccessException(e);
        }
    }



    @Override
    public void create(DtoCategoryIn in) {
        try{
            repo.create(in.getCategory(), in.getTag(), in.getParentCategoryId());
        } catch(DataAccessException e) {
            validarErroresCategoria(e);
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al crear la categoría");
        }
    }

    @Override
    public void update(DtoCategoryIn in, Integer id) {
        if (!repo.existsById(id)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "El id de la categoría no existe");
        }
        try {
            repo.update(id, in.getCategory(), in.getTag(), in.getParentCategoryId());
        }catch (DataAccessException e) {
            validarErroresCategoria(e);
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al actualizar la categoría");
        }
    }

    private String mensajeBD(DataAccessException e) {
        Throwable root = e.getRootCause();
        String msg = (root != null) ? root.getMessage() : e.getLocalizedMessage();
        return (msg != null) ? msg : "";
    }

    private void validarErroresCategoria(DataAccessException e) {
        String msg = mensajeBD(e);

        if (msg.contains("ux_category"))
            throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está registrado");
        if (msg.contains("ux_tag"))
            throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está registrado");
        if (msg.contains("La categoría padre no existe o no está activa"))
            throw new ApiException(HttpStatus.BAD_REQUEST, "La categoría padre no existe o no está activa");
        if (msg.contains("Una categoría no puede ser padre de sí misma"))
            throw new ApiException(HttpStatus.BAD_REQUEST, "Una categoría no puede ser padre de sí misma");
    }

    @Override
    public void enable(Integer id) {
        try {
            repo.enable(id);
        }catch (DataAccessException e) {
            if(repo.findById(id).isEmpty()){
                throw new ApiException(HttpStatus.NOT_FOUND, "El id de la categoría no existe");
            }

            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al activar la categoría");
        }
    }

    @Override
    public void disable(Integer id) {
        try {
            repo.disable(id);
        }catch (DataAccessException e) {
            if(repo.findById(id).isEmpty()){
                throw new ApiException(HttpStatus.NOT_FOUND, "El id de la categoría no existe");
            }
            String msg = mensajeBD(e);

            if (msg.contains("No es posible eliminar una categoría si tiene categorías hijas")) {
                throw new ApiException(HttpStatus.CONFLICT, "La categoría tiene subcategorías activas");
            }
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al desactivar la categoría");

        }
    }


}
