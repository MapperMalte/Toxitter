package Toxitter.Core.Elemental.wind;

import Toxitter.Boxfresh.routes.User;
import Toxitter.Core.Elemental.aether.ToxitterWebsocketHandler;
import Toxitter.Core.Elemental.water.OUTPUT;
import Toxitter.Boxfresh.state.OnlineStateManager;
import Toxitter.Core.Elemental.earth.ID;
import Toxitter.Core.Elemental.earth.Relation;
import Toxitter.Core.Elemental.earth.ReservoirEntity;

public class Pool extends ReservoirEntity
{
    private static final Relation<User, OUTPUT> undeliveredPool = new Relation<>();
    private static final Relation<Pool, User> subscribersByPool = new Relation<>();

    private final ID telekey = new ID();

    public void subscribe(User user)
    {
        subscribersByPool.put(this,user);
    }

    public static void fetchForUser(User user)
    {
        if ( OnlineStateManager.isUserOnline(user.userId) )
        {
            DiamondList<OUTPUT> open = undeliveredPool.forwardGet(user);
            open.bottom();
            while (!open.isPointerNull()) {
                ToxitterWebsocketHandler.push(user.userId, open.getCurrent());
                open.next();
            }
        }
    }

    public static void pullForUserIdIfUserOnline(User user)
    {
        if ( OnlineStateManager.isUserOnline(user.userId) ) {
            DiamondList<OUTPUT> open = undeliveredPool.forwardGet(user);
            open.bottom();
            while (!open.isPointerNull()) {
                ToxitterWebsocketHandler.push(user.userId, open.getCurrent());
                open.removeCurrent();
            }
        }
    }

    public void pull()
    {
        DiamondList<User> subscribers = subscribersByPool.forwardGet(this);
        subscribers.bottom();
        while (!subscribers.isPointerNull())
        {
            pullForUserIdIfUserOnline(subscribers.getCurrent());
            subscribers.next();
        }
    }
    /**
     * Pushes the data to all users that subscribed to this pool
     * @param tda
     */
    public void push(OUTPUT tda)
    {
        DiamondList<User> subscribers = subscribersByPool.forwardGet(this);
        subscribers.bottom();
        while (!subscribers.isPointerNull())
        {
            String userId = subscribers.getCurrent().userId;

            if ( OnlineStateManager.isUserOnline(userId) ) {
                ToxitterWebsocketHandler.push(userId, tda);
            } else
            {
                undeliveredPool.put(subscribers.getCurrent(),tda);
            }
            subscribers.next();
        }
    }
}
