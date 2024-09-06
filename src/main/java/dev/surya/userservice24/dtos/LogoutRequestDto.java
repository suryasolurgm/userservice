package dev.surya.userservice24.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LogoutRequestDto {
    private String token;
}
