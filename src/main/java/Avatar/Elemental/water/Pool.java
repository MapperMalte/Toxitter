package Avatar.Elemental.water;

import Avatar.Boxfresh.routes.User;
import Avatar.Elemental.aether.OnlineStateManager;
import Avatar.Elemental.earth.ID;
import Avatar.Elemental.earth.Relation;
import Avatar.Elemental.earth.ReservoirEntity;
import Avatar.Elemental.earth.ReservoirEntityList;
import Avatar.Elemental.wind.artifacts.DiamondList;

public class Pool<T extends OUTPUT> extends ReservoirEntity
{
    private Relation<User, T> undeliveredPool = new Relation<>();
    private Relation<Pool<T>, User> subscribersByPool = new Relation<>();
    private Pusher pusher;

    private final ID telekey = new ID();

    public void subscribe(User user)
    {
        subscribersByPool.put(this,user);
    }

    public void setPusher(Pusher pusher)
    {
        this.pusher = pusher;
    }

    private void deliver(User user)
    {
        if ( OnlineStateManager.isUserOnline(user) )
        {
            DiamondList<T> open = undeliveredPool.forwardGet(user);
            open.bottom();
            while (!open.isPointerNull()) {
                pusher.push(user, open.getCurrent());
                open.next();
            }
        }
    }

    public void userComesOnline(User user)
    {
        DiamondList<T> open = undeliveredPool.forwardGet(user);
        open.bottom();
        while (!open.isPointerNull()) {
            pusher.push(user, open.getCurrent());
            open.removeCurrent();
        }
    }

    public ReservoirEntityList<T> pull(User user)
    {
        return undeliveredPool.forwardGet(user);
    }
    /**
     * Pushes the data to all users that subscribed to this pool
     * @param tda
     */
    public void push(T tda)
    {
        DiamondList<User> subscribers = subscribersByPool.forwardGet(this);
        subscribers.bottom();
        while (!subscribers.isPointerNull())
        {
            if (OnlineStateManager.isUserOnline(subscribers.getCurrent())) {
                pusher.push(subscribers.getCurrent(), tda);
            } else
            {
                undeliveredPool.put(subscribers.getCurrent(),tda);
            }
            subscribers.next();
        }
    }
}