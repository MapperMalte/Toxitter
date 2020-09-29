package Toxitter.Annotations.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PushTo
{
    String route();
    String method();
}
