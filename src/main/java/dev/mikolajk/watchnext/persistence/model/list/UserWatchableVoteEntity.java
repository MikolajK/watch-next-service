package dev.mikolajk.watchnext.persistence.model.list;

import static dev.mikolajk.watchnext.persistence.jpa.ColumnNames.LIST_ID_COLUMN_NAME;
import static dev.mikolajk.watchnext.persistence.jpa.ColumnNames.USER_ID_COLUMN_NAME;
import static dev.mikolajk.watchnext.persistence.jpa.ColumnNames.WATCHABLE_ID_COLUMN_NAME;
import static dev.mikolajk.watchnext.persistence.jpa.Queries.GET_USERS_VOTES_FOR_WATCHABLE_AND_LIST;
import static dev.mikolajk.watchnext.persistence.jpa.Queries.GET_USERS_VOTES_FOR_WATCHABLE_AND_LIST_NAME;

import dev.mikolajk.watchnext.persistence.model.id.WatchableUserAndListIdCompositeKey;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_vote")
@IdClass(WatchableUserAndListIdCompositeKey.class)
@NamedQuery(name = GET_USERS_VOTES_FOR_WATCHABLE_AND_LIST_NAME, query = GET_USERS_VOTES_FOR_WATCHABLE_AND_LIST)
public class UserWatchableVoteEntity {

    @Id
    @Column(name = USER_ID_COLUMN_NAME)
    private String userId;

    @Id
    @Column(name = WATCHABLE_ID_COLUMN_NAME)
    private String watchableId;

    @Id
    @Column(name = LIST_ID_COLUMN_NAME)
    private long listId;

    @Min(0)
    @Max(100)
    @Column(name = "votes")
    private int votes;
}
