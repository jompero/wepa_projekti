package projekti.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import projekti.DevelopmentSecurityConfiguration;
import projekti.entities.Account;
import projekti.entities.Profile;
import projekti.repositories.AccountRepository;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accounts;
    @Autowired
    private DevelopmentSecurityConfiguration sec;
    @Autowired
    private ProfileService profiles;
    
    public Account getActiveAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accounts.findByUsername(username);
        return account;
    }

    public void createAccount(Account account) {
        account.setPassword(sec.passwordEncoder().encode(account.getPassword())); // Password is passed as plain text in form but needs to be encoded
        accounts.save(account);
        // Let's also populate the profile so that it isn't null
        Profile profile = new Profile();
        profile.setProfileName(Integer.toString(account.hashCode()));
    }

    public void setProfile(Profile profile) {
        Account account = getActiveAccount();
        if (account.getProfile() == null) {
            account.setProfile(profile);
        }
    }
    
    public Profile getProfile() {
        return getActiveAccount().getProfile();
    }
}
