package projekti.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Photo extends AbstractPersistable<Long> {

    String name;
    String contentType;
    Long size;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;

    @OneToMany
    private List<Profile> likes = new ArrayList<>();
    
    @ManyToOne
    private Profile profile;
}
