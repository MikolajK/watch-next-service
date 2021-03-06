package dev.mikolajk.watchnext.persistence.mapper;

import dev.mikolajk.watchnext.persistence.model.list.WatchableListEntity;
import dev.mikolajk.watchnext.service.model.list.DetailedWatchableListRepresentation;
import dev.mikolajk.watchnext.service.model.list.SimpleWatchableListRepresentation;
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
    List<SimpleWatchableListRepresentation> toSimpleWatchableListRepresentationList(List<WatchableListEntity> listEntities);

    @Mapping(source = "listId", target = "id")
    DetailedWatchableListRepresentation toDetailedWatchableListRepresentation(WatchableListEntity list);
}
