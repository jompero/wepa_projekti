package projekti.repositories;

import projekti.entities.Profile;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    public Profile findByProfileName(String profileName);

    @Query(
        "SELECT p FROM Profile p " + 
        "WHERE TRIM(CONCAT(LOWER(p.firstName), LOWER(p.lastName), LOWER(p.profileName))) " + 
        "LIKE CONCAT('%', LOWER(?1), '%')"
    )
    public List<Profile> findByFullNameContains(String string);

}
