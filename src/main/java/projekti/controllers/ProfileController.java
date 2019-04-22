package projekti.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import projekti.entities.Comment;
import projekti.entities.Profile;
import projekti.services.FriendRequestService;
import projekti.services.ProfileService;

@Controller
public class ProfileController {

    @Autowired
    private ProfileService profiles;
    @Autowired
    private FriendRequestService requests;
    
    @GetMapping("/profile")
    public String redirectProfile() {
        Profile profile = profiles.getActiveProfile();
        if (profile == null) {
            return "redirect:/settings";
        }
        return "redirect:/profile/" + profile.getProfileName() + "/";
    }

    @GetMapping("/profile/{profileName}")
    public String getProfile(Model model, @PathVariable String profileName, @ModelAttribute Comment comment) {
        model.addAttribute("profile", profiles.getProfile(profileName));
        return "profile";
    }

    @PostMapping("/profile/{profileName}/comment")
    public String postComment(@Valid @ModelAttribute Comment comment, @PathVariable String profileName) {
        Profile profile = profiles.getProfile(profileName);
        profiles.comment(profile, comment);
        return "redirect:/profile/" + profileName + "/";
    }

    @PostMapping("/profile/{profileName}/request")
    public String postFriendRequest(@PathVariable String profileName) {
        Profile from = profiles.getProfile(profileName);
        Profile to = profiles.getActiveProfile();
        requests.saveRequest(from, to);
        return "redirect:/profile/" + profileName + "/";
    }
}
