package Examples.locality;

import Toxitter.Annotations.core.FetchAt;
import Toxitter.Elemental.earth.ReservoirEntity;
import Toxitter.Elemental.earth.Persist;

@FetchAt(route="user")
@Persist(primaryKey = "id", tableName = "user")
public class User extends ReservoirEntity
{
}
