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
    @Autowired
    private AccountService accounts;
    
    public Profile getProfile(String profileName) {
        Profile profile = profiles.findByProfileName(profileName);
        return profile;
    }

    public Profile getActiveProfile() {
        return accounts.getActiveAccount().getProfile();
    }

    public Profile saveProfile(Profile profile) {
        Profile oldProfile = getActiveProfile();
        if (oldProfile != null) {
            oldProfile.setProfileName(profile.getProfileName());
            oldProfile.setFirstName(profile.getFirstName());
            oldProfile.setLastName(profile.getLastName());
            oldProfile.setProfileImage(profile.getProfileImage());
            return profiles.save(oldProfile);
        }
        return profiles.save(profile);
    }

    public boolean isUniqueProfileName(String profileName) {
        Profile profile = profiles.findByProfileName(profileName);
        Profile activeProfile = getActiveProfile();
        if (profile != null) {
            if (activeProfile != null && activeProfile.getId() != profile.getId()) {
                return false;
            }
        }
        return true;
    }
}
