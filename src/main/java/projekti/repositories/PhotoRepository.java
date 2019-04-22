package projekti.repositories;

import projekti.entities.Photo;
import projekti.entities.Profile;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

	List<Photo> findByProfile(Profile profile);
    
}
