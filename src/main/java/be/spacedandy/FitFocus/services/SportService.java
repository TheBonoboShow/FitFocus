package be.spacedandy.FitFocus.services;

import be.spacedandy.FitFocus.models.Sport;
import be.spacedandy.FitFocus.repositories.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SportService {

    @Autowired private SportRepository sportRepository;


    public List<Sport> getSports(){
        return sportRepository.findAll();
    }

    public void save(Sport sport){
        sportRepository.save(sport);
    }

    public Optional<Sport> findById(int id){
        return sportRepository.findById(id);
    }

    public Sport findByName(String name){ return sportRepository.findByName(name);
    }

    public void delete(int id) {
        sportRepository.deleteById(id);
    }
}
