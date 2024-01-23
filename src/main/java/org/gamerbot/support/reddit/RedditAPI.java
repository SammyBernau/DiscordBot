package org.gamerbot.support.reddit;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.gamerbot.support.api.APIRequest;
import org.springframework.http.*;

import java.io.IOException;
import java.util.*;
import java.util.List;

public class RedditAPI {

    public final static String REDDIT_THUMBNAIL_URL = "https://i.imgur.com/sdO8tAw.png";

    private final static String REDDIT_BASE_URL = "https://www.reddit.com";

    public static List<RedditPost> topRedditPosts(String subReddit) {
        String json = getHotPostsJson(subReddit);

        JsonObject hotPostsJsonObject = new Gson().fromJson(json,JsonObject.class);
        JsonArray posts = hotPostsJsonObject.getAsJsonObject("data").getAsJsonArray("children");

        ArrayList<RedditPost> redditPosts = new ArrayList<>();
        posts.forEach(post -> {
            JsonObject postAsObject = post.getAsJsonObject().getAsJsonObject("data");
            String url = postAsObject.has("url_overridden_by_dest") ?
                    postAsObject.get("url_overridden_by_dest").getAsString() :
                    "";
            String description = postAsObject.has("selftext") ?
                    postAsObject.get("selftext").getAsString() :
                    "";
            redditPosts.add(new RedditPost(
                    "r/" + subReddit,
                    REDDIT_BASE_URL + postAsObject.get("permalink").toString().replaceAll("\"", ""),
                    replaceLast(postAsObject.get("title").toString().replaceFirst("\"", ""), "\"", ""),
                    url.replaceAll("\"", ""),
                    description));
        });

        return redditPosts;
    }

    public static String getHotPostsJson(String subReddit) {
        String authToken = getAuthToken();
        return new APIRequest.Builder("https://oauth.reddit.com/r/"+subReddit+"/hot")
                .userAgent()
                .bearerAuth(authToken)
                .get()
                .getBody();
    }

    private static String getAuthToken() {
        String clientId = "";
        String clientSecret= "";
        String body = "grant_type=client_credentials";
        String authUrl = "https://www.reddit.com/api/v1/access_token";

        try {
            Map<String, Object> map = new APIRequest.Builder(authUrl)
                    .basicAuth(clientId, clientSecret)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0")
                    .body(body)
                    .postAsJson();
            System.out.println(map);
            return String.valueOf(map.get("access_token"));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)"+regex+"(?!.*?"+regex+")", replacement);
    }

}
