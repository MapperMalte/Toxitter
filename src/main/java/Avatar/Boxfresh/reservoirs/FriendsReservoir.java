package Avatar.Boxfresh.reservoirs;

import Avatar.Boxfresh.routes.Friend;
import Avatar.Boxfresh.routes.User;
import Avatar.Elemental.earth.Reservoir;
import Avatar.Elemental.earth.ReservoirEntityList;
import Avatar.Elemental.earth.SymmetricRelation;

public class FriendsReservoir
{
    private static SymmetricRelation<User> friends;

    public static ReservoirEntityList<User> getAllFriendsOfUser(User user)
    {
        return friends.get(user);
    }
}
