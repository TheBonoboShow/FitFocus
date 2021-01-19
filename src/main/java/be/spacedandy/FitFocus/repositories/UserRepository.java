package be.spacedandy.FitFocus.repositories;

import be.spacedandy.FitFocus.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUsername(String username);

    public User findByEmail(String email);

    public User findById(int id);

    public User findByVerificationToken(String token);

    public User findByPasswordResetToken(String token);

    @Query(value = "SELECT * FROM user u WHERE u.username LIKE %:keyword% OR u.email LIKE %:keyword% ORDER BY u.username", nativeQuery = true)
    Page<User> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM session_participants_join WHERE user_id = ?1", nativeQuery = true)
    void deleteSessionsUser(int id);
}
