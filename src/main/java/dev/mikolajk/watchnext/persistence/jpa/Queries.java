package dev.mikolajk.watchnext.persistence.jpa;

public class Queries {

    public static final String GET_LISTS_FOR_USER_QUERY_NAME = "getListsForUser";
    public static final String GET_LIST_BY_ID_AND_USER_ID_NAME = "getListByIdAndUserId";
    public static final String GET_WATCHABLES_BY_LIST_OF_IDS_NAME = "getWatchablesByListOfIds";
    public static final String GET_USERS_VOTES_FOR_WATCHABLE_AND_LIST_NAME = "getUsersVotesForWatchableAndList";


    public static final String GET_LISTS_FOR_USER_QUERY = "SELECT DISTINCT list "
        + "FROM WatchableListEntity list "
        + "JOIN list.users users "
        + "WHERE users.userId = :userId";

    public static final String GET_LIST_BY_ID_AND_USER_ID = "SELECT DISTINCT list "
        + "FROM WatchableListEntity list "
        + "JOIN list.users users "
        + "WHERE users.userId = :userId "
        + "AND list.listId = :listId ";

    public static final String GET_WATCHABLES_BY_LIST_OF_IDS = "SELECT DISTINCT watchable "
        + "FROM WatchableEntity watchable "
        + "WHERE watchable.id IN :ids";

    public static final String GET_USERS_VOTES_FOR_WATCHABLE_AND_LIST = "SELECT DISTINCT vote "
        + "FROM UserWatchableVoteEntity vote "
        + "WHERE vote.listId = :listId "
        + "AND vote.watchableId = :watchableId";
}
