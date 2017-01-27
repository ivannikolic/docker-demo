package rs.codlr.imager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import rs.codlr.imager.model.Photo;
import rs.codlr.imager.repository.PhotoRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @ivan
 */
@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${image.repository.url}")
    private String imageRepositoryUrl;

    private List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png");

    @Override
    public Photo getPhoto(String id) {
        return photoRepository.findOne(id);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "userPhotos", key = "#userId")
    public void deletePhoto(String id, String userId) {
        photoRepository.delete(id);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "userPhotos", key = "#userId")
    public Photo uploadPhoto(MultipartFile multipartFile, String userId) throws IOException {
        String fileExtension = getFileExtension(multipartFile.getOriginalFilename()).toLowerCase();
        if (!allowedExtensions.contains(fileExtension)) {
            throw new RuntimeException("Extension not allowed " + fileExtension);
        }
        Photo photo = photoRepository.save(new Photo(multipartFile.getOriginalFilename(), fileExtension, userId));
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        final String filename = photo.getId() + "." + fileExtension;
        map.add("name", filename);
        map.add("filename", filename);
        ByteArrayResource contentsAsResource = new ByteArrayResource(multipartFile.getBytes()){
            @Override
            public String getFilename(){
                return filename;
            }
        };
        map.add("file", contentsAsResource);
        restTemplate.postForObject(imageRepositoryUrl + "/upload", map, String.class);
        return photo;
    }

    @Override
    @Cacheable(cacheNames = "userPhotos", key = "#userId")
    public List<Photo> getPhotosForUser(String userId) {
        return photoRepository.findByUserIdOrderByCreationDateDesc(userId);
    }

    private String getFileExtension(String fileName){
        String extension = "";
        int i = fileName.lastIndexOf('.');
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
        if (i > p) {
            extension = fileName.substring(i+1);
        }
        return extension;
    }

}
