package projekti.entities;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.springframework.data.jpa.domain.AbstractPersistable;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class GenericEntity extends AbstractPersistable<Long> {

}