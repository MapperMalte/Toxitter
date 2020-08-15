package Toxitter.Persistence;

import Toxitter.Persistence.annotations.Table;
import theory.DiamondList;

public class ReservoirEntityDataPresenter
{
    private DataAccessToReservoirEntity dataAccessToReservoirEntityBeingPersisted;

    public Table getTable()
    {
        return dataAccessToReservoirEntityBeingPersisted.getTable();
    }

    public ReservoirEntityDataPresenter(DataAccessToReservoirEntity o)
    {
        this.dataAccessToReservoirEntityBeingPersisted = o;
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
            list += paramList.getCurrent().fieldName + separatedByWhat;
            paramList.next();
        }
        list += paramList.getCurrent().fieldName;
        return list;
    }

    public String getValueStringSeparatedBy(String separatedByWhat, TypeTransformer t)
    {
        DiamondList<DataAccessToReservoirEntity.DataAccessField> paramList = dataAccessToReservoirEntityBeingPersisted.getAllFields();
        paramList.bottom();
        String list = "";
        while(!paramList.isPointerAtTop())
        {
            list += "'"+dataAccessToReservoirEntityBeingPersisted.get(paramList.getCurrent().fieldName)+"'"+separatedByWhat;
            paramList.next();
        }
        list += "'"+dataAccessToReservoirEntityBeingPersisted.get(paramList.getCurrent().fieldName)+"'";
        return list;
    }

    public String getAsHumanReadableJSON()
    {
        return null;
    }
}
