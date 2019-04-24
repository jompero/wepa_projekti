package projekti.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Photo extends GenericEntity {

    String name;
    String description;
    String contentType;
    Long size;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;
    
    @ManyToOne
    private Profile profile;

    @OneToMany
    @Basic(fetch = FetchType.LAZY)
    private Map<Long, Profile> likes = new HashMap<>();

}
