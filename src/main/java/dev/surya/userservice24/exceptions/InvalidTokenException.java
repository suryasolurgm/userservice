package dev.surya.userservice24.exceptions;

public class InvalidTokenException extends Exception{
    public InvalidTokenException(String message){
        super(message);
    }
}
