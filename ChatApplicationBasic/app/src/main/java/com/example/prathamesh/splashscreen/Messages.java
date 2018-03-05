package com.example.prathamesh.splashscreen;

/**
 * Created by PRATHAMESH on 02-03-2018.
 */

public class Messages {

    private String content, username;

    public Messages(){

    }

    public Messages(String content, String username) {
        this.content = content;
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
