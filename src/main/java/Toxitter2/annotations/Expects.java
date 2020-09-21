package Toxitter2.annotations;

import Toxitter2.dto.InputAtom;

import java.lang.annotation.Repeatable;

@Repeatable(Expectations.class)
public @interface Expects
{
    public Class<? extends InputAtom> dto();
}