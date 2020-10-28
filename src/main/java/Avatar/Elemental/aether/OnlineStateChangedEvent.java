package Avatar.Elemental.aether;

import Avatar.Boxfresh.routes.User;

public interface OnlineStateChangedEvent
{
    public void onComeOnline(User user);
    public void onGoOffline(User user);
}
