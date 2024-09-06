package dev.surya.userservice24.repository;

import dev.surya.userservice24.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValueAndDeletedAndExpiryAtGreaterThan(
            String tokenValue,
            Boolean deleted,
            Date date
    );

    Optional<Token> findByValueAndDeleted(String tokenValue, Boolean deleted);
}
