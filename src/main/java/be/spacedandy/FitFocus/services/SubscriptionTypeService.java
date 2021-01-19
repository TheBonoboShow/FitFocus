package be.spacedandy.FitFocus.services;

import be.spacedandy.FitFocus.models.SubscriptionType;
import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.repositories.SubscriptionTypeRepository;
import be.spacedandy.FitFocus.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionTypeService {

    @Autowired
    private SubscriptionTypeRepository subscriptionTypeRepository;
    @Autowired
    private UserRepository userRepository;

    public List<SubscriptionType> getSubscriptionTypes(){
        return subscriptionTypeRepository.findAll();
    }

    public void save(SubscriptionType subscriptionType){
        subscriptionTypeRepository.save(subscriptionType);
    }

    public Optional<SubscriptionType> findById(int id){
        return subscriptionTypeRepository.findById(id);
    }

    public void delete(int id) {
        subscriptionTypeRepository.deleteById(id);
    }

    public void upgrade(User user, SubscriptionType subscriptionType) {
        user.setRemainingSessions(subscriptionType.getNumberOfSessions());
        user.setStartDate(LocalDate.now().toString());
        user.setEndDate(LocalDate.now().plusDays(subscriptionType.getDaysValid()).toString());
        user.setSubscriptionType(subscriptionType);
        userRepository.save(user);
    }
}
