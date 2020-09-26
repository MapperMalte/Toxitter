package Toxitter2.annotations;

import Toxitter.Core.remake.dto.output.Transferrable;

public @interface TriggerResponse
{
    public Class<? extends Transferrable> dto();
}