package projekti.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import projekti.services.PhotoService;

@Controller
public class AlbumController {

    @Autowired
    private PhotoService photos;

    @GetMapping(path = "profile/{profileName}/album/{id}", produces = "image/jpg")
    @ResponseBody
    public byte[] get(@PathVariable Long id) {
        return photos.getContent(id);
    }

    @PostMapping("/album")
    public String uploadPhoto(@RequestParam("file") MultipartFile file) {
        System.out.println(file.getSize());
        System.out.println(file.getContentType());

        try {
			photos.save(file);
		} catch (IOException e) {
			return "redirect:/settings?fileerror=true";
		}
        return "redirect:/settings";
    }
}
