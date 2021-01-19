package be.spacedandy.FitFocus.controllers;

import be.spacedandy.FitFocus.models.Session;
import be.spacedandy.FitFocus.models.SubscriptionType;
import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.models.UserPrincipal;
import be.spacedandy.FitFocus.security.NoSessionsLeftException;
import be.spacedandy.FitFocus.security.NoValidSubscriptionException;
import be.spacedandy.FitFocus.services.SubscriptionTypeService;
import be.spacedandy.FitFocus.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SubscriptionTypeController {
    @Autowired
    SubscriptionTypeService subscriptionTypeService;
    @Autowired
    UserService userService;

    @GetMapping("/prices")
    public String getSubscriptionTypes(Model model){
        List<SubscriptionType> subscriptionTypeList = subscriptionTypeService.getSubscriptionTypes();
        model.addAttribute("subTypes", subscriptionTypeList);
        return "prices";
    }

    @GetMapping("/buySubscription")
    public String getBuySubscriptionPage(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            model.addAttribute("message", "You need to be logged in to buy a subscription");
            return "login";
        }
        User user = userService.findByUsername(userPrincipal.getUsername());
        model.addAttribute("user", user);
        List<SubscriptionType> subscriptionTypeList = subscriptionTypeService.getSubscriptionTypes();
        model.addAttribute("subTypes", subscriptionTypeList);
        return "buy_subscription";
    }

    @PostMapping("/buySubscription")
    public String getBuySubscriptionPage(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model, SubscriptionType subscriptionType) {

        User user = userService.findByUsername(userPrincipal.getUsername());
        subscriptionTypeService.upgrade(user, subscriptionType);
        model.addAttribute("user", user);
        List<SubscriptionType> subscriptionTypeList = subscriptionTypeService.getSubscriptionTypes();
        model.addAttribute("subTypes", subscriptionTypeList);
        model.addAttribute("message", "Your subscription has been upgraded ! (no payment needed)");
        return "buy_subscription";
    }
}
