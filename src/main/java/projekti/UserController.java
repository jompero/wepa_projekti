package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.entities.Account;

@Controller
public class UserController {
    
    @Autowired
    private AccountService accounts;
    
    @GetMapping("/user")
    public String user(Model model) {
        Account user = accounts.getUser();
        model.addAttribute(user);
        return "user";
    }
    
    @PostMapping("/registration")
    public String register(@RequestParam String username, @RequestParam String password) {
        
        accounts.createUser(username, password);
        
        return "redirect:/user";
    }
}
