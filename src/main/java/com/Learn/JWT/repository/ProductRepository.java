package com.Learn.JWT.repository;

import com.Learn.JWT.Entity.Product;
import com.Learn.JWT.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT p FROM Product p WHERE LOWER(p.productName) LIKE %:keyword% OR LOWER(p.category.categoryName) LIKE %:keyword%")
    List<Product> getProductsByKeyword(@Param("keyword") String keyword);

    List<Product> findBySeller(User seller);

    Optional<Product> findBySellerUserIdAndProductId(Integer userId, Integer productId);
}
