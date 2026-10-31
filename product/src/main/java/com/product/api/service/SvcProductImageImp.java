package com.product.api.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.dto.out.DtoProductImageOut;
import com.product.api.entity.ProductImage;
import com.product.api.repository.RepoProduct;
import com.product.api.repository.RepoProductImage;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

@Service
public class SvcProductImageImp implements SvcProductImage {
	private final RepoProductImage imageRepo;
	private final RepoProduct productRepo;

	public SvcProductImageImp(RepoProductImage imageRepo, RepoProduct productRepo) {
		this.imageRepo = imageRepo;
		this.productRepo = productRepo;
	}

	@Override
	public ResponseEntity<List<DtoProductImageOut>> getProductImages(Integer productId) {
		try {
			validateProduct(productId);
			List<DtoProductImageOut> images = imageRepo
					.findByProductIdAndStatusOrderByProductImageIdAsc(productId, 1)
					.stream()
					.map(image -> toDto(image))
					.toList();
			return ResponseEntity.ok(images);
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	@Override
	public ResponseEntity<String> createProductImage(Integer productId, DtoProductImageIn in) {
		try {
			validateProduct(productId);
			if (!productId.equals(in.getProduct_id())) {
				throw new ApiException(HttpStatus.BAD_REQUEST, "El product_id no coincide con el id de la ruta");
			}

			ProductImage image = new ProductImage();
			image.setProductId(productId);
			image.setImage(in.getImage());
			image.setStatus(1);
			imageRepo.save(image);
			return new ResponseEntity<>("La imagen ha sido registrada", HttpStatus.CREATED);
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	@Override
	public ResponseEntity<String> deleteProductImage(Integer productId, Integer productImageId) {
		try {
			validateProduct(productId);
			ProductImage image = imageRepo
					.findByProductImageIdAndProductIdAndStatus(productImageId, productId, 1)
					.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "El id de la imagen no existe"));
			image.setStatus(0);
			imageRepo.save(image);
			return ResponseEntity.ok("La imagen ha sido eliminada");
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	private void validateProduct(Integer productId) {
		if (!productRepo.existsById(productId)) {
			throw new ApiException(HttpStatus.NOT_FOUND, "El id del producto no existe");
		}
	}

	private DtoProductImageOut toDto(ProductImage image) {
		return new DtoProductImageOut(image.getProductImageId(), image.getProductId(), image.getImage());
	}
}