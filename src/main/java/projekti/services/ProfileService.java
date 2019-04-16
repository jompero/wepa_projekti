package projekti.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projekti.entities.Account;
import projekti.entities.Profile;
import projekti.repositories.ProfileRepository;

@Service
public class ProfileService {
    
    @Autowired
    private ProfileRepository profiles;
    
    public Profile getProfile(Account account) {
        Profile profile = profiles.findByAccount(account);
        return profile;
    }

    public Profile getProfile(String profileName) {
        Profile profile = profiles.findByProfileName(profileName);
        return profile;
    }

    public void saveProfile(Profile profile) {
        profiles.save(profile);
    }
}
