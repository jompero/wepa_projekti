package projekti.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import projekti.entities.Comment;
import projekti.entities.Photo;
import projekti.entities.Profile;
import projekti.services.CommentsService;
import projekti.services.PhotoService;
import projekti.services.ProfileService;

@Controller
public class AlbumController {

    @Autowired
    private PhotoService photos;

    @Autowired
    private CommentsService comments;

    @Autowired
    private ProfileService profiles;

    @GetMapping("/profile/{profileName}/album")
    public String getAlbum(@ModelAttribute Photo phoro, @PathVariable String profileName, Model model) {
        List<Photo> ps = photos.getPhotos(profileName);
        model.addAttribute("activeProfile", profiles.getActiveProfile());
        model.addAttribute("profile", profiles.getProfile(profileName));
        model.addAttribute("photos", ps);
        return "album";
    }

    @GetMapping("/profile/{profileName}/album/{id}")
    public String getPhoto(@ModelAttribute Comment comment, @PathVariable Long id, @PathVariable String profileName, Model model) {
        Photo photo = photos.getPhoto(id);
        Profile profile = profiles.getProfile(profileName);
        model.addAttribute("activeProfile", profiles.getActiveProfile());
        model.addAttribute("profile", profile);
        model.addAttribute("photo", photo);
        model.addAttribute("comments", comments.getComments(photo));
        return "photo";
    }

    @GetMapping("/photos/{id}/download")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long id) {
        Photo photo = photos.getPhoto(id);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(photo.getContentType()));
        headers.setContentLength(photo.getSize());
        headers.add("Content-Disposition", "attachment; filename=" + photo.getName());

        return new ResponseEntity<>(photo.getContent(), headers, HttpStatus.CREATED);
    }

    @GetMapping(path = "/photos/{id}", produces = "image/jpeg")
    @ResponseBody
    public byte[] get(@PathVariable Long id) {
        return photos.getContentOf(id);
    }

    @PostMapping("/photos")
    public String uploadPhoto(@RequestParam("file") MultipartFile file, @RequestParam String description) {
        String profileName = profiles.getActiveProfile().getProfileName();
        String redirect = String.format("redirect:/profile/%s/album/", profileName);
        
        try {
			photos.save(file, description);
		} catch (IOException e) {
			return redirect + "?fileerror=true";
        }
        return redirect;
        
    }

    @PostMapping("/profile/{profileName}/album/{id}/comment")
    public String postComment(@RequestParam String content,
            @PathVariable String profileName,
            @PathVariable Long id,
            Model model) {

        Photo photo = photos.getPhoto(id);
        comments.commentPhoto(photo, content);
        return String.format("redirect:/profile/%s/album/%d/", profileName, id);
    }

    @PostMapping("/profile/{profileName}/album/{photoId}/comment/{id}/like")
    public String likeComment(@PathVariable String profileName,
            @PathVariable Long photoId,
            @PathVariable Long id) {
        comments.likeComment(id);
        return String.format("redirect:/profile/%s/album/%d/", profileName, photoId);
    }

    @PostMapping("/profile/{profileName}/album/{id}/like")
    public String likePhoto(@PathVariable String profileName,
            @PathVariable Long id) {
        photos.likePhoto(id);
        return String.format("redirect:/profile/%s/album/%d/", profileName, id);
    }
}
