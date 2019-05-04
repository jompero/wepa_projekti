package projekti.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude="profile")
public class Photo extends GenericEntity {

    String name;
    String description;
    String contentType;
    Long size;

    @Lob
    @Column(columnDefinition="BLOB")
    //@Type(type="org.hibernate.type.BinaryType") 
    //@Basic(fetch = FetchType.LAZY)
    private byte[] content;
    
    @ManyToOne
    @Basic(fetch = FetchType.LAZY)
    private Profile profile;

    @OneToMany
    @Basic(fetch = FetchType.LAZY)
    private Set<Profile> likes = new HashSet<>();

}
