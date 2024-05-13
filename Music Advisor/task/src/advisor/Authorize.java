package advisor;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static advisor.Main.*;


public class Authorize {
    public static void getAccessToken() {

        System.out.println("making http request for access_token...");
        // System.out.println("response:");

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(Main.SERVER_PATH + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=authorization_code"
                                + "&code=" + AURHORIZATION_CODE
                                + "&client_id=" + CLIENT_ID
                                + "&client_secret=" + CLIENT_SECRET
                                + "&redirect_uri=" + REDIRECT_URI))
                .build();

        try {

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assert response != null;
            //System.out.println(response.body());
            String json = response.body();
            JsonElement jsonElement = JsonParser.parseString(json).getAsJsonObject();
            ACCESS_TOKEN = jsonElement.getAsJsonObject().get("access_token").getAsString();
            // System.out.println(ACCESS_TOKEN);
            System.out.println("Success!");

        } catch (InterruptedException | IOException e) {
            System.out.println("Error response");
        }

    }
    public static void getAuthorizationCode() {


        //Creating a server and listening to the request.
        try {
            HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            server.start();
            //Creating a line to go to in the browser
            String uri = SERVER_PATH + "/authorize"
                    + "?client_id=" + CLIENT_ID
                    + "&redirect_uri=" + REDIRECT_URI
                    + "&response_type=code";
            System.out.println("use this link to request the access code:");
            System.out.println(uri);
            server.createContext("/",
                    new HttpHandler() {
                        public void handle(HttpExchange exchange) throws IOException {
                            String query = exchange.getRequestURI().getQuery();
                            if (query != null && query.contains("code")) {
                                AURHORIZATION_CODE = query.substring(5);
                                System.out.println("code received");
                                // System.out.println(AURHORIZATION_CODE);
                                String request = "Got the code. Return back to your program.";
                                exchange.sendResponseHeaders(200, request.length());
                                exchange.getResponseBody().write(request.getBytes());
                                exchange.getResponseBody().close();
                            } else {
                                String request1 = "Authorization code not found. Try again.";
                                exchange.sendResponseHeaders(200, request1.length());
                                exchange.getResponseBody().write(request1.getBytes());
                                exchange.getResponseBody().close();
                            }

                        }
                    });

            System.out.println("waiting for code...");
            while (AURHORIZATION_CODE.length() == 0) {
                Thread.sleep(100);
            }
            server.stop(5);


        } catch (IOException | InterruptedException e) {
            System.out.println("Server error");
        }

    }
}
