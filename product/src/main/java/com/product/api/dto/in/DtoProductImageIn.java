package com.product.api.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DtoProductImageIn {
    @NotNull(message = "El product_id es obligatorio")
    private Integer product_id;

    @NotBlank(message = "La imagen es obligatoria")
    private String image;

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}