package projekti.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import projekti.entities.Comment;
import projekti.entities.Photo;
import projekti.entities.Profile;

public interface CommentsRepository extends JpaRepository<Comment, Long> {

	public Page<Comment> findByProfile(Profile profile, Pageable pr);

	public Page<Comment> findByPhoto(Photo photo, Pageable pr);

}
