package Toxitter2.annotations;

import Toxitter.Core.remake.dto.output.Transferrable;

public @interface Extracts
{
    public Class<? extends Transferrable> dto();
}