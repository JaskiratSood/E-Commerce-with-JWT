package com.Learn.JWT.DTO;

import com.Learn.JWT.Entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ProductDto {

    private Integer productId;
    private String productName;
    private Category category;

}
