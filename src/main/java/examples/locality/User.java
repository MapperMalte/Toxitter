package examples.locality;

import Toxitter.Annotations.core.FetchAt;
import Toxitter.Core.Elemental.earth.ReservoirEntity;
import Toxitter.Core.Elemental.earth.Persist;

@FetchAt(route="user")
@Persist(primaryKey = "id", tableName = "user")
public class User extends ReservoirEntity
{
}
