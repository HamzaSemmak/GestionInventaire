package com.example.gestioninventairesystem.Mapper.Impl;


import com.example.gestioninventairesystem.Dto.ProductDto;
import com.example.gestioninventairesystem.Mapper.InternalMapper;
import com.example.gestioninventairesystem.Model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper implements InternalMapper<ProductDto, Product> {

    public ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductDto(
                product.getName(),
                product.getCategory(),
                product.getStock(),
                product.getPrice()
        );
    }
    public Product toModel(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }
        return new Product(
                0,
                productDto.getName(),
                productDto.getCategory(),
                productDto.getStock(),
                productDto.getPrice(),
                ""
        );
    }

    public List<ProductDto> toDtos(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return null;
        }
        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Product> toModels(List<ProductDto> productDtos) {
        if (productDtos == null || productDtos.isEmpty()) {
            return null;
        }
        return productDtos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

}
