package Toxitter2.dto;

import Toxitter.Core.User;
import Toxitter2.annotations.Injected;

public class ChatMessageInputAtom extends InputAtom
{
    @Injected(bykey = "userId")
    User from;
    String message;
}
