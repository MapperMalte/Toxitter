package Toxitter.Security.annotations;

import Toxitter.Model.ReservoirEntity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Owned
{
    public Class<? extends ReservoirEntity<String>> by();
}