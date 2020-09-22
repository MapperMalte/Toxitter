package Toxitter2.annotations;

import Toxitter.Core.realtime.TransferrableDataAtom;

import java.lang.annotation.Repeatable;

@Repeatable(Expectations.class)
public @interface Expects
{
    public Class<? extends TransferrableDataAtom> dto();
}