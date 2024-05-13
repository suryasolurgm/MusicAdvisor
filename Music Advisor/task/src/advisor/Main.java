package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {


    private static boolean accessConfirmed = false;
    public static final  HashMap<String,String> categoriesHashMap = new HashMap<>();
    public static final String CLIENT_ID = "f8704ba485544f758dcb9b7b299ceaa7";
    public static final String CLIENT_SECRET = "bb5892cc37aa4260bfc6f3b596a2a0d1";
    public static final String REDIRECT_URI = "http://localhost:8080"; // Replace with your redirect URI
    public static String SERVER_PATH = "https://accounts.spotify.com";
    public static String API_SERVER_PATH="https://api.spotify.com";
    public static String AURHORIZATION_CODE = "";
    public static String ACCESS_TOKEN = "";
    public static Integer PAGE_SIZE = 5;

    public static void main(String[] args) {
        if (args.length > 1 && args[0].equals("-access") ) {
            SERVER_PATH = args[1];
            if(args[2].equals("-resource")){
                API_SERVER_PATH =args[3];
            }
            if(args[4].equals("-page")){
                PAGE_SIZE = Integer.parseInt(args[5]);
            }
        }
        Scanner scanner = new Scanner(System.in);
        PrintPage printPage =new PrintPage(Main.PAGE_SIZE);
        String input;

        do {
            input = scanner.nextLine();

            switch (input) {
                case "new":
                    if (accessConfirmed) {
                       printPage.print(service.getNew());
                    } else {
                        System.out.println("Please, provide access for application.");
                    }
                    break;
                case "featured":
                    if (accessConfirmed) {
                        printPage.print(service.getFeatured());
                    } else {
                        System.out.println("Please, provide access for application.");
                    }
                    break;
                case "categories":
                    if (accessConfirmed) {
                        printPage.print(service.getAllCategories());
                    } else {
                        System.out.println("Please, provide access for application.");
                    }
                    break;
                case "auth":
                    Authorize.getAuthorizationCode();
                    Authorize.getAccessToken();
                    accessConfirmed = true;
                    break;
                case "exit":
                    //System.out.println("---GOODBYE!---");
                    break;
                case ("next"):
                    printPage.printNext();
                    break;
                case ("prev"):
                    printPage.printPrev();
                    break;
                default:
                    if (input.startsWith("playlists")) {
                        if (accessConfirmed) {
                            String category = input.substring(10).trim();
                            //System.out.println("category inside playslistas"+category);
                            service.getAllCategories();
                            if(service.getCategoryById(category)==null){
                                break;
                            }else{
                                printPage.print(service.getCategoryById(category));
                            }
                        } else {
                            System.out.println("Please, provide access for application.");
                        }
                    } else {
                        System.out.println("Invalid command. Please try again.");
                    }
                    break;
            }

        } while (!input.equals("exi"));

        scanner.close();

    }
}
