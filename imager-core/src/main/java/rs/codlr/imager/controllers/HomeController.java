package rs.codlr.imager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rs.codlr.imager.model.Photo;
import rs.codlr.imager.service.PhotoService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ivan
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private PhotoService photoService;

    @Value("${image.repository.url.public}")
    private String repositoryUrlPublic;

    @RequestMapping(method = RequestMethod.GET)
    public String home(HttpServletRequest request, Model model) {
        List<Photo> photos = photoService.getPhotosForUser(request.getRemoteUser());
        model.addAttribute("photos", photos);
        model.addAttribute("repositoryUrlPublic", repositoryUrlPublic);
        return "home";
    }

}
