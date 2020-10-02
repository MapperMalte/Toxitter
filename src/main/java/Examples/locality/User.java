package Examples.locality;

import Avatar.Annotations.core.FetchAt;
import Avatar.Elemental.earth.ReservoirEntity;
import Avatar.Elemental.earth.Persist;

@FetchAt(route="user")
@Persist(primaryKey = "id", tableName = "user")
public class User extends ReservoirEntity
{
}
