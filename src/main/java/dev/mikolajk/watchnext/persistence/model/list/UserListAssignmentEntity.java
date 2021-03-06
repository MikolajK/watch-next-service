package dev.mikolajk.watchnext.persistence.model.list;

import static dev.mikolajk.watchnext.persistence.jpa.ColumnNames.LIST_ID_COLUMN_NAME;
import static dev.mikolajk.watchnext.persistence.jpa.ColumnNames.USER_ID_COLUMN_NAME;

import dev.mikolajk.watchnext.persistence.model.id.UserIdAndListIdCompositeKey;
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
@Table(name = "user_list_assignment")
@IdClass(UserIdAndListIdCompositeKey.class)
public class UserListAssignmentEntity {

    @Id
    @Column(name = USER_ID_COLUMN_NAME)
    private String userId;

    @Id
    @Column(name = LIST_ID_COLUMN_NAME)
    private long listId;

}
