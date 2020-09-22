package Toxitter2.annotations;

import Toxitter.Core.realtime.Transferrable;

public @interface TriggerResponse
{
    public Class<? extends Transferrable> dto();
}