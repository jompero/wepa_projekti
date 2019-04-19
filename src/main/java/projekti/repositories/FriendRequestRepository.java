package projekti.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import projekti.entities.FriendRequest;
import projekti.entities.Profile;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

	List<FriendRequest> findByTo(Profile profile);

}
