package Toxitter.Realtime.annotations;

import Toxitter.Realtime.TeleState;

public @interface TeleStateful
{
    public Class<TeleState> messageHub();
    public String name();
}
