package dev.surya.userservice24.controllers;

import dev.surya.userservice24.dtos.*;
import dev.surya.userservice24.dtos.ResponseStatus;
import dev.surya.userservice24.models.Token;
import dev.surya.userservice24.models.User;
import dev.surya.userservice24.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public List<User> getAllUsers() {
        throw new RuntimeException("Not implemented");
      // return userService.getAll();
    }
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {

        LoginResponseDto responseDto = new LoginResponseDto();
        try {
            Token token = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            responseDto.setToken(token);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }
    @GetMapping ("/testing/{id}")
    public ResponseEntity<?> testing(@PathVariable int id) {
        return new ResponseEntity<>(id,HttpStatus.OK);
    }

    @PostMapping("/signup")
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto requestDto) {
        SignUpResponseDto responseDto = new SignUpResponseDto();
        try {
            User user = userService.signUp(
                    requestDto.getName(),
                    requestDto.getEmail(),
                    requestDto.getPassword()
            );
            responseDto.setUser(user);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }

    @PostMapping("/validate")
    public ResponseEntity<UserDto> validateToken(@RequestHeader("Authorization") String token) {
        UserDto responseDto = new UserDto() ;
        try {
            User user = userService.validateToken(token);
             responseDto= responseDto.fromUser(user);
             responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto) {
        ResponseEntity<Void> responseEntity ;
        try {
            userService.logout(logoutRequestDto.getToken());
            responseEntity = ResponseEntity.ok().build();
        }catch (Exception e) {
            responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return responseEntity;
    }
}
