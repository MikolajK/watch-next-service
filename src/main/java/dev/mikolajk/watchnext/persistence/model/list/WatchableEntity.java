package dev.mikolajk.watchnext.persistence.model.list;

import static dev.mikolajk.watchnext.persistence.jpa.ColumnNames.WATCHABLE_ID_COLUMN_NAME;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "watchable")
public class WatchableEntity {

    /**
     * IMDB ID serves as an ID in the database as it's unique.
     */
    @Id
    @Column(name = WATCHABLE_ID_COLUMN_NAME, nullable = false)
    private String imdbId;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "year")
    private String year;

}
