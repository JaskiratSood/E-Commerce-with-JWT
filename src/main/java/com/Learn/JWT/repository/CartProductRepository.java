package com.Learn.JWT.repository;


import com.Learn.JWT.Entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Integer> {

}
