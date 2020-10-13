package Avatar.Elemental.aether;

import Avatar.Elemental.wind.artifacts.DiamondList;

import java.util.Date;

public class ToxitterSessionReservoir
{
    private static DiamondList<Request> requests = new DiamondList<>();

    public static class Request
    {
        ToxitterModelSignature signature;
        String ip;
        Date d;
    }
    public static void registerRequest(ToxitterModelSignature tms, String ip)
    {
        System.out.println("Incoming: "+tms.toString());
        Request r = new Request();
        r.ip = ip;
        r.signature = tms.getClone();
        r.d = new Date();
        System.out.println("Signature Registered in Reservoir: "+r.signature.toString());
        requests.addOnTop(r);
    }
    public static void log()
    {
        requests.bottom();
        while (!requests.isPointerNull())
        {
            System.out.println("Request from "+requests.getCurrent().ip+" with signature "+requests.getCurrent().signature.toString()+" at "+requests.getCurrent().d.toString());
            requests.next();
        }
    }
}