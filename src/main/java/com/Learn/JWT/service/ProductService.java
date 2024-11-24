package com.Learn.JWT.service;


import com.Learn.JWT.DTO.ProductDto;
import com.Learn.JWT.Entity.Category;
import com.Learn.JWT.Entity.Product;
import com.Learn.JWT.Entity.User;
import com.Learn.JWT.Exception.ProductAlreadyExistsException;
import com.Learn.JWT.Exception.ResourceNotFoundException;
import com.Learn.JWT.repository.CategoryRepository;
import com.Learn.JWT.repository.ProductRepository;
import com.Learn.JWT.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository catergoryRepository;

    public List<ProductDto> getProductsByKeyword(String keyword){

        List<Product> productsByKeyword = productRepository.getProductsByKeyword(keyword);

        return productsByKeyword
                .stream()
                .map(product -> new ProductDto(product.getProductId(), product.getProductName(), product.getCategory()))
                .toList();
    }

    public List<Product> getProductsBySeller(String username) {
        User seller = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found"));

        return productRepository.findBySeller(seller);
    }

    public Product getProductById(String username, Integer productId) {

        List<Product> productsBySeller = getProductsBySeller(username);
        Optional<Product> productById = productsBySeller.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst();
        if(productById.isEmpty()){
            throw new ResourceNotFoundException("Product not found");
        }

        return productById.get();

    }

    public Product saveProduct(String username, Product productRequest) {

        User seller = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Seller not found"));

        Optional<Product> product = productRepository.findBySellerUserIdAndProductId(seller.getUserId(), productRequest.getProductId());
        if(product.isPresent()){
            throw new ProductAlreadyExistsException("Product already exists");
        }


        Category existingCategory = catergoryRepository
                .findByCategoryName(productRequest.getCategory().getCategoryName())
                .orElseThrow(() -> new ResourceNotFoundException("Category does not exists"));

        // set relationships
        productRequest.setCategory(existingCategory);
        productRequest.setSeller(seller);

        return productRepository.save(productRequest);

    }

    public void updateProduct(String username, Product productRequest) {

        User seller = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Seller not found"));

        Optional<Product> product = productRepository.findBySellerUserIdAndProductId(seller.getUserId(), productRequest.getProductId());
        if(product.isEmpty()){
            throw new ResourceNotFoundException("Product does not exists");
        }

        Product existingProduct = product.get();

        existingProduct.setProductName(productRequest.getProductName());
        existingProduct.setPrice(productRequest.getPrice());

        Category existingCategory = catergoryRepository
                .findByCategoryName(productRequest.getCategory().getCategoryName())
                .orElseThrow(() -> new ResourceNotFoundException("Category does not exists"));


        existingProduct.setCategory(existingCategory);
        existingProduct.setSeller(seller);

        productRepository.save(existingProduct);

    }

    public boolean deleteProduct(String username, Integer productId) {

        User seller = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Seller not found"));

        Product productToBeRemoved = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if(!productToBeRemoved.getSeller().getUserId().equals(seller.getUserId()))
            return false;

        productRepository.delete(productToBeRemoved);
        return true;
    }
}
