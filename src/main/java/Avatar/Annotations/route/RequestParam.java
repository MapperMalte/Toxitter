package Avatar.Annotations.route;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam
{
    String name();
    boolean obligatory() default false;
}
