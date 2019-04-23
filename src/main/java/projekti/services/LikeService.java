package projekti.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projekti.entities.Like;
import projekti.repositories.LikeRepository;

@Service
public class LikeService {
    
    @Autowired
    private LikeRepository likes;
    
    public Like saveLike(Like like) {
        return likes.save(like);
    }
}
