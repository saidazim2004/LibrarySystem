package com.example.cosinuslibrarysystem.exception;


public class SendVerificationCodeException extends RuntimeException{
    public SendVerificationCodeException(String message) {
        super(message);
    }
}