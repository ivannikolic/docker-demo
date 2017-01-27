package rs.codlr.imager.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @ivan
 */
@Entity
public class Photo implements Serializable {

    @Id
    private String id;
    private String originalName;
    private String userId;
    private Date creationDate;
    private String extension;

    Photo() {
    }

    public Photo(String originalName, String extension, String userId) {
        this.id = UUID.randomUUID().toString();
        this.originalName = originalName;
        this.extension = extension;
        this.creationDate = new Date();
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getExtension() {
        return extension;
    }

    public String getPhotoNameWithExtension(){
        return id + "." + extension;
    }

    public String getUserId() {
        return userId;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
