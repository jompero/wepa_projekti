package projekti.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AbstractPersistable<Long> {
    
    private String username;
    private String password;
    private SimpleGrantedAuthority authority;
    
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        authority = new SimpleGrantedAuthority("USER");
    }
}
