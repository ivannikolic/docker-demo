package rs.codlr.imager.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import rs.codlr.imager.model.Photo;
import rs.codlr.imager.service.PhotoService;

import java.io.IOException;
import java.util.List;

/**
 * @ivan
 */
@Controller
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Value("${image.repository.url.public}")
    private String repositoryUrlPublic;

    @RequestMapping(value = "/{photoId}", method = RequestMethod.GET)
    public String get(@PathVariable String photoId, Model model) {
        Photo photo = photoService.getPhoto(photoId);
        model.addAttribute("photo", photo);
        model.addAttribute("repositoryUrlPublic", repositoryUrlPublic);
        return "photo";
    }

    @RequestMapping(value = "/{photoId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable String photoId, Model model) {
        photoService.deletePhoto(photoId, httpServletRequest.getRemoteUser());
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String post(@RequestParam("file") MultipartFile file) throws IOException {
        Photo photo = photoService.uploadPhoto(file, httpServletRequest.getRemoteUser());
        return "redirect:photo/" + photo.getId();
    }

}
