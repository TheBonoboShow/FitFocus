package be.spacedandy.FitFocus.services;

import be.spacedandy.FitFocus.models.SubscriptionType;
import be.spacedandy.FitFocus.repositories.SubscriptionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionTypeService {

    @Autowired
    private SubscriptionTypeRepository subscriptionTypeRepository;

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

}
