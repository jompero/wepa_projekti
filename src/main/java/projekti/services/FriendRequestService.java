package projekti.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projekti.entities.FriendRequest;
import projekti.entities.Profile;
import projekti.repositories.FriendRequestRepository;

@Service
public class FriendRequestService {

    @Autowired
    FriendRequestRepository friendRequests;

    public FriendRequest saveRequest(Profile from, Profile to) {
        FriendRequest request = new FriendRequest();
        request.setFrom(from);
        request.setTo(to);
        return friendRequests.save(request);
    }

}
