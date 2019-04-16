package projekti.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import projekti.entities.Account;
import projekti.services.AccountService;

@Controller
public class AuthenticationController {
    
    @Autowired
    private AccountService accounts;

    @GetMapping("/login")
    public String getLogin(@ModelAttribute Account account, Model model) {
        model.addAttribute("title", "Log In");
        model.addAttribute("profilename", "USERNAME_HERE");
        return "login";
    }

    @GetMapping("/signup")
    public String getSignUp(@ModelAttribute Account account, Model model) {
        model.addAttribute("title", "Sign Up");
        model.addAttribute("profilename", "USERNAME_HERE");
        return "signup";
    }
    
    @PostMapping("/signup")
    public String postSignUpForm(@Valid @ModelAttribute Account account, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "signup";
        }
        accounts.createAccount(account);
        
        return "redirect:/profile";
    }
}
