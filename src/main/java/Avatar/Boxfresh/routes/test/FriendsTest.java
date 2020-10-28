package Avatar.Boxfresh.routes.test;

import Avatar.Boxfresh.relations.Friendslist;
import Avatar.Boxfresh.routes.Friends;
import Avatar.Boxfresh.routes.User;
import Avatar.Boxfresh.routes.UserReservoir;

public class FriendsTest
{
    public void test()
    {
        User malte = UserReservoir.registerUser("Malte","malte_nolden@yahoo.de", "pwd");
        User satan = UserReservoir.registerUser("Satan","satan@666.de", "godsuxxHEHE");

        Friends.sendFriendRequestFromTo(malte.userId,satan.name);
        Friends.acceptFriendRequestFromBy(malte.name,satan.userId);


    }
}
