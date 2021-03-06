package Avatar.Elemental.water;

import Avatar.Elemental.earth.Persist;
import Avatar.Elemental.wind.artifacts.DiamondList;

public class ReservoirEntityDataPresenter
{
    private DataAccessToReservoirEntity dataAccessToReservoirEntityBeingPersisted;

    public Persist getTable()
    {
        return dataAccessToReservoirEntityBeingPersisted.getTable();
    }

    public ReservoirEntityDataPresenter(DataAccessToReservoirEntity o)
    {
        this.dataAccessToReservoirEntityBeingPersisted = o;
    }

    public String escape(String in)
    {
        return "`"+in+"`";
    }

    public String escapeValue(String field, String value, TypeTransformer tf)
    {
        return tf.escape(value,dataAccessToReservoirEntityBeingPersisted.getType(field));
    }

    public DataAccessToReservoirEntity getAccess()
    {
        return dataAccessToReservoirEntityBeingPersisted;
    }

    public String getFieldStringSeparatedBy(String separatedByWhat, TypeTransformer t)
    {
        DiamondList<DataAccessToReservoirEntity.DataAccessField> paramList = dataAccessToReservoirEntityBeingPersisted.getAllFields();
        paramList.bottom();
        String list = "";
        while(!paramList.isPointerAtTop())
        {
            list += escape(paramList.getCurrent().fieldName) + separatedByWhat;
            paramList.next();
        }
        list += escape(paramList.getCurrent().fieldName);
        return list;
    }

    public String getValueStringSeparatedBy(String separatedByWhat, TypeTransformer t)
    {
        DiamondList<DataAccessToReservoirEntity.DataAccessField> paramList = dataAccessToReservoirEntityBeingPersisted.getAllFields();
        paramList.bottom();
        String list = "";
        while(!paramList.isPointerAtTop())
        {
            list += t.escape(dataAccessToReservoirEntityBeingPersisted.get(paramList.getCurrent().fieldName).toString(),paramList.getCurrent().type)+separatedByWhat;
            paramList.next();
        }
        list += t.escape(dataAccessToReservoirEntityBeingPersisted.get(paramList.getCurrent().fieldName).toString(),paramList.getCurrent().type);
        return list;
    }

    public String getFieldEqualsValueSeparatedBy(String separatedByWhat, TypeTransformer t)
    {
        DiamondList<DataAccessToReservoirEntity.DataAccessField> paramList = dataAccessToReservoirEntityBeingPersisted.getAllFields();
        paramList.bottom();
        String list = "";
        while(!paramList.isPointerAtTop())
        {
            list += escape(paramList.getCurrent().fieldName)+" = "+
                    t.escape(dataAccessToReservoirEntityBeingPersisted.get(
                        paramList.getCurrent().fieldName).toString(),
                        paramList.getCurrent().type)+
                    separatedByWhat;
            paramList.next();
        }
        list += escape(paramList.getCurrent().fieldName)+" = "+
                t.escape(dataAccessToReservoirEntityBeingPersisted.get(
                        paramList.getCurrent().fieldName).toString(),
                        paramList.getCurrent().type);
        return list;
    }

    public String getFieldsSeparatedBy(String separatedByWhat, TypeTransformer t)
    {
        DiamondList<DataAccessToReservoirEntity.DataAccessField> paramList = dataAccessToReservoirEntityBeingPersisted.getAllFields();
        paramList.bottom();
        String list = "";
        while(!paramList.isPointerAtTop())
        {
            list += escape(paramList.getCurrent().fieldName)+ " "
                    +t.transform(dataAccessToReservoirEntityBeingPersisted.getType(paramList.getCurrent().fieldName))
                    +separatedByWhat;
            paramList.next();
        }
        list += escape(paramList.getCurrent().fieldName)+ " "
                +t.transform(dataAccessToReservoirEntityBeingPersisted.getType(paramList.getCurrent().fieldName));
        return list;
    }

    public String getAsHumanReadableJSON()
    {
        return null;
    }
}
