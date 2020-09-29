package examples.locality;

import Toxitter.Annotations.core.FetchAt;
import Toxitter.Model.elemental.matter.ReservoirEntity;
import Toxitter.Annotations.persistence.Persist;

@FetchAt(route="user")
@Persist(primaryKey = "id", tableName = "user")
public class User extends ReservoirEntity
{
}
