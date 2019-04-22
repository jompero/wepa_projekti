package projekti.repositories;

import projekti.entities.Account;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>  {

    @EntityGraph(attributePaths = {"profile"})
    public Account findByUsername(String username);
    
}
