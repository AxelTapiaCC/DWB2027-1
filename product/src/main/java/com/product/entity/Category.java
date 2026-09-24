package com.product.entity;

/*Equipo
    Irving Axel Tapia Anrubio
    Alan Vladimir Alonso Sanchez */

public class Category {
    private Integer category_id;
    private String category;
    private String tag;
    private Integer parentCategoryId;
    private Integer status;

    //Constructor
    public Category(String category, String tag, Integer parentCategoryId) {
        this.category = category;
        this.tag = tag;
        this.parentCategoryId = parentCategoryId;
        this.status = 1; 
    }

    //getters y setters
    public Integer getCategory_id() { return category_id; }
    public String getCategory() { return category; }
    public String getTag() { return tag; }
    public Integer getParentCategoryId() { return parentCategoryId; }
    public Integer getStatus() { return status; }
    
    public void setCategory_id(Integer category_id) { this.category_id = category_id; }
    public void setCategory(String category) { this.category = category; }

    public void setTag(String tag) { this.tag = tag; }

    public void setParentCategoryId(Integer parentCategoryId) { this.parentCategoryId = parentCategoryId; }

    public void setStatus(Integer status) { this.status = status; }

    @Override
    public String toString() {
        return "{" + category_id + ", \"" + category + "\", \"" + tag + "\", " + 
               (parentCategoryId == null ? "null" : parentCategoryId) + ", " + status + "}";
    }
}

