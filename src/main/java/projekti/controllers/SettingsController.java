package projekti.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import projekti.entities.Profile;
import projekti.services.AccountService;
import projekti.services.ProfileService;

@Controller
public class SettingsController {

    @Autowired
    private AccountService account;
    @Autowired
    private ProfileService profiles;

    @GetMapping("/settings")
    public String getSettings(@ModelAttribute Profile profile,
            Model model) {
        Profile activeProfile = account.getProfile();
        if (activeProfile != null) {
            model.addAttribute("profile", activeProfile);
            model.addAttribute("activeProfile", activeProfile);
        }
        
        return "settings";
    }
    
    @PostMapping("/settings")
    public String postSettingsForm(@Valid @ModelAttribute Profile profile, BindingResult bindingResult, Model model) {
        
        String newProfileName = profile.getProfileName();
        if (!profiles.isUniqueProfileName(newProfileName)) {
            bindingResult.rejectValue("profile", "error.profile", "Profile name taken.");
        }
        if(bindingResult.hasErrors()) {
            model.addAttribute("profile", account.getProfile());
            return "settings";
        }

        profiles.saveProfile(profile);

        return "redirect:/settings/";
    }

    @PostMapping("/settings/profilephoto/{id}")
    public String setProfilePhoto(@PathVariable Long id) {
        profiles.setProfilePhoto(id);
        String activeProfileName = profiles.getActiveProfile().getProfileName();
        return String.format("redirect:/profile/%s/album/{id}/", activeProfileName);
    }
}
