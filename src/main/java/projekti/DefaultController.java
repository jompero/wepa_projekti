package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import projekti.repositories.AccountRepository;

@Controller
public class DefaultController {
    
    @Autowired
    AccountRepository accountRepository;
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("users", accountRepository.findAll());
        model.addAttribute("profilename", "USERNAME_HERE");
        model.addAttribute("title", "Mockbook");
        return "index";
    }
}
