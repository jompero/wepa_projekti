package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import projekti.entities.Account;
import projekti.repositories.AccountRepository;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accounts;
    @Autowired
    private DevelopmentSecurityConfiguration sec;
    
    public Account getAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accounts.findByUsername(username);
        return account;
    }

    void createAccount(Account account) {
        accounts.save(account);
    }
}
