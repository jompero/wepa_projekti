package projekti.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projekti.entities.FriendRequest;
import projekti.entities.Profile;
import projekti.repositories.FriendRequestRepository;

@Service
public class FriendRequestService {

    @Autowired
    FriendRequestRepository friendRequests;

    @Autowired
    ProfileService profiles;

	public List<FriendRequest> getActiveProfileFriendRequests() {
        return friendRequests.findByTo(profiles.getActiveProfile());
    };

    public List<FriendRequest> findAll() {
        return friendRequests.findAll();
    };

    public FriendRequest saveRequest(Profile from, Profile to) {
        FriendRequest request = new FriendRequest();
        request.setFrom(from);
        request.setTo(to);
        return friendRequests.save(request);
    }

	public void accept(Long id) {
        FriendRequest request = friendRequests.getOne(id);
        profiles.addFriend(request.getFrom(), request.getTo());
        friendRequests.deleteById(id);
	}

	public void decline(Long id) {
        friendRequests.deleteById(id);
	}

}
