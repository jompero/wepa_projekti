package projekti.repositories;

import projekti.entities.Profile;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    public Profile findByProfileName(String profileName);

	public List<Profile> findByFirstNameStartsWith(String string);

}
