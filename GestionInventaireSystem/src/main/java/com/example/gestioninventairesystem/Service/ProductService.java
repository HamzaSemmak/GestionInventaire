package com.example.gestioninventairesystem.Service;

import com.example.gestioninventairesystem.Dto.ProductDto;
import com.example.gestioninventairesystem.Logger.Logger;
import com.example.gestioninventairesystem.Mapper.Impl.ProductMapper;
import com.example.gestioninventairesystem.Model.Product;
import com.example.gestioninventairesystem.Repository.Impl.ProductRepository;

import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductService() {
        this.productRepository = new ProductRepository();
        this.productMapper = new ProductMapper();
    }

    public List<Product> getAllProduct() {
        return this.productRepository.all();
    }

    public List<Product> searchProducts(String value) {
        return this.productRepository.search(value);
    }

    public Product findById(int id) {
        return this.productRepository.findById(id);
    }

    public void addProduct(ProductDto productDto) {
        if (productDto.getName().isEmpty() || productDto.getCategory().isEmpty()) {
            throw new IllegalArgumentException("Product name and category cannot be empty.");
        }
        if (productDto.getPrice() <= 0) {
            throw new IllegalArgumentException("The price must be greater than 0.");
        }
        if (productDto.getStock() < 0) {
            throw new IllegalArgumentException("Stock quantity must be greater than or equal to 0.");
        }
        Product product = productMapper.toModel(productDto);
        Product addedProduct = productRepository.add(product);
        if (addedProduct != null) {
            Logger.info("PRODUCT CREATED SUCCESSFULLY: " + addedProduct.toString());
        } else {
            throw new RuntimeException("Failed to create product.");
        }
    }

    public Product updateProduct(ProductDto productDto, int id) {
        if (productDto.getName().isEmpty() || productDto.getCategory().isEmpty()) {
            throw new IllegalArgumentException("Product name and category cannot be empty.");
        }
        if (productDto.getPrice() <= 0) {
            throw new IllegalArgumentException("The price must be greater than 0.");
        }
        if (productDto.getStock() < 0) {
            throw new IllegalArgumentException("Stock quantity must be greater than or equal to 0.");
        }
        Product existingProduct = productRepository.findById(id);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product with the given ID not found.");
        }
        Product newProduct = productMapper.toModel(productDto);
        newProduct.setId(existingProduct.getId());
        newProduct.setTimestamp(existingProduct.getTimestamp());
        Product updatedProduct = productRepository.update(newProduct);
        if (updatedProduct == null) {
            throw new RuntimeException("Failed to update product.");
        }
        Logger.info("PRODUCT UPDATED SUCCESSFULLY: " + updatedProduct.toString());
        return updatedProduct;
    }

    public void deleteProduct(int id) {
        Product existingProduct = productRepository.findById(id);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product with the given ID not found.");
        }
        boolean check = this.productRepository.delete(id);
        if(check) {
            Logger.info("PRODUCT DELETED SUCCESSFULLY: " + existingProduct.toString());
        }
    }

}
