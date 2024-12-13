package ml.authentication.repository;

import ml.authentication.model.UserML;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMLRepository extends JpaRepository<UserML, String> {
    Optional<UserML> findByUsername(String username);
}