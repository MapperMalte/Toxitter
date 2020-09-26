package Toxitter2.annotations;

import Toxitter.Core.remake.dto.output.Transferrable;

import java.lang.annotation.Repeatable;

@Repeatable(Expectations.class)
public @interface Expects
{
    public Class<? extends Transferrable> dto();
}