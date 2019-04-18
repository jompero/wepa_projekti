package projekti.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import projekti.entities.FriendRequest;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

}
