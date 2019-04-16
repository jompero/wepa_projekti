package projekti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import projekti.entities.Profile;
import projekti.services.AccountService;
import projekti.services.ProfileService;

@Controller
public class ProfileController {

    @Autowired
    private AccountService accounts;
    @Autowired
    private ProfileService profiles;
    
    @GetMapping("/profile")
    public String redirectProfile() {
        Profile profile = profiles.getProfile(accounts.getActiveAccount());
        if (profile == null) {
            return "redirect:/settings";
        }
        return "redirect:/profile/" + profile.getProfileName();
    }

    @GetMapping("/profile/{profileName}")
    public String profile(Model model, @PathVariable String profileName) {
        model.addAttribute("profile", profiles.getProfile(profileName));
        return "profile";
    }
}
