package projekti.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import projekti.entities.Comment;

public interface CommentsRepository extends JpaRepository<Comment, Long> {
    
}
