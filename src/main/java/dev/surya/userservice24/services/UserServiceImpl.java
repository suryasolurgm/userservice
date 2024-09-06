package dev.surya.userservice24.services;

import dev.surya.userservice24.exceptions.EmailIsRegisteredEception;
import dev.surya.userservice24.exceptions.InvalidTokenException;
import dev.surya.userservice24.exceptions.UserNotFoundException;
import dev.surya.userservice24.exceptions.WrongPasswordException;
import dev.surya.userservice24.models.Token;
import dev.surya.userservice24.models.User;
import dev.surya.userservice24.repository.TokenRepository;
import dev.surya.userservice24.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private TokenRepository tokenRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder ;
    public UserServiceImpl(TokenRepository tokenRepository,
                           UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }
    @Override
    public Token login(String email, String password) throws UserNotFoundException, WrongPasswordException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()) {
            // throw an exception or redirect user to signup
            throw new UserNotFoundException("User not found");
        }

        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password, user.getHashedPassword())) {
            // throw an exception
            throw new WrongPasswordException("Wrong password");
        }

        Token token = createToken(user);
        return tokenRepository.save(token);

    }

    @Override
    public User signUp(String name, String email, String password) throws EmailIsRegisteredEception {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()) {
            throw new EmailIsRegisteredEception("Email is already present");
        }
        User user = new User();
        user.setName(name);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        user.setEmail(email);
        return userRepository.save(user);
    }

    @Override
    public User validateToken(String token) throws InvalidTokenException {
        Optional<Token> tokenOptional = tokenRepository.
                findByValueAndDeletedAndExpiryAtGreaterThan(token,false,new Date());
        if(tokenOptional.isEmpty()){
            throw new InvalidTokenException("Invalid Token");
        }

        return tokenOptional.get().getUser();
    }

    @Override
    public void logout(String token) throws InvalidTokenException {
        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeleted(token,false);
        if(tokenOptional.isEmpty()){
            throw new InvalidTokenException("Invalid Token");
        }
        tokenOptional.get().setDeleted(true);
        tokenRepository.save(tokenOptional.get());
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    private Token createToken(User user) {
        Token token = new Token();
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphanumeric(128)); // Read about UUIDs

        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, 30);
        Date date30DaysFromToday = calendar.getTime();

        token.setExpiryAt(date30DaysFromToday);
        token.setDeleted(false);

        return token;
    }
}
