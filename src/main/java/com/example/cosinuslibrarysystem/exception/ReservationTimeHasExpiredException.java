package com.example.cosinuslibrarysystem.exception;

public class ReservationTimeHasExpiredException extends RuntimeException {
    public ReservationTimeHasExpiredException(String message) {
        super(message);
    }
}
