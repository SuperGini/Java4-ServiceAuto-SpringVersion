package com.gini.errors;

public class InvalidToken  extends RuntimeException{

    public InvalidToken(String message) {
        super(message);
    }
}
