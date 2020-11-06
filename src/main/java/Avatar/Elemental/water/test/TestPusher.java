package Avatar.Elemental.water.test;

import Avatar.Boxfresh.routes.User;
import Avatar.Elemental.water.OUTPUT;

public class TestPusher implements Avatar.Elemental.water.Pusher {
    @Override
    public void push(User user, OUTPUT outputDTO)
    {
        System.out.println("PUSH output "+outputDTO.asJSON());
    }
}
