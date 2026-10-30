package com.product.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*Equipo
    Irving Axel Tapia Anrubio
    Alan Vladimir Alonso Sanchez */

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("categoryId")
    @Column(name = "category_id")
    private Integer categoryId;

    @JsonProperty("category")
    @Column(name = "category")
    private String category;

    @JsonProperty("tag")
    @Column(name = "tag")
    private String tag;

    @JsonProperty("parentCategoryId")
    @Column(name = "parent_category_id")
    private Integer parentCategoryId;

    @JsonProperty("status")
    @Column(name = "status")
    private Integer status;

    // Constructor vacío para SPRING
    public Category() {
    }

    // Constructor
    public Category(String category, String tag, Integer parentCategoryId) {
        this.category = category;
        this.tag = tag;
        this.parentCategoryId = parentCategoryId;
        this.status = 1;
    }

    // getters y setters
    public Integer getCategoryId() {
        return categoryId;
    }

    public String getCategory() {
        return category;
    }

    public String getTag() {
        return tag;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" + categoryId + ", \"" + category + "\", \"" + tag + "\", " +
                (parentCategoryId == null ? "null" : parentCategoryId) + ", " + status + "}";
    }
}
