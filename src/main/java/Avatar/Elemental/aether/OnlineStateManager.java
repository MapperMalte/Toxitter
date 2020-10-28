package Avatar.Elemental.aether;

import Avatar.Boxfresh.routes.User;
import Avatar.Elemental.earth.ReservoirEntityList;
import Avatar.Elemental.wind.artifacts.DiamondList;

public class OnlineStateManager
{
    private static ReservoirEntityList<User> onlineUsers = new ReservoirEntityList<>();
    private static DiamondList<OnlineStateChangedEvent> eventDiamondList = new DiamondList<>();

    public static void comeOnline(User user)
    {
        onlineUsers.addOnTop(user);
        eventDiamondList.bottom();
        while (!eventDiamondList.isPointerNull())
        {
            synchronized (OnlineStateManager.class)
            {
                eventDiamondList.pushBreakpoint();
                eventDiamondList.getCurrent().onComeOnline(user);
                eventDiamondList.popBreakpoint();
            }
            eventDiamondList.next();
        }
    }

    public static void addOnlineStateChangedEvent(OnlineStateChangedEvent event)
    {
        eventDiamondList.addOnTop(event);
    }

    public static boolean isUserOnline(User user)
    {
        return onlineUsers.contains(user);
    }

    public static void goOffline(User user)
    {
        onlineUsers.removeByKey(user.getId());
        eventDiamondList.bottom();
        while (!eventDiamondList.isPointerNull())
        {
            synchronized (OnlineStateManager.class)
            {
                eventDiamondList.pushBreakpoint();
                eventDiamondList.getCurrent().onGoOffline(user);
                eventDiamondList.popBreakpoint();
            }
            eventDiamondList.next();
        }
    }
}
