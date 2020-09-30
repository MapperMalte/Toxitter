package Toxitter.Elemental.earth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Persist
{
    public String primaryKey();
    public String tableName();
}
