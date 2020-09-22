package Toxitter2.annotations;

import Toxitter.Core.realtime.TransferrableDataAtom;

public @interface Responds
{
    public Class<? extends TransferrableDataAtom> dto();
}