package projekti.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import projekti.entities.Comment;
import projekti.entities.Photo;
import projekti.repositories.PhotoRepository;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photos;
    @Autowired
    private ProfileService profiles;
    @Autowired
    private CommentsService comments;

    public void comment(Photo to, Comment comment) {
        comment.setFrom(profiles.getActiveProfile());
        to.getComments().add(comments.saveComment(comment));
        photos.save(to);
    }

    public void save(MultipartFile file) throws IOException {
        Photo photo = new Photo();
        photo.setContent(file.getBytes());
        photos.save(photo);
	}

	public byte[] getContent(Long id) {
		return photos.getOne(id).getContent();
	}
}
