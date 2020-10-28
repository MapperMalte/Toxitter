package Avatar.Elemental.water;

import Avatar.Boxfresh.routes.User;

public interface Pusher {
    public abstract void push(User user, OUTPUT outputDTO);
}
