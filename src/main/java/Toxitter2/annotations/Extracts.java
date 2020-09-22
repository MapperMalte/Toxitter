package Toxitter2.annotations;

import Toxitter.Core.realtime.TransferrableDataAtom;

public @interface Extracts
{
    public Class<? extends TransferrableDataAtom> dto();
}