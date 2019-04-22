package projekti.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile extends AbstractPersistable<Long> {
    
    @Column(unique=true)
    @Size(min = 4, max = 14)
    String profileName;

    String firstName = "";
    String lastName = "";
    
    @OneToOne
    Photo profilePhoto;

    // These are the comments on the wall. Not explicitly by this profile.
    @OneToMany
    @Basic(fetch = FetchType.LAZY)
    @Size(max=10)
    List<Comment> comments = new ArrayList<>();

    @ManyToMany
    List<Profile> friends = new ArrayList<>();
}
