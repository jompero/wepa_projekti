package projekti.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projekti.entities.Account;
import projekti.entities.Comment;
import projekti.entities.Profile;
import projekti.repositories.ProfileRepository;

@Service
public class ProfileService {
    
    @Autowired
    private ProfileRepository profiles;
    @Autowired
    private AccountService accounts;
    @Autowired
    private CommentsService comments;
    @Autowired
    private PhotoService photos;
    
    public Profile getProfile(String profileName) {
        Profile profile = profiles.findByProfileName(profileName);
        return profile;
    }

    public Profile getActiveProfile() {
        Account account = accounts.getActiveAccount();
        if (account == null) {
            return null; 
        }
        return account.getProfile();
    }

    public Profile createProfile(Account account, Profile profile) {
        profiles.save(profile);
        accounts.setProfile(account, profile);
        return profile;
    }

    public Profile saveProfile(Profile profile) {
        Account account = accounts.getActiveAccount();
        Profile oldProfile = getActiveProfile();
        if (oldProfile != null) {
            oldProfile.setProfileName(profile.getProfileName());
            oldProfile.setFirstName(profile.getFirstName());
            oldProfile.setLastName(profile.getLastName());
            // Profile photo setup separately
            // oldProfile.setProfilePhoto(profile.getProfilePhoto());
            return profiles.save(oldProfile);
        }
        return createProfile(account, profile);
    }

    public boolean isUniqueProfileName(String profileName) {
        Profile profile = profiles.findByProfileName(profileName);
        Profile activeProfile = getActiveProfile();
        if (profile != null) {
            if (activeProfile == null) {
                return false;
            }
            if (activeProfile != null && activeProfile.getId() != profile.getId()) {
                return false;
            }
        }
        return true;
    }

    public void comment(Profile to, Comment comment) {
        comment.setFrom(getActiveProfile());
        comment.setTo(to);
        comments.saveComment(comment);
    }

    public List<Profile> getActiveProfileFriends() {
        Profile activeProfile = getActiveProfile();
        return activeProfile.getFriends();
    }

    public void addFriend(Profile from, Profile to) {
        to.getFriends().add(from);
        from.getFriends().add(to);
        profiles.save(to);
    }

	public void setProfilePhoto(Long id) {
        Profile profile = getActiveProfile();
        profile.setProfilePhoto(photos.getPhoto(id));
        profiles.save(profile);
    }
}
