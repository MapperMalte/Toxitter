package Toxitter2.annotations;

import Toxitter.Core.realtime.TransferrableDataAtom;

public @interface TriggerResponse
{
    public Class<? extends TransferrableDataAtom> dto();
}