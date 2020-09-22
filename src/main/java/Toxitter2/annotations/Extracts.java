package Toxitter2.annotations;

import Toxitter.Core.realtime.Transferrable;

public @interface Extracts
{
    public Class<? extends Transferrable> dto();
}