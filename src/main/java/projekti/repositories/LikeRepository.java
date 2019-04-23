package projekti.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import projekti.entities.GenericEntity;
import projekti.entities.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

	public Page<Like> findByTo(GenericEntity to, Pageable pr);

}
