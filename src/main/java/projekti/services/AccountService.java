package projekti.services;

import org.apache.commons.lang3.RandomStringUtils;
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
        // Password is passed as plain text in form but needs to be encoded
        account.setPassword(sec.passwordEncoder().encode(account.getPassword()));
        accounts.save(account);
        
        // Let's also populate the profile so that it isn't null
        Profile profile = new Profile();
        String randomName = RandomStringUtils.randomAlphanumeric(14);
        while (!profiles.isUniqueProfileName(randomName)) {
            randomName = RandomStringUtils.randomAlphanumeric(14);
        }
        profile.setProfileName(randomName);
        profiles.createProfile(account, profile);
    }

    public boolean isUniqueUserName(String username) {
        Account account = accounts.findByUsername(username);
        if (account != null) {
            return false;
        }
        return true;
    }

    public void setProfile(Account account, Profile profile) {
        if (account.getProfile() == null) {
            account.setProfile(profile);
        }
        accounts.save(account);
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
