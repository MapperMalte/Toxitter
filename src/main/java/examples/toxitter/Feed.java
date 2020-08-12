package examples.toxitter;

import Toxitter.Core.annotations.FetchAt;
import Toxitter.Security.annotations.Protected;
import Toxitter.Core.annotations.RequestParam;
import Toxitter.Core.annotations.Route;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import theory.DiamondList;
import theory.DiamondListAdapter;

@FetchAt(route = "feed")
public class Feed
{
    private static DiamondList<Post> posts = new DiamondList<>();

    public static void addPost(Post p)
    {
        posts.addOnTop(p);
    }

    @Route(route = "all")
    @Protected(scope = "user")
    public static String getFeed(@RequestParam(name = "filter",obligatory = true) String filter)
    {
        System.out.println("READING FEED!");
        posts.bottom();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DiamondList.class, new DiamondListAdapter());

        Gson gson = gsonBuilder.create();
        String result = "[";
        while (!posts.isPointerNull())
        {
            result += gson.toJson(posts.getCurrent())+", ";
            posts.next();
        }
        result = result.substring(0,result.length()-2);
        result += "]";
        return result;
    }
}