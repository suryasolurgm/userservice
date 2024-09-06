package dev.surya.userservice24.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.surya.userservice24.models.Role;
import dev.surya.userservice24.models.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@NoArgsConstructor
@JsonDeserialize
public class MyUserDetails implements UserDetails {
    private String username;
    private String password;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private boolean accountNonLocked;
    private Collection<MyGrantedAuthority> authorities;
    public MyUserDetails(User user) {
        this.username = user.getEmail();
        this.password = user.getHashedPassword();
        authorities = new ArrayList<>();
        this.accountNonExpired = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.accountNonLocked = true;
        for (Role role : user.getRoles()) {
            authorities.add(new MyGrantedAuthority(role));
        }
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
