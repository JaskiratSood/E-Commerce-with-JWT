package com.Learn.JWT.Exception;

public class ProductAlreadyExistsException extends RuntimeException{
    public ProductAlreadyExistsException(String s)  {
        super(s);
    }
}
