package Avatar.Elemental.water.test;

import Avatar.Elemental.water.OUTPUT;

public class TestPusher implements Avatar.Elemental.water.Pusher {
    @Override
    public void push(String userId, OUTPUT outputDTO) {
        System.out.println("Pushing "+outputDTO.asJSON()+" to "+userId);
    }
}
