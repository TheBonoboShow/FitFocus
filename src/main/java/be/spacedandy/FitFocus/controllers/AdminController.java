package be.spacedandy.FitFocus.controllers;

import be.spacedandy.FitFocus.models.*;
import be.spacedandy.FitFocus.security.EmailAlreadyExistException;
import be.spacedandy.FitFocus.security.UserAlreadyExistException;
import be.spacedandy.FitFocus.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    SportService sportService;
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;
    @Autowired
    SubscriptionTypeService subscriptionTypeService;
    @Autowired
    RegisterService registerService;
    @Autowired
    SessionService sessionService;

    @GetMapping("/admin")
    public String getAdmin(Model model){
        return findPaginated(1, model);
    }

    @GetMapping("/page/{pageNumber}")
    public String findPaginated(@PathVariable(value = "pageNumber") int pageNumber, Model model){
        int pageSize = 10;

        Page<User> page = userService.findPaginated(pageNumber, pageSize);
        List<User> users = page.getContent();

        model.addAttribute("currentPage" , pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("users", users);
        List<Role> roleList = roleService.getRoles();
        model.addAttribute("roles", roleList);
        List<Sport> sportList = sportService.getSports();
        model.addAttribute("sports", sportList);
        List<SubscriptionType> subscriptionTypeList = subscriptionTypeService.getSubscriptionTypes();
        model.addAttribute("subTypes", subscriptionTypeList);
        return "admin";
    }

    // all edit functions
    @RequestMapping(value="/admin/updateSport", method= {RequestMethod.PUT, RequestMethod.GET})
    public String update(Sport sport) {
        sportService.save(sport);
        return "redirect:/admin";
    }

    @RequestMapping(value="/admin/updateRole", method= {RequestMethod.PUT, RequestMethod.GET})
    public String update(Role role) {
        roleService.save(role);
        return "redirect:/admin";
    }

    @RequestMapping(value="/admin/updateSubscriptionType", method= {RequestMethod.PUT, RequestMethod.GET})
    public String update(SubscriptionType subscriptionType) {
        subscriptionTypeService.save(subscriptionType);
        return "redirect:/admin";
    }

    @RequestMapping(value="/admin/updateUser", method= {RequestMethod.PUT, RequestMethod.GET})
    public String update(User user) {
        user.setReservedSessions(sessionService.getUserSessions(userService.findById(user.getId())));
        userService.saveAdmin(user);
        return "redirect:/admin";
    }

    // all create new functions
    @PostMapping("/admin/addNewSport")
    public String addNew(Sport sport){
        sportService.save(sport);
        return "redirect:/admin";
    }

    @PostMapping("/admin/addNewRole")
    public String addNew(Role role){
        roleService.save(role);
        return "redirect:/admin";
    }

    @PostMapping("/admin/addNewSubscriptionType")
    public String addNew(SubscriptionType subscriptionType){
        subscriptionTypeService.save(subscriptionType);
        return "redirect:/admin";
    }

    @PostMapping("/admin/addNewUser")
    public String addNew(User user, Model model) {
        try {
            registerService.register(user);
            String url = "http://localhost:8080/verify?token=" ;
            url += user.getVerificationToken();
            registerService.sendVerificationEmail(user, url);
        }catch (UserAlreadyExistException e){
            expandModel(model);
            model.addAttribute("message", "This username already exists");
            return "admin";
        }
        catch (EmailAlreadyExistException e){
            expandModel(model);
            model.addAttribute("message", "This email already exists");
            return "admin";
        }
        catch (UnsupportedEncodingException | MessagingException e){
            model.addAttribute("user", user);
            return "admin";
        }
        return "redirect:/admin";
    }

    // all find functions
    @RequestMapping("/admin/findSubscriptionType")
    @ResponseBody
    public Optional<SubscriptionType> findSubscriptionTypeById(int id){
        return subscriptionTypeService.findById(id);
    }

    @RequestMapping("/admin/findSport")
    @ResponseBody
    public Optional<Sport> findSportById(int id){
        return sportService.findById(id);
    }

    @RequestMapping("/admin/findUser")
    @ResponseBody
    public User findUserById(int id){
        return userService.findById(id);
    }

    @RequestMapping("/admin/findRole")
    @ResponseBody
    public Optional<Role> findRoleById(int id){
        return roleService.findById(id);
    }

    // all delete functions
    @RequestMapping(value = "/admin/deleteSport", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteSport(Integer id){
        sportService.delete(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/deleteSubscriptionType", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteSubscriptionType(Integer id){
        subscriptionTypeService.delete(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/deleteRole", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteRole(Integer id){
        roleService.delete(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/deleteUser", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteUser(Integer id){
        userService.delete(id);
        return "redirect:/admin";
    }

    public void expandModel(Model model){
        List<User> userList = userService.getUsers();
        model.addAttribute("users", userList);
        List<Role> roleList = roleService.getRoles();
        model.addAttribute("roles", roleList);
        List<Sport> sportList = sportService.getSports();
        model.addAttribute("sports", sportList);
        List<SubscriptionType> subscriptionTypeList = subscriptionTypeService.getSubscriptionTypes();
        model.addAttribute("subTypes", subscriptionTypeList);
    }
}
