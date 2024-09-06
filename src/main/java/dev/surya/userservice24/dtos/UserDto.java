package dev.surya.userservice24.dtos;

import dev.surya.userservice24.models.Role;
import dev.surya.userservice24.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    private List<Role> roles;
    private ResponseStatus responseStatus;
    public  UserDto fromUser(User user) {
        if (user == null) {
            return null;
        }


        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setRoles(user.getRoles());

        return this;
    }
}
