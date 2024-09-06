package dev.surya.userservice24.services;

import dev.surya.userservice24.exceptions.EmailIsRegisteredEception;
import dev.surya.userservice24.exceptions.InvalidTokenException;
import dev.surya.userservice24.exceptions.UserNotFoundException;
import dev.surya.userservice24.exceptions.WrongPasswordException;
import dev.surya.userservice24.models.Token;
import dev.surya.userservice24.models.User;

import java.util.List;

public interface UserService {
    public Token login(String email, String password) throws UserNotFoundException, WrongPasswordException;
    public User signUp(String name, String email, String password) throws EmailIsRegisteredEception;
    public User validateToken(String token) throws InvalidTokenException;
    public void logout(String token) throws InvalidTokenException;

    List<User> getAll();
}
