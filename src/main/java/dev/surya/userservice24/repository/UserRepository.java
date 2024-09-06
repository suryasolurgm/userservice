package dev.surya.userservice24.repository;

import dev.surya.userservice24.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByname(String name);
    Optional<User> findByEmail(String email);
    Optional<User> findById(long id);

    List<User> findAll();
}
