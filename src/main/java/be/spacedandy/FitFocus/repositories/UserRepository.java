package be.spacedandy.FitFocus.repositories;

import be.spacedandy.FitFocus.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUsername(String username);

    public User findByEmail(String email);

    public User findById(int id);

    public User findByVerificationToken(String token);

    public User findByPasswordResetToken(String token);
}
