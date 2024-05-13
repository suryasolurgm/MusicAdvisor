package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static advisor.Main.*;

public class service {
    public static String getCategoryById(String category){
        String categoryId="";
        if(categoriesHashMap.containsKey(category)){
            categoryId = categoriesHashMap.get(category);
        }else {
            System.out.println("Unknown category name.");
            return null;
        }
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .uri(URI.create(API_SERVER_PATH+"/v1/browse/categories/"+categoryId+"/playlists"))
                .GET()
                .build();
        try {

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            assert response != null;
            //System.out.println(response.body());
            if (response.body().contains("error")) {
                JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                JsonObject error = json.getAsJsonObject("error");
                System.out.println(error.get("message").getAsString());
                return null;
            }

            String json = response.body();
//            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
//            JsonArray playlistsArray = jsonObject.getAsJsonObject("playlists").getAsJsonArray("items");
//
//            for (JsonElement playlistElement : playlistsArray) {
//                JsonObject playlistObject = playlistElement.getAsJsonObject();
//                String playlistName = playlistObject.get("name").getAsString();
//                String playlistUrl = playlistObject.get("external_urls").getAsJsonObject().get("spotify").getAsString();
//                System.out.println(playlistName);
//                System.out.println(playlistUrl + "\n");
//            }

            List<Info> infos = new ArrayList<>();
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            JsonObject categories = jsonObject.getAsJsonObject("playlists");

            for (JsonElement item : categories.getAsJsonArray("items")) {
                Info element = new Info();
                element.setAlbum(item.getAsJsonObject().get("name").toString().replaceAll("\"", ""));

                element.setLink(item.getAsJsonObject().get("external_urls")
                        .getAsJsonObject().get("spotify")
                        .toString().replaceAll("\"", ""));

                infos.add(element);
            }

            StringBuilder result = new StringBuilder();
            for (Info each : infos) {
                result.append(each.album).append("\n")
                        .append(each.link).append("\n")
                        .append("\n");
            }
            return result.toString();

        } catch (InterruptedException | IOException e) {
            System.out.println("Error response");
        }
        return null;
    }
    public static String getFeatured(){
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .uri(URI.create(API_SERVER_PATH+"/v1/browse/featured-playlists"))
                .GET()
                .build();
        try {

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            assert response != null;
            //  System.out.println(response.body());
            String json = response.body(); // Your JSON string here
//            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
//            JsonArray playlistsArray = jsonObject.getAsJsonObject("playlists").getAsJsonArray("items");
//
//            for (JsonElement playlistElement : playlistsArray) {
//                JsonObject playlistObject = playlistElement.getAsJsonObject();
//                String playlistName = playlistObject.get("name").getAsString();
//                String playlistUrl = playlistObject.get("external_urls").getAsJsonObject().get("spotify").getAsString();
//                System.out.println(playlistName);
//                System.out.println(playlistUrl + "\n");
//            }
            List<Info> infos = new ArrayList<>();
            JsonObject jo = JsonParser.parseString(json).getAsJsonObject();
            JsonObject categories = jo.getAsJsonObject("playlists");

            for (JsonElement item : categories.getAsJsonArray("items")) {
                Info element = new Info();
                element.setAlbum(item.getAsJsonObject().get("name").toString().replaceAll("\"", ""));

                element.setLink(item.getAsJsonObject().get("external_urls")
                        .getAsJsonObject().get("spotify")
                        .toString().replaceAll("\"", ""));

                infos.add(element);
            }
            StringBuilder result = new StringBuilder();
            for (Info each : infos) {
                result.append(each.album).append("\n")
                        .append(each.link).append("\n")
                        .append("\n");
            }
            return result.toString();
        } catch (InterruptedException | IOException e) {
            System.out.println("Error response");
        }
        return null;
    }
    public static String getNew(){
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .uri(URI.create(API_SERVER_PATH+"/v1/browse/new-releases"))
                .GET()
                .build();
        try {

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            assert response != null;
            // System.out.println(response.body());
            String json = response.body(); // Your JSON string here
//            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
//            JsonObject albumsObject = jsonObject.getAsJsonObject("albums");
//            JsonArray itemsArray = albumsObject.getAsJsonArray("items");
//
//            for (JsonElement element : itemsArray) {
//                JsonObject albumObject = element.getAsJsonObject();
//                String albumName = albumObject.get("name").getAsString();
//                JsonArray artistsArray = albumObject.getAsJsonArray("artists");
//                StringBuilder artistsString = new StringBuilder();
//                for (JsonElement artistElement : artistsArray) {
//                    JsonObject artistObject = artistElement.getAsJsonObject();
//                    String artistName = artistObject.get("name").getAsString();
//                    artistsString.append(artistName).append(", ");
//                }
//                if (artistsString.length() > 0) {
//                    artistsString.setLength(artistsString.length() - 2); // Remove the trailing comma and space
//                }
//                String albumUrl = albumObject.get("external_urls").getAsJsonObject().get("spotify").getAsString();
//
//                System.out.println(albumName);
//                System.out.println("[" + artistsString.toString() + "]");
//                System.out.println(albumUrl);
//                System.out.println();
//            }
            List<Info> infos = new ArrayList<>();
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            JsonObject categories = jsonObject.getAsJsonObject("albums");


            for (JsonElement item : categories.getAsJsonArray("items")) {
                Info element = new Info();
                element.setAlbum(item.getAsJsonObject().get("name").toString().replaceAll("\"", ""));

                StringBuilder artists = new StringBuilder("[");

                for (JsonElement name : item.getAsJsonObject().getAsJsonArray("artists")) {
                    if (!artists.toString().endsWith("[")) {
                        artists.append(", ");
                    }
                    artists.append(name.getAsJsonObject().get("name"));
                }

                element.setName(artists.append("]").toString().replaceAll("\"", ""));

                element.setLink(item.getAsJsonObject().get("external_urls")
                        .getAsJsonObject().get("spotify")
                        .toString().replaceAll("\"", ""));

                infos.add(element);
            }

            StringBuilder result = new StringBuilder();
            for (Info each : infos) {
                result.append(each.album).append("\n")
                        .append(each.name).append("\n")
                        .append(each.link).append("\n")
                        .append("\n");
            }
            return result.toString();
        } catch (InterruptedException | IOException e) {
            System.out.println("Error response");
        }
        return null;
    }
    public static String getAllCategories(){
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .uri(URI.create(API_SERVER_PATH+"/v1/browse/categories"))
                .GET()
                .build();

        try {

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            assert response != null;
            // System.out.println(response.body());
            if(true){
               // System.out.println("Top Lists");
            }

            String json = response.body();
            JsonElement jsonElement = JsonParser.parseString(json).getAsJsonObject();
            JsonObject categories1 = jsonElement.getAsJsonObject().getAsJsonObject("categories");
            JsonArray items = categories1.getAsJsonArray("items");

            for(JsonElement category : items) {
                String categoryName = category.getAsJsonObject().get("name").getAsString();
                String categoryId = category.getAsJsonObject().get("id").getAsString();
                if(!categoriesHashMap.containsKey(categoryName)){
                    categoriesHashMap.put(categoryName,categoryId);
                    //System.out.println(categoriesHashMap.get(categoryName));
                }

            }
            List<Info> infos = new ArrayList<>();
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            JsonObject categories = jsonObject.getAsJsonObject("categories");
            for (JsonElement item : categories.getAsJsonArray("items")) {
                Info element = new Info();
                element.setCategories(item.getAsJsonObject().get("name").toString().replaceAll("\"", ""));
                infos.add(element);
            }

            StringBuilder result = new StringBuilder();
            for (Info each : infos) {
                result.append(each.categories).append("\n").append("\n");;
            }
            return result.toString();


        } catch (InterruptedException | IOException e) {
            System.out.println("Error response");
        }
        return null;
    }
}
