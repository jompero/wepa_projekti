package projekti.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
        return "login";
    }

    @GetMapping("/signup")
    public String getSignUp(@ModelAttribute Account account, Model model) {
        return "signup";
    }

    @PostMapping("/signup")
    public String postSignUpForm(@Valid @ModelAttribute Account account, BindingResult bindingResult,
            HttpServletRequest request) throws ServletException {
        if(bindingResult.hasErrors()) {
            return "signup";
        }
        // Custom check for unique usernames
        if (!accounts.isUniqueUserName(account.getUsername())) {
            bindingResult.rejectValue("username", "error.account", "Username taken.");
            return "signup";
        }

        // Store info for auto-login as password will be encoded after this
        String username = account.getUsername();
        String password = account.getPassword();

        // Save account
        accounts.createAccount(account);

        // Auto-login
        request.login(username, password);
        
        return "redirect:/settings";
    }
}
