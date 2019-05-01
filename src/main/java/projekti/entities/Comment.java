package projekti.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude={"from", "to", "comments", "likes"})
public class Comment extends GenericEntity {
    
    @ManyToOne
    @Basic(fetch = FetchType.LAZY)
    private Profile from;

    @ManyToOne
    @Basic(fetch = FetchType.LAZY)
    private GenericEntity to;

    private String content;

    @CreationTimestamp
    private Date createdDate;

    @ManyToMany
    @Basic(fetch = FetchType.LAZY)
    private Set<Profile> likes = new HashSet<>();

    @OneToMany(mappedBy = "to")
    @Basic(fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

}
