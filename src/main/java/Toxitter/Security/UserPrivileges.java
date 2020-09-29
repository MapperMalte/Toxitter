package Toxitter.Security;

import Toxitter.Model.elemental.sky.DiamondList;

import java.util.TreeMap;

public class UserPrivileges
{
    private static TreeMap<String, DiamondList<String>> privileges = new TreeMap<>();

    public static void add(String userId, String scope)
    {
        if ( privileges.containsKey(userId) )
        {
            privileges.get(userId).addOnTop(scope);
        } else {
            DiamondList<String> dl = new DiamondList<>();
            privileges.put(userId,dl);
            dl.addOnTop(scope);
        }
    }

    /**
     *
     * @param userId
     * @param scope
     * @return
     */
    public static boolean hasUserPrivilege(String userId, String scope)
    {
        if ( privileges.containsKey(userId) )
        {
            return privileges.get(userId).contains(scope);
        } else {
            return false;
        }
    }
}
