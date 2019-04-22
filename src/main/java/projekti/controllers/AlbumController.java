package projekti.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

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
import projekti.services.PhotoService;
import projekti.services.ProfileService;

@Controller
public class AlbumController {

    @Autowired
    private PhotoService photos;

    @Autowired
    private ProfileService profiles;

    @GetMapping("/profile/{profileName}/album")
    public String getAlbum(@PathVariable String profileName, Model model) {
        List<Photo> ps = photos.getPhotos(profileName);
        model.addAttribute("profile", profiles.getProfile(profileName));
        model.addAttribute("photos", ps);
        return "album";
    }

    @GetMapping("/profile/{profileName}/album/{id}")
    public String getPhoto(@ModelAttribute Comment comment, @PathVariable Long id, @PathVariable String profileName, Model model) {
        Photo photo = photos.getPhoto(id);
        model.addAttribute("profile", profiles.getProfile(profileName));
        model.addAttribute("photo", photo);
        model.addAttribute("id", id);
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
    public String uploadPhoto(@RequestParam("file") MultipartFile file) {
        String profileName = profiles.getActiveProfile().getProfileName();
        String redirect = String.format("redirect:/profile/%s/album/", profileName);
        
        try {
			photos.save(file);
		} catch (IOException e) {
			return redirect + "?fileerror=true";
        }
        return redirect;
        
    }

    @PostMapping("/profile/{profileName}/album/{id}/comment")
    public String postComment(@Valid @ModelAttribute Comment comment, 
            @PathVariable String profileName,
            @PathVariable Long id) {
        Photo photo = photos.getPhoto(id);
        photos.comment(photo, comment);
        return String.format("redirect:/profile/%s/album/%d/", profileName, id);
    }
}
