package examples.toxitter;

import Toxitter.Core.annotations.FetchAt;
import Toxitter.Core.annotations.RequestParam;
import Toxitter.Core.annotations.Route;
import Toxitter.Security.annotations.Protected;

@FetchAt(route = "notifications")
public class Notifications
{
    @Protected(scope = "user")
    @Route(route = "user")
    public static String getUserNotifications(@RequestParam(name = "userId", obligatory = true) String userId)
    {
        System.out.println("Getting Notifications for User "+userId);
        System.out.println("Notifications: "+JsonNotification.toJson(UserNotificationCollector.byUserId(userId).fetchAllNotifications()));
        return JsonNotification.toJson(UserNotificationCollector.byUserId(userId).fetchAllNotifications());
    }
}