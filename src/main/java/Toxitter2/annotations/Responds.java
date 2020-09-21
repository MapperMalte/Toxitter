package Toxitter2.annotations;

import Toxitter2.dto.InputAtom;

public @interface Responds
{
    public Class<? extends InputAtom> dto();
}