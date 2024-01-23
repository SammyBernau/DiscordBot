package org.gamerbot.support.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class APIRequest {

    private HttpHeaders headers;
    private RestTemplate restTemplate;
    private String url;
    private String body;

    APIRequest(String url) {
        this.url = url;
        this.headers = new HttpHeaders();
        this.restTemplate = new RestTemplate();
    }

    public static class Builder {

        private APIRequest request;

        public Builder(String url) {
            this.request = new APIRequest(url);
            request.setBody("");
        }

        public Builder userAgent() {
            return header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
        }

        public Builder header(String key, String value) {
            request.headers.put(key, Collections.singletonList(value));
            return this;
        }

        public Builder header(String key, List<String> value) {
            request.headers.put(key, value);
            return this;
        }

        public Builder basicAuth(String username, String password) {
            request.headers.setBasicAuth(username, password);
            return this;
        }

        public Builder bearerAuth(String authToken) {
            request.headers.setBearerAuth(authToken);
            return this;
        }

        public Builder contentType(MediaType mediaType) {
            request.headers.setContentType(mediaType);
            return this;
        }

        public Builder body(String body) {
            request.setBody(body);
            return this;
        }

        private HttpEntity<String> buildEntity() {
            return new HttpEntity<>(this.request.body, this.request.headers);
        }

        public ResponseEntity<String> post() {
            return this.request.restTemplate.postForEntity(this.request.url, buildEntity(), String.class);
        }

        public Map<String,Object> postAsJson() throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            return new HashMap<>(mapper.readValue(post().getBody(), new TypeReference<Map<String, Object>>() {}));
        }

        public ResponseEntity<String> get() {
            return this.request.restTemplate.exchange(this.request.url, HttpMethod.GET,buildEntity(),String.class);
        }

        public JsonObject getAsJson() {
            return new Gson().fromJson(get().getBody(), JsonObject.class);
        }

    }
}
