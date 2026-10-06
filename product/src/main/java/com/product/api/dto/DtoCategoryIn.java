package com.product.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// Esta libreria es para los @NotNull y poder asegurar que los valores no sean nulos
import jakarta.validation.constraints.*;

public class DtoCategoryIn {
    @JsonProperty("category")
    @NotNull (message = "La categoria es obligatoria")
    private String category;

    @JsonProperty("tag")
    @NotNull (message = "El tag es obligatorio")
    private String tag;

    @JsonProperty("parentCategoryId")
    private Integer parentCategoryId;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }


}
