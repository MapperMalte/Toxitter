package Avatar.Annotations.security;

import Avatar.Elemental.earth.ReservoirEntity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Owned
{
    public Class<? extends ReservoirEntity> by();
}