package Avatar.Elemental.earth;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;

public class ID implements Comparable<ID>
{
    private String id = "";
    private static KeyPair keyPair = getRSAKeypair();

    private static TreeMap<String, Boolean> IDs = new TreeMap<>();

    @Override
    public int compareTo(ID nid) {
        return id.compareTo(nid.id);
    }

    private static KeyPair getRSAKeypair()
    {
        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        kpg.initialize(1024);
        return kpg.generateKeyPair();
    }

    public ID()
    {
        /*
        try {

            //publicKey = kp.getPublic().toString();

            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256); // for example
            SecretKey secretKey = keyGen.generateKey();
            publicKey = secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/

        id = makeId();
    }

    public ID(String string)
    {
        id = string;
        IDs.put(id,true);
    }

    @Override
    public String toString()
    {
        return id;
    }

    public ID merge(ID with)
    {
        return new ID(this.id+with.id);
    }

    public static ID fromString(String string)
    {
        return new ID(string);
    }

    public static boolean exists(ID id)
    {
        return IDs.containsKey(id.id);
    }

    public static String makeId()
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < 32; i++)
        {
            sb.append((char)(97+(Math.random()* (122-97))));
        }
        String result = sb.toString();
        if ( IDs.containsKey(result) )
        {
            return makeId();
        }
        IDs.put(result,true);
        return sb.toString();
    }

    public int makeIntId()
    {
        return (int)(Math.random()*((double)Integer.MAX_VALUE));
    }

    public long makeLongId()
    {
        return (long)(Math.random()*((double)Long.MAX_VALUE));
    }
}
