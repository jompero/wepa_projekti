package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import projekti.services.FriendRequestService;
import projekti.services.ProfileService;

@Controller
public class FriendsController {
    
    @Autowired
    ProfileService profiles;
    @Autowired
    FriendRequestService friendRequests;
    
    @GetMapping("/friends")
    public String index(Model model) {
        model.addAttribute("friends", profiles.getActiveProfileFriends());
        return "friends";
    }

    @PostMapping("/friends/{id}")
    public String confirmFriendRequest(@PathVariable Long id, @RequestParam boolean accept) {
        if (accept) {
            friendRequests.accept(id);
        } else {
            friendRequests.decline(id);
        }
        return "redirect:/";
    }
}
