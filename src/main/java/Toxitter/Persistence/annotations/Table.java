package Toxitter.Persistence.annotations;

import Toxitter.Persistence.DataAccessToReservoirEntityBeingPersisted;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Table
{
    public String primaryKey();
    public String tableName();
}
