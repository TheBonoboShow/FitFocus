package be.spacedandy.FitFocus.repositories;

import be.spacedandy.FitFocus.models.Session;
import be.spacedandy.FitFocus.models.Sport;
import be.spacedandy.FitFocus.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    Optional<Session> findByCoach (User coach);

    List<Session> findAllBySport (Sport sport);
}
