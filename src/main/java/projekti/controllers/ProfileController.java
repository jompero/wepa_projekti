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
import org.springframework.web.bind.annotation.RequestParam;

import projekti.entities.Comment;
import projekti.entities.Profile;
import projekti.services.CommentsService;
import projekti.services.FriendRequestService;
import projekti.services.ProfileService;

@Controller
public class ProfileController {

    @Autowired
    private ProfileService profiles;
    @Autowired
    private CommentsService comments;
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
    public String getProfile(Model model, @ModelAttribute Comment comment, @PathVariable String profileName) {
        Profile profile = profiles.getProfile(profileName);
        model.addAttribute("activeProfile", profiles.getActiveProfile());
        model.addAttribute("profile", profile);
        model.addAttribute("messages", comments.getMessages(profile, 0));
        return "profile";
    }

    @PostMapping("/profile/{profileName}/comment")
    public String postComment(@RequestParam Long to,
            @RequestParam String content,
            @PathVariable String profileName,
            Model model) {
        comments.commentComment(content, to);
        return "redirect:/profile/" + profileName + "/";
    }

    @PostMapping("/profile/{profileName}/message")
    public String postMessage(@Valid @ModelAttribute Comment comment, 
            BindingResult bindingResult,
            @PathVariable String profileName,
            Model model) {
        
        if(bindingResult.hasErrors()) {
            Profile profile = profiles.getProfile(profileName);
            model.addAttribute("activeProfile", profiles.getActiveProfile());
            model.addAttribute("profile", profile);
            model.addAttribute("messages", comments.getMessages(profile, 0));
            return "profile";
        }
        Profile profile = profiles.getProfile(profileName);
        profiles.comment(profile, comment);
        return "redirect:/profile/" + profileName + "/";
    }

    @PostMapping("/profile/{profileName}/comment/{id}/like")
    public String likeComment(@PathVariable String profileName, 
            @PathVariable Long id) {
        comments.likeComment(id);
        return "redirect:/profile/" + profileName + "/";
    }

    @PostMapping("/profile/{profileName}/request")
    public String postFriendRequest(@PathVariable String profileName) {
        Profile to = profiles.getProfile(profileName);
        Profile from = profiles.getActiveProfile();
        requests.saveRequest(from, to);
        return "redirect:/profile/" + profileName + "/";
    }
}
