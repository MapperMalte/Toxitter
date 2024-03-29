var routes = [];
var request = new XMLHttpRequest();
request.open("GET", "commands_client.json", false);
request.send(null)
var my_JSON_object = JSON.parse(request.responseText);
console.log(my_JSON_object.length);
console.log(JSON.stringify(my_JSON_object[0].route));
for (let i = 0; i < my_JSON_object.length; i++)
{
    routes.push(my_JSON_object[i]);
}
var ws = new WebSocket("ws://localhost:8887");

ws.onopen = function()
{
    // Web Socket is connected, send data using send()
};
ws.onmessage = function (evt)
{
    var received_msg = evt.data;
    for (let i = 0; i < routes.length; i++)
    {
        if (received_msg.startsWith(routes[i].route)) {
            console.log("FOUND ROUTE! "+routes[i].route);
            console.log("JSON: "+received_msg.substr(routes[i].route.length+1));
            var json_data = JSON.parse(received_msg.substr(routes[i].route.length+1));
            console.log("Method "+routes[i].method);
            var fname = routes[i].method;
            var params = "";
            send(fname,json_data);
        } else {
            console.log("Not starts with: " + routes[i].route)
        }
    }
};
ws.onclose = function()
{
    // websocket is closed.
    alert("Connection is closed...");
};