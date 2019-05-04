package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import projekti.services.FriendRequestService;

@Controller
public class DefaultController {

    @Autowired
    FriendRequestService friendRequests;
    
    @GetMapping("/")
    public String index() {
        return "redirect:/profile";
    }
}
