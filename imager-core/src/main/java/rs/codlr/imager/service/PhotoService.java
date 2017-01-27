package rs.codlr.imager.service;

import org.springframework.web.multipart.MultipartFile;
import rs.codlr.imager.model.Photo;

import java.io.IOException;
import java.util.List;

/**
 * @ivan
 */
public interface PhotoService {

    Photo getPhoto(String id);

    void deletePhoto(String id, String userId);

    Photo uploadPhoto(MultipartFile multipartFile, String userId) throws IOException;

    List<Photo> getPhotosForUser(String userId);

}
