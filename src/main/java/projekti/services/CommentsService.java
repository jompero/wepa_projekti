package projekti.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import projekti.entities.Comment;
import projekti.entities.GenericEntity;
import projekti.entities.Photo;
import projekti.entities.Profile;
import projekti.repositories.CommentsRepository;

@Service
public class CommentsService {
        
        @Autowired
        private CommentsRepository comments;
        @Autowired
        private ProfileService profiles;

        public Comment saveComment(Comment comment) {
                return comments.save(comment);
        }

        public Page<Comment> getMessages(GenericEntity to, int page) {
                Pageable pr = PageRequest.of(page, 25, Sort.by("createdDate").descending());
                return comments.findByTo(to, pr);
        }

        public List<Comment> getComments(GenericEntity to) {
                Pageable pr = PageRequest.of(0, 10, Sort.by("createdDate").descending());
                Page<Comment> page = comments.findByTo(to, pr);
                List<Comment> comments = new ArrayList<>(page.getContent());
                Collections.reverse(comments);
                return comments;
        }

        public Comment getComment(Long id) {
                return comments.getOne(id);
        }

        public void likeComment(Long id) {
                Comment comment = getComment(id);
                Profile activeProfile = profiles.getActiveProfile();
                if (!comment.getLikes().remove(activeProfile)) {
                        comment.getLikes().add(activeProfile);
                }
                comments.save(comment);
        }

        public void commentComment(String content, Long to) {
                if (content.isEmpty()) return;
                Comment newComment = new Comment();
                newComment.setFrom(profiles.getActiveProfile());
                newComment.setTo(comments.getOne(to));
                newComment.setContent(content);
                comments.save(newComment);
        }

        public void commentPhoto(Photo photo, String content) {
                if (content.isEmpty()) return;
                Comment newComment = new Comment();
                newComment.setFrom(profiles.getActiveProfile());
                newComment.setTo(photo);
                newComment.setContent(content);
                comments.save(newComment);    
        }
}
