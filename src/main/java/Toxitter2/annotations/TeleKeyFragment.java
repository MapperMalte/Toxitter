package Toxitter2.annotations;

import java.lang.annotation.Repeatable;

@Repeatable(TeleKey.class)
public @interface TeleKeyFragment
{
    String paramName();
}
