package dev.surya.userservice24.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Role extends BaseModel{
    private String roleName;
}
