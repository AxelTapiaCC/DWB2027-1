package com.product.exception;

import org.springframework.dao.DataAccessException;

public class DBAccesException extends RuntimeException{

    private DataAccessException exception;

    public DBAccesException(DataAccessException e) {
        this.exception = e;
    }
    
}
