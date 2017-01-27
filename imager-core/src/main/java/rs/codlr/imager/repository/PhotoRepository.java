package rs.codlr.imager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.codlr.imager.model.Photo;

import java.util.List;

/**
 * @ivan
 */
public interface PhotoRepository extends JpaRepository<Photo, String> {

    List<Photo> findByUserIdOrderByCreationDateDesc(String userId);

}
