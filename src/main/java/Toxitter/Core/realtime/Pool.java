package Toxitter.Core.realtime;

import Toxitter.Core.User;
import Toxitter.Core.remake.dto.output.Transferrable;
import Toxitter.Model.Many;
import theory.DiamondList;

public class Pool
{
    private static final Many<String, Transferrable> undeliveredPool = new Many<>();
    private static final Many<String,User> subscribersByTelekey = new Many<>();

    private final String telekey = "";

    public void subscribe(User user)
    {
        subscribersByTelekey.put(telekey,user);
    }

    public static void fetchForUser(String userId)
    {
        if ( OnlineStateManager.isUserOnline(userId) )
        {
            DiamondList<Transferrable> open = undeliveredPool.get(userId);
            open.bottom();
            while (!open.isPointerNull()) {
                ToxitterWebsocketHandler.push(userId, open.getCurrent());
                open.next();
            }
        }
    }

    public static void pullForUserIdIfUserOnline(String userId)
    {
        if ( OnlineStateManager.isUserOnline(userId) ) {
            DiamondList<Transferrable> open = undeliveredPool.get(userId);
            open.bottom();
            while (!open.isPointerNull()) {
                ToxitterWebsocketHandler.push(userId, open.getCurrent());
                open.removeCurrent();
            }
        }
    }

    public void pull()
    {
        DiamondList<User> subscribers = subscribersByTelekey.get(telekey);
        subscribers.bottom();
        while (!subscribers.isPointerNull())
        {
            pullForUserIdIfUserOnline(subscribers.getCurrent().userId);
            subscribers.next();
        }
    }
    /**
     * Pushes the data to all users that subscribed to this pool
     * @param tda
     */
    public void push(Transferrable tda)
    {
        DiamondList<User> subscribers = subscribersByTelekey.get(telekey);
        subscribers.bottom();
        while (!subscribers.isPointerNull())
        {
            String userId = subscribers.getCurrent().userId;

            if ( OnlineStateManager.isUserOnline(userId) ) {
                ToxitterWebsocketHandler.push(userId, tda);
            } else
            {
                undeliveredPool.put(userId,tda);
            }

            subscribers.next();
        }
    }
}
