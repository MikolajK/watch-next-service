package dev.mikolajk.watchnext.persistence.jpa;

public class Queries {

    public static final String GET_LISTS_FOR_USER_QUERY_NAME = "getListsForUser";
    public static final String GET_LIST_BY_ID_AND_USER_ID_NAME = "getListByIdAndUserId";


    public static final String GET_LISTS_FOR_USER_QUERY = "SELECT DISTINCT list "
        + "FROM WatchableListEntity list "
        + "JOIN list.users users "
        + "WHERE users.userId = :userId";

    public static final String GET_LIST_BY_ID_AND_USER_ID = "SELECT DISTINCT list "
        + "FROM WatchableListEntity list "
        + "JOIN list.users users "
        + "WHERE users.userId = :userId "
        + "AND list.listId = :listId ";

}
