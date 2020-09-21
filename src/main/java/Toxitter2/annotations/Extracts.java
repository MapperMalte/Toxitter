package Toxitter2.annotations;

import Toxitter2.dto.InputAtom;

public @interface Extracts
{
    public Class<? extends InputAtom> dto();
}