package com.product.api.dto.out;

public class DtoProductOut {
    private Integer product_id;
    private String gtin;
    private String product;
    private String description;
    private Float price;
    private Integer stock;
    private Integer category_id;
    private Integer status;

    public DtoProductOut(Integer product_id, String gtin, String product, String description,
            Float price, Integer stock, Integer category_id, Integer status) {
        this.product_id = product_id;
        this.gtin = gtin;
        this.product = product;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category_id = category_id;
        this.status = status;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public String getGtin() {
        return gtin;
    }

    public String getProduct() {
        return product;
    }

    public String getDescription() {
        return description;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public Integer getStatus() {
        return status;
    }
}
