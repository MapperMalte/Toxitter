package com.locality.toxitterbackend2;

import ToxitterModel.*;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import theory.DiamondList;
import theory.ReplenisherStack;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ToxitterServer
{
    private static TreeMap<String, ToxitterModelSignature> routes = new TreeMap<>();
    public static void loadSeedData()
    {
        String userId = UserReservoir.registerUser("Admin","admin@toxitter.de","adminpwd");
        User user = UserReservoir.getUserByUserId(userId);
        user.photoUrl = "https://randomuser.me/api/portraits/women/93.jpg";
        UserPrivileges.add(userId,"user");
        UserPrivileges.add(userId,"admin");

        String userId2 = UserReservoir.registerUser("TestUser","sod",".");
        User user2 = UserReservoir.getUserByUserId(userId2);
        user2.photoUrl = "https://randomuser.me/api/portraits/men/50.jpg";
        UserPrivileges.add(userId2,"user");
    }
    public static void loadTest()
    {
        loadSeedData();
        UserReservoir.registerUser("Malte Nolden","maltemail", "pwdmalte");
        UserReservoir.registerUser("Niklas Köhler","nkoehler@gmx.de", User.getPwdHashMock());

        String malteId = UserReservoir.getUserByMail("maltemail").getId();
        String niklasId = UserReservoir.getUserByMail("nkoehler@gmx.de").getId();

        UserPrivileges.add(malteId,"admin");
        Ullog.put("Maltes ID: "+malteId);
        Ullog.put("Niklas ID: "+niklasId);

        Post p1 = Post.create(malteId,"Hallo Welt","Ich bin der erste Post");
        Post p2 = Post.create(niklasId,"DOOMED","Ich bin ein Post von Niklas");
        Post p3 = Post.create(malteId,"Post3"," sakdhgfh");

        p1.react(malteId, "rage");
        p1.react(niklasId, "joy");
        p1.react(niklasId, "rage");

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

    public static void main(String[] args)
    {
        try {
            loadTest();
            new ToxitterServer().serve();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HttpServer server;

    public void serve() throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);

        ToxitterOverseer.serve(ToxitterModel.Post.class);
        ToxitterOverseer.serve(ToxitterModel.Feed.class);
        ToxitterOverseer.serve(ToxitterModel.UserReservoir.class);
        ToxitterOverseer.serve(ToxitterSecurity.class);

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        server.setExecutor(threadPoolExecutor);
        server.start();
        Ullog.put(" Server started on port 8001");
    }

    public static void registerRoute(String route, ToxitterModelSignature model)
    {
        System.out.println("Route: "+route);
        routes.put(route,model);
        server.createContext(route, new  MyHttpHandler());
    }

    private static class MyHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException
        {
            System.out.println("Handling..."+httpExchange.getRequestURI().toString());
            String[] params = httpExchange.getRequestURI().toString().split("\\?");

            System.out.println("ParamCount "+params.length);
            System.out.println("Route: "+params[0]);
            ToxitterModelSignature tms = null;

            if ( !ToxitterSecurityMiddleware.allowed(params[0],httpExchange.getRequestURI().toString()) )
            {
                String response = "Not allowed!";
                Headers headers= httpExchange.getResponseHeaders();
                headers.add("Access-Control-Allow-Origin", "*");
                httpExchange.sendResponseHeaders(403, response.length());
                OutputStream outputStream = httpExchange.getResponseBody();
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
                return;
            }

            if ( routes.containsKey(params[0]) )
            {
                System.out.println("Route known! ");
                System.out.println("Params: ");
                tms = routes.get(params[0]);
            } else
            {
                System.out.println("Route unknown! ");
            }

            String response = "";
            ReplenisherStack<ToxitterModelSignature.Method> methods = null;
            if ( tms != null )
            {
                methods = tms.getReplenisherStack();
            }
            for(String param: params)
            {
                System.out.println("Param: "+param);
                String[] data = param.split("=");
                System.out.println("Datalength: "+data.length);
                if ( data.length > 1 )
                {
                    if ( tms != null )
                    {
                        ToxitterModelSignature.Method m = methods.find(data[0]);
                        if ( m!= null )
                        {
                            System.out.println("ToxitterMethod found: "+m.name);
                            m.value = data[1];
                        }
                    }
                    Ullog.put("Data[0]: "+data[0]+" and Data[1]: "+data[1]);
                    response += "Data[0]: "+data[0]+" and Data[1]: "+data[1]+" --- ";
                }
            }
            if ( tms != null )
            {
                System.out.println("#1 Complete: "+tms.isComplete());
                methods = tms.getReplenisherStack();
                methods.replenish();
                Object[] args = new Object[methods.getCount()];
                while(methods.getCount()>0)
                {
                    args[methods.getCount()-1] = methods.peek().value;
                    System.out.println("Key: "+methods.peek().name+" / Value: "+methods.peek().value);
                    methods.pop();
                }
                System.out.println(tms.toString());
                ToxitterSessionReservoir.registerRequest(tms,httpExchange.getRemoteAddress().getAddress().toString());
                tms.replenish();
                System.out.println("Now invoke ");
                try {
                    System.out.println("Invoking ... "+methods.peek().name);
                    String result = methods.peek().method.invoke(tms.toxiClass, args).toString();
                    response = result;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Response: "+response);
            response=Umlauter.umlaut(response);
            Headers headers= httpExchange.getResponseHeaders();
            headers.add("Access-Control-Allow-Origin", "*");
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();
        }
    }
}
