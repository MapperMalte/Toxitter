package Avatar.Boxfresh.routes.test;

import Avatar.Boxfresh.routes.Friend;
import Avatar.Boxfresh.routes.User;
import Avatar.Boxfresh.reservoirs.UserReservoir;

public class FriendsTest
{
    public void test()
    {
        User malte = UserReservoir.registerUser("Malte","malte_nolden@yahoo.de", "pwd");
        User satan = UserReservoir.registerUser("Satan","satan@666.de", "godsuxxHEHE");

        Friend.sendFriendRequestFromTo(malte.userId,satan.name);
        Friend.acceptFriendRequestFromBy(malte.name,satan.userId);

    }
}
