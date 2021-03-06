package dev.mikolajk.watchnext.persistence.model.list;

import static dev.mikolajk.watchnext.persistence.jpa.ColumnNames.LIST_ID_COLUMN_NAME;
import static dev.mikolajk.watchnext.persistence.jpa.ColumnNames.LIST_NAME_COLUMN_NAME;
import static dev.mikolajk.watchnext.persistence.jpa.Queries.GET_LISTS_FOR_USER_QUERY;
import static dev.mikolajk.watchnext.persistence.jpa.Queries.GET_LISTS_FOR_USER_QUERY_NAME;
import static dev.mikolajk.watchnext.persistence.jpa.Queries.GET_LIST_BY_ID_AND_USER_ID;
import static dev.mikolajk.watchnext.persistence.jpa.Queries.GET_LIST_BY_ID_AND_USER_ID_NAME;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "watchable_list")
@NamedQuery(name = GET_LISTS_FOR_USER_QUERY_NAME, query = GET_LISTS_FOR_USER_QUERY)
@NamedQuery(name = GET_LIST_BY_ID_AND_USER_ID_NAME, query = GET_LIST_BY_ID_AND_USER_ID)
public class WatchableListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "watchable_list_sequence")
    @Column(name = LIST_ID_COLUMN_NAME)
    private long listId;

    @Column(name = LIST_NAME_COLUMN_NAME)
    private String name;

    @OneToMany(mappedBy = "listId")
    private List<WatchableListAssignmentEntity> watchables;

    @OneToMany(mappedBy = "listId")
    private List<UserListAssignmentEntity> users;

}
