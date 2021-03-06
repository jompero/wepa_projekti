package projekti.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import projekti.entities.Comment;
import projekti.entities.GenericEntity;

public interface CommentsRepository extends JpaRepository<Comment, Long> {

	public Page<Comment> findByTo(GenericEntity to, Pageable pr);

}
