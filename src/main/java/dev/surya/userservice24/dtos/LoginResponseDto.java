package dev.surya.userservice24.dtos;

import dev.surya.userservice24.models.Token;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponseDto {
    private Token token;
    private ResponseStatus responseStatus;
}
