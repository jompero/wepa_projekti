package projekti.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projekti.entities.Comment;
import projekti.entities.Profile;
import projekti.repositories.CommentsRepository;
import projekti.repositories.ProfileRepository;

@Service
public class CommentsService {
    
    @Autowired
    private CommentsRepository comments;
    
    public Comment saveComment(Comment comment) {
        return comments.save(comment);
    }

}
