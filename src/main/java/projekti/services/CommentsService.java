package projekti.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import projekti.entities.Comment;
import projekti.entities.Photo;
import projekti.entities.Profile;
import projekti.repositories.CommentsRepository;

@Service
public class CommentsService {
    
    @Autowired
    private CommentsRepository comments;
    
    public Comment saveComment(Comment comment) {
        return comments.save(comment);
    }

	public Page<Comment> getPage(Profile profile, int page) {
        Pageable pr = PageRequest.of(page, 10, Sort.by("createdDate").descending());
        return comments.findByProfile(profile, pr);
	}

	public Page<Comment> getPage(Photo photo, int page) {
        Pageable pr = PageRequest.of(page, 10, Sort.by("createdDate").descending());
        return comments.findByPhoto(photo, pr);
	}

}
