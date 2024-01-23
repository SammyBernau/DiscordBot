package org.gamerbot.support.reddit;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RedditPost {

    private String subReddit;
    private String url;
    private String title;
    private String imgUrl;
    private String description;

    public RedditPost(String subReddit, String url, String title, String imgUrl) {
        this.subReddit = subReddit;
        this.url = url;
        this.title = title;
        this.imgUrl = imgUrl;
        this.description = "";
    }

    public RedditPost(String subReddit, String url, String title, String imgUrl, String description) {
        this.subReddit = subReddit;
        this.url = url;
        this.title = title;
        this.imgUrl = imgUrl;
        this.description = description;
    }

    public boolean hasImg() {
//        return !imgUrl.contains("/r/") && imgUrl.length() > 4;
        return imgUrl.endsWith(".jpg") || imgUrl.endsWith(".png") || imgUrl.endsWith(".gif");
    }

    public boolean hasLink() {
        return imgUrl.length() > 5;
    }

}
