package com.product.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*Equipo
    Irving Axel Tapia Anrubio
    Alan Vladimir Alonso Sanchez */

@Entity 
@Table(name="category")
public class Category {
    @Id 
    private Integer category_id;
    private String category;
    private String tag;
    private Integer parent_category_id;
    private Integer status;

    //Constructor vacio para SPRING
    public Category(){
    }

    // Constructor
    public Category(String category, String tag, Integer parentCategoryId) {
        this.category = category;
        this.tag = tag;
        this.parent_category_id = parentCategoryId;
        this.status = 1;
    }

    // getters y setters
    public Integer getCategory_id() {
        return category_id;
    }

    public String getCategory() {
        return category;
    }

    public String getTag() {
        return tag;
    }

    public Integer getParentCategoryId() {
        return parent_category_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parent_category_id = parentCategoryId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" + category_id + ", \"" + category + "\", \"" + tag + "\", " +
                (parent_category_id == null ? "null" : parent_category_id) + ", " + status + "}";
    }
}
