package Toxitter.Realtime.annotations;

import Toxitter.Realtime.concepts.TeleState;

public @interface TeleStateful
{
    public Class<TeleState> messageHub();
    public String name();
}
