package dev.surya.userservice24.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.surya.userservice24.models.Role;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
@NoArgsConstructor
@JsonDeserialize
public class MyGrantedAuthority implements GrantedAuthority {
    private String authority;
    public MyGrantedAuthority(Role role) {
        this.authority = role.getRoleName();
    }
    @Override
    public String getAuthority() {
        return authority;
    }
}
