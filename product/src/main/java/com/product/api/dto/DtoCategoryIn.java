package com.product.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class DtoCategoryIn {
    @JsonProperty("category")
    @NotBlank(message = "La categoría es obligatoria")
    private String category;

    @JsonProperty("tag")
    @NotBlank(message = "El tag es obligatorio")
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
