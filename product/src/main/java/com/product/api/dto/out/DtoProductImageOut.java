package com.product.api.dto.out;

public class DtoProductImageOut {
    private Integer product_image_id;
    private Integer product_id;
    private String image;

    public DtoProductImageOut(Integer product_image_id, Integer product_id, String image) {
        this.product_image_id = product_image_id;
        this.product_id = product_id;
        this.image = image;
    }

    public Integer getProduct_image_id() {
        return product_image_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public String getImage() {
        return image;
    }
}