package com.Learn.JWT.service;

import com.Learn.JWT.Entity.Cart;
import com.Learn.JWT.Entity.Product;

import com.Learn.JWT.Entity.*;
import com.Learn.JWT.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository catergoryRepository;
    private final CartService cartService;

    public ResponseEntity<?> getCart(String username) {
        try {
            Cart cart = cartService.getCartForUser(username);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }


    public ResponseEntity<?> addProductToCart(String username, Product productRequest) {

        try {

            cartService.addProductToCart(username, productRequest);
            return ResponseEntity.status(HttpStatus.OK).body(null);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    public ResponseEntity<?> updateCart(String username, CartProduct cartProductRequest) {

        try {
            cartService.updateCartProduct(username, cartProductRequest);

            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    public ResponseEntity<?> removeProductFromCart(String username, Product productRequest) {

        try {
            cartService.removeProductFromCart(username, productRequest);
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }
}
