package projekti.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Like extends AbstractPersistable<Long> {
    
    @ManyToOne
    Profile from;

    @ManyToOne
    GenericEntity to;

}
