package projekti.controllers;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import projekti.entities.Profile;
import projekti.services.FriendRequestService;
import projekti.services.ProfileService;

@Controller
public class FriendsController {
    
    @Autowired
    ProfileService profiles;
    @Autowired
    FriendRequestService friendRequests;
    
    @GetMapping("/profile/{profileName}/friends")
    public String index(@PathVariable String profileName, Model model) {
        Profile profile = profiles.getProfile(profileName);
        model.addAttribute("profile", profile);
        model.addAttribute("friends", profile.getFriends());
        model.addAttribute("activeProfile", profiles.getActiveProfile());
        model.addAttribute("friendRequests", friendRequests.getActiveProfileFriendRequests());
        return "friends";
    }

    @PostMapping("/profile/{profileName}/friends/{id}")
    public String confirmFriendRequest(@PathVariable Long id, 
            @RequestParam boolean accept,
            @PathVariable String profileName) {
        if (accept) {
            friendRequests.accept(id);
        } else {
            friendRequests.decline(id);
        }
        return String.format("redirect:/profile/%s/friends/", profileName);
    }

    @GetMapping("/search")
    public String searchProfile(@RequestParam String name, Model model) {
        HashSet<Profile> result = profiles.search(name);
        model.addAttribute("profiles", result);
        return "search";
    }
}
