package be.spacedandy.FitFocus.services;

import be.spacedandy.FitFocus.models.Role;
import be.spacedandy.FitFocus.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired private RoleRepository roleRepository;


    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

    public void save(Role role){
        roleRepository.save(role);
    }

    public Optional<Role> findById(int id){
        return roleRepository.findById(id);
    }

    public Optional<Role> findByName(String name){
        return roleRepository.findByName(name);
    }

    public void delete(int id) {
        roleRepository.deleteById(id);
    }
}
