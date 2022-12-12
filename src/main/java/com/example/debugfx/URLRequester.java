package com.example.debugfx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class URLRequester {

    private String link;
    private String content;

    URLRequester(String link) throws IOException {
        this.link = link;



        URL url = new URL(link);
        URLConnection connection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            content.append(line).append('\n');
        }
        bufferedReader.close();
        this.content = content.toString();

    }

    String getContent() {
        return content;
    }
}
