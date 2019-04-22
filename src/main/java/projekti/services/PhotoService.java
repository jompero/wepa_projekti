package projekti.services;

import java.io.IOException;
import java.util.List;

import javax.activation.UnsupportedDataTypeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import projekti.ImageConverter;
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
        comment.setPhoto(to);
        comments.saveComment(comment);
    }

    public void save(MultipartFile file) throws IOException {
        // The file browser allows only images to be selected, 
        // however, we need to catch the case where they bypass this.
        if (photos.findByProfile(profiles.getActiveProfile()).size() >= 10) {
            throw new IOException("Max album size reached.");
        }
        String type = file.getContentType();
        if (!type.startsWith("image/")) {
            throw new UnsupportedDataTypeException(String.format(
                "Only image types are supported; file is type {%s}.", type));
        }

        Photo photo = new Photo();

        ImageConverter imageConverter = new ImageConverter();
        byte[] image = imageConverter.convertForWeb(file.getBytes());
        photo.setContent(image);

        photo.setName(file.getOriginalFilename());
        photo.setSize((long) image.length);
        photo.setContentType("image/jpg");

        photo.setProfile(profiles.getActiveProfile());
        photos.save(photo);
	}

	public Photo getPhoto(Long id) {
		return photos.getOne(id);
	}

	public List<Photo> getPhotos(String profileName) {
		return photos.findByProfile(profiles.getProfile(profileName));
	}

	public byte[] getContentOf(Long id) {
		return getPhoto(id).getContent();
	}
}
