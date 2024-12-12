package ml.autentication.repository;

import ml.autentication.model.UserML;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMLRepository extends JpaRepository<UserML, String> {
    Optional<UserML> findByUsername(String username);
}