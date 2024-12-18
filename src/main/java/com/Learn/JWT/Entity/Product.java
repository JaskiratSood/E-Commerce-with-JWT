package com.Learn.JWT.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Product Id is mandatory")
    private Integer productId;

    private String productName;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "userId", updatable = false)
    @JsonIgnore
    private User seller;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private  Category category;

}