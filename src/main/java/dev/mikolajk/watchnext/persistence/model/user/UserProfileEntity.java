package dev.mikolajk.watchnext.persistence.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import lombok.Getter;

/**
 * Dummy user until authentication is implemented.
 */
@Entity
@Getter
public class UserProfileEntity {

    @Id
    @Column(name = "user_id")
    private String id = "dummy";

    @PrePersist
    public void prePersist() {
        this.id = "dummy";
    }

}
