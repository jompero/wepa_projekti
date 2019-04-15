package projekti.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
    
    //@NotEmpty
    //@Size(min = 4, max = 18)
    private String username;
    
    //@NotEmpty
    //@Size(min = 4, max = 18)
    private String password;
    
    private SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
}