package dev.mikolajk.watchnext.persistence.model.list;

import static dev.mikolajk.watchnext.persistence.jpa.ColumnNames.WATCHABLE_ID_COLUMN_NAME;
import static dev.mikolajk.watchnext.persistence.jpa.Queries.GET_WATCHABLES_BY_LIST_OF_IDS;
import static dev.mikolajk.watchnext.persistence.jpa.Queries.GET_WATCHABLES_BY_LIST_OF_IDS_NAME;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "watchable")
@NamedQuery(name = GET_WATCHABLES_BY_LIST_OF_IDS_NAME, query = GET_WATCHABLES_BY_LIST_OF_IDS)
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

    @Column(name = "plot_summary")
    private String plotSummary;

    @Column(name = "runtime")
    private String runtime;

    @Column(name = "genre")
    private String genre;
}
