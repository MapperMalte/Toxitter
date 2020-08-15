package examples.toxitter.test;

import Toxitter.Logging.Ullog;
import examples.toxitter.*;
import org.junit.Test;
import theory.DiamondList;

import static org.junit.Assert.assertEquals;

public class UllTest
{
    @Test
    public void testUserReservoir()
    {
        Ullog.put(UserReservoir.registerUser("Malte Nolden","malte_nolden@yahoo.de", User.getPwdHashMock()));
        Ullog.put(UserReservoir.registerUser("Niklas Köhler","nkoehler@gmx.de", User.getPwdHashMock()));
        Ullog.put(UserReservoir.getUserIdByMail("malte_nolden@yahoo.de"));

        assertEquals(UserReservoir.getUserByUserId(UserReservoir.getUserIdByMail("malte_nolden@yahoo.de")).getName(),"Malte Nolden");
    }

    @Test
    public void testLikes()
    {
        UserReservoir.registerUser("Malte Nolden","malte_nolden@yahoo.de", User.getPwdHashMock());
        UserReservoir.registerUser("Niklas Köhler","nkoehler@gmx.de", User.getPwdHashMock());

        String malteId = UserReservoir.getUserByMail("malte_nolden@yahoo.de").getId();
        String niklasId = UserReservoir.getUserByMail("nkoehler@gmx.de").getId();

        Ullog.put("Maltes ID: "+malteId);
        Ullog.put("Niklas ID: "+niklasId);

        Post p1 = Post.create(malteId,"Hallo Welt","Ich bin der erste Post");
        Post p2 = Post.create(niklasId,"DOOMED","Ich bin ein Post von Niklas");
        Post p3 = Post.create(malteId,"Post3"," sakdhgfh");

        p1.react(malteId, "grinning");
        p1.react(niklasId, "joy");
        p1.react(niklasId, "grinning");

        JsonNotification[] notifs = UserNotificationCollector.byUserId(malteId).fetchAllNotifications();
        for(JsonNotification notif: notifs)
        {
            System.out.println(notif.text);
        }

        DiamondList<Post> postsByMalte = PostReservoir.getAllPostsByUserId(malteId);
        postsByMalte.bottom();
        while ( !postsByMalte.isPointerNull() )
        {
            Post p = postsByMalte.getCurrent();
            System.out.println("Dies ist ein Post von Malte: Titel: "+p.title+" und Inhalt: "+p.content);
            postsByMalte.next();
        }

        DiamondList<Post> postsByNiklas = PostReservoir.getAllPostsByUserId(niklasId);
        postsByNiklas.bottom();
        while ( !postsByNiklas.isPointerNull() )
        {
            Post p = postsByNiklas.getCurrent();
            System.out.println("Dies ist ein Post von Niklas: Titel: "+p.title+" und Inhalt: "+p.content);
            postsByNiklas.next();
        }
    }
}