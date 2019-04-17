package projekti.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AbstractPersistable<Long> {
    
    @NotEmpty
    @Size(min = 4, max = 18)
    private String username;
    
    @NotEmpty
    private String password;

    @OneToOne
    Profile profile;
    
    private SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
}
