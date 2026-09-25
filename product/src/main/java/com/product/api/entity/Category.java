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
    @JsonProperty("category_id")
    @Column(name = "category_id")
    private Integer category_id;

    @JsonProperty("category")
    @Column(name = "category")
    private String category;

    @JsonProperty("tag")
    @Column(name = "tag")
    private String tag;

    @JsonProperty("parent_category_id")
    @Column(name = "parent_category_id")
    private Integer parent_category_id;

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
