package be.spacedandy.FitFocus.repositories;

import be.spacedandy.FitFocus.models.Session;
import be.spacedandy.FitFocus.models.Sport;
import be.spacedandy.FitFocus.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    Optional<Session> findByCoach (User coach);

    Session findById (int id);

    List<Session> findAllBySport (Sport sport);

    List<Session> findAllByDate (String date);

    List<Session> findAllByOrderByDateAsc ();

    @Query(value = "SELECT session_id FROM session_participants WHERE user_id = ?1", nativeQuery = true)
    List<Integer> findSessionsByUserid(int userid);

    @Query(value = "SELECT user_id FROM session_participants WHERE session_id = ?1", nativeQuery = true)
    List<Integer> findUsersBySessionid(int sessionid);

    }
