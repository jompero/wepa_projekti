package projekti.repositories;

import projekti.entities.Profile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    public Profile findByProfileName(String profileName);
}
