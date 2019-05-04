package projekti.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude={"profilePhoto", "friends"})
public class Profile extends GenericEntity {
    
    // Uniqueness handled on service level. Commenting this out because NetBeans
    // fails the build due to 'Duplicate annotation' which is not an error
    // thrown by VS Code, Travis nor Heroku
    // @Column(unique=true)
    @Size(min = 4, max = 14)
    String profileName;

    String firstName = "";
    String lastName = "";
    
    @OneToOne
    @Basic(fetch = FetchType.LAZY)
    Photo profilePhoto;

    @ManyToMany
    @Basic(fetch = FetchType.LAZY)
    Set<Profile> friends = new HashSet<>();

}
