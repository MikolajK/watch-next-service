package dev.mikolajk.watchnext.persistence.model.list;

import static dev.mikolajk.watchnext.persistence.jpa.ColumnNames.LIST_ID_COLUMN_NAME;
import static dev.mikolajk.watchnext.persistence.jpa.ColumnNames.WATCHABLE_ID_COLUMN_NAME;

import dev.mikolajk.watchnext.persistence.model.id.WatchableAndListIdCompositeKey;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "watchable_list_assignment")
@IdClass(WatchableAndListIdCompositeKey.class)
public class WatchableListAssignmentEntity {

    @Id
    @Column(name = WATCHABLE_ID_COLUMN_NAME)
    private String watchableId;

    @Id
    @Column(name = LIST_ID_COLUMN_NAME)
    private long listId;

}
