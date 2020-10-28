package Avatar.Elemental.aether;

import Avatar.Boxfresh.routes.User;
import Avatar.Elemental.earth.ID;
import Avatar.Elemental.earth.ReservoirEntity;
import Avatar.Elemental.earth.ReservoirEntityList;

import java.util.TreeMap;

public class ConnectionManager
{
    private static TreeMap<ID,ID> liveIDs = new TreeMap<>();
    private static TreeMap<ID,TreeMap<Class<? extends Connectable>, Connectable>> das;
    private static TreeMap<String, User> connectableTreemap = new TreeMap<>();

    public static void connect(Connectable connection, User user)
    {
        das.get(user.getId()).put(connection.getClass(),connection);
        connectableTreemap.put(connection.getSourceIdentifier(),user);
    }

    public static boolean isConnected(User user)
    {
        return das.containsKey(user.getId());
    }

    public static boolean isConnectedByType(User user, Class<? extends Connectable> connectableClass)
    {
        return das.get(user.getId()).containsKey(connectableClass);
    }

    public static User getUserByConnector(Connectable connectable)
    {
        return connectableTreemap.get(connectable.getSourceIdentifier());
    }

    public static void disconnect(User user, Class<? extends Connectable> c)
    {
        das.get(user.getId()).remove(c);
    }

    public static void disconnectAll(User user)
    {
        das.remove(user.getId());
    }

    /**
     * We assume that only one connectable per connectable type is assigned to the user.
     * One connectable may be a thing like one websocket, or one html-session or other things like that
     * @param user
     * @param c
     */
    public static Connectable getConnectionByType(User user, Class<? extends Connectable> c)
    {
        return das.get(user.getId()).get(c);
    }
}