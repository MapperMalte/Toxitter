package examples.locality;

import Toxitter.Boxfresh.Email;
import Toxitter.Core.annotations.FetchAt;
import Toxitter.Model.ReservoirEntity;
import Toxitter.Persistence.annotations.Table;

@FetchAt(route="post")
@Table(primaryKey = "id", tableName = "user")
public class User extends ReservoirEntity<String>
{
    private String id;
    private Email mail;

    @Override
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}
