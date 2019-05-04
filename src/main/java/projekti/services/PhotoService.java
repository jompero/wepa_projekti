package projekti.services;

import java.io.IOException;
import java.util.List;

import javax.activation.UnsupportedDataTypeException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import projekti.ImageConverter;
import projekti.entities.Photo;
import projekti.entities.Profile;
import projekti.repositories.PhotoRepository;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photos;
    @Autowired
    private ProfileService profiles;

    public void save(MultipartFile file, String description) throws IOException {
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
        photo.setDescription(description);
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
    
    public void likePhoto(Long id) {
            Photo photo = getPhoto(id);
            Profile activeProfile = profiles.getActiveProfile();
            
            if (!photo.getLikes().remove(activeProfile)) {
                    photo.getLikes().add(activeProfile);
            }

            photos.save(photo);
    }
}
