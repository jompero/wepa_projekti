package projekti.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
    String profileName;

    String firstName;
    String lastName;
    
    @OneToOne
    Photo profileImage;

    @OneToMany
    List<Comment> comments;

    @ManyToMany
    List<Profile> friends = new ArrayList<>();
}
