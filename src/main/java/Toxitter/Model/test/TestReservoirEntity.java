package Toxitter.Model.test;

import Toxitter.Model.ID;
import Toxitter.Model.ReservoirEntity;
import Toxitter.Persistence.annotations.Table;

@Table(primaryKey = "id",tableName = "testreservoir")
public class TestReservoirEntity extends ReservoirEntity<String>
{
    private String id;
    private String name;
    private int powerlevel;

    public String toString()
    {
        return ("Id: "+id+" / name: "+name+" /  powerlevel: "+powerlevel);
    }

    public TestReservoirEntity()
    {
        this.id = ID.makeId();
        this.powerlevel = (int)(Math.random()*10000);
        this.name = ID.makeId();
    }
    public TestReservoirEntity(String name, int powerlevel)
    {
        this.id = ID.makeId();
        this.name = name;
        this.powerlevel = powerlevel;
    }

    @Override
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getPowerlevel() { return powerlevel; }
    public void setPowerlevel(int powerlevel) { this.powerlevel = powerlevel; }
}
