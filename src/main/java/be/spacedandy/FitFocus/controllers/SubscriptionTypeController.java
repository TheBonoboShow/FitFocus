package be.spacedandy.FitFocus.controllers;

import be.spacedandy.FitFocus.models.SubscriptionType;
import be.spacedandy.FitFocus.services.SubscriptionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SubscriptionTypeController {
    @Autowired
    SubscriptionTypeService subscriptionTypeService;

    @GetMapping("/prices")
    public String getSubscriptionTypes(Model model){
        List<SubscriptionType> subscriptionTypeList = subscriptionTypeService.getSubscriptionTypes();
        model.addAttribute("subTypes", subscriptionTypeList);
        return "prices";
    }
}
