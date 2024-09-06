package dev.surya.userservice24.exceptions;

public class WrongPasswordException extends Exception{
    public WrongPasswordException(String message){
        super(message);
    }
}
