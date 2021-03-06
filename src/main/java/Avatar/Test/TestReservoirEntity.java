package Avatar.Test;

import Avatar.Elemental.earth.ID;
import Avatar.Elemental.earth.ReservoirEntity;
import Avatar.Elemental.earth.Persist;

@Persist(primaryKey = "id",tableName = "testreservoir")
public class TestReservoirEntity extends ReservoirEntity
{
    private String name;
    private int powerlevel;

    public String toString()
    {
        return ("Id: "+id+" / name: "+name+" /  powerlevel: "+powerlevel);
    }

    public TestReservoirEntity()
    {
        this.id = new ID();
        this.powerlevel = (int)(Math.random()*10000);
        this.name = ID.makeId();
    }
    public TestReservoirEntity(String name, int powerlevel)
    {
        this.id = new ID();
        this.name = name;
        this.powerlevel = powerlevel;
    }
    public ID getId()
    {
        return id;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getPowerlevel() { return powerlevel; }
    public void setPowerlevel(int powerlevel) { this.powerlevel = powerlevel; }
}
