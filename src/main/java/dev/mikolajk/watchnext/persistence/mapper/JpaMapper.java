package dev.mikolajk.watchnext.persistence.mapper;

import dev.mikolajk.watchnext.persistence.model.list.UserWatchableVoteEntity;
import dev.mikolajk.watchnext.persistence.model.list.WatchableEntity;
import dev.mikolajk.watchnext.persistence.model.list.WatchableListEntity;
import dev.mikolajk.watchnext.service.model.list.DetailedWatchableListRepresentation;
import dev.mikolajk.watchnext.service.model.list.SimpleWatchableListRepresentation;
import dev.mikolajk.watchnext.service.model.watchable.DetailedWatchableRepresentation;
import dev.mikolajk.watchnext.service.model.watchable.UserVoteRepresentation;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "cdi")
public interface JpaMapper {

    @Named("toSimpleWatchableListRepresentation")
    @Mapping(source = "listId", target = "id")
    SimpleWatchableListRepresentation toSimpleWatchableListRepresentation(WatchableListEntity list);

    @IterableMapping(qualifiedByName = "toSimpleWatchableListRepresentation")
    List<SimpleWatchableListRepresentation> toSimpleWatchableListRepresentationList(
        List<WatchableListEntity> listEntities);

    @Mapping(source = "listId", target = "id")
    DetailedWatchableListRepresentation toDetailedWatchableListRepresentation(WatchableListEntity list);

    @Named("toDetailedWatchableRepresentation")
    DetailedWatchableRepresentation toDetailedWatchableRepresentation(WatchableEntity watchableIds);

    @IterableMapping(qualifiedByName = "toDetailedWatchableRepresentation")
    List<DetailedWatchableRepresentation> toDetailedWatchableRepresentationList(List<WatchableEntity> watchableIds);

    @Named("toUserVoteRepresentation")
    UserVoteRepresentation toUserVoteRepresentation(UserWatchableVoteEntity watchableVoteEntity);
}
