package ma.ensaf.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.ensaf.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}