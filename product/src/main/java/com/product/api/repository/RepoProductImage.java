package com.product.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product.api.entity.ProductImage;

public interface RepoProductImage extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findByProductIdAndStatusOrderByProductImageIdAsc(Integer productId, Integer status);

    Optional<ProductImage> findByProductImageIdAndProductIdAndStatus(
            Integer productImageId, Integer productId, Integer status);
}