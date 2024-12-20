package com.example.OnlineSinema.exceptions;

public class ThisEmailAlreadyConnected extends RuntimeException {
    public ThisEmailAlreadyConnected(String message){
        super(message);
    }
}
