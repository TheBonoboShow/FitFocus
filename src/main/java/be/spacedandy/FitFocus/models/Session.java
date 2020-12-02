package be.spacedandy.FitFocus.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Session {

    @Id
    @GeneratedValue
    private int id;

    @ManyToMany
    private List<User> users;
}
