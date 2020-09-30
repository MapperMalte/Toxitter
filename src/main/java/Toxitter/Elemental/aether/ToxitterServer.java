package Toxitter.Elemental.aether;

import Toxitter.Logging.Ullog;
import com.sun.net.httpserver.HttpServer;
import Toxitter.Boxfresh.ToxitterStandardConfiguratedEnvironment;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ToxitterServer
{
    public static TreeMap<String, ToxitterModelSignature> routeSignatures = new TreeMap<>();

    private static HttpServer server;

    public static void main(String[] args)
    {
        try {
            ToxitterStandardConfiguratedEnvironment.up();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void up(int port, int threads) throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", port), 0);
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
        server.setExecutor(threadPoolExecutor);
        server.start();
        Ullog.put(" Server started on port "+port+" on "+threads+" threads.");
        for(Map.Entry<String,ToxitterModelSignature> entry: routeSignatures.entrySet())
        {
            server.createContext(entry.getKey(), new ToxitterHttpHandler());
        }
        int port2 = 8887;
        ToxitterWebsocketHandler s = new ToxitterWebsocketHandler(port2);
        s.start();
        System.out.println("WebsocketServer started on port: " + s.getPort());

    }

    public static void registerRoute(String route, ToxitterModelSignature model)
    {
        System.out.println("Route: "+route);
        routeSignatures.put(route,model);
    }
}
