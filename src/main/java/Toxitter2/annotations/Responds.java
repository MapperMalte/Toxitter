package Toxitter2.annotations;

import Toxitter.Core.realtime.Transferrable;

public @interface Responds
{
    public Class<? extends Transferrable> dto();
}