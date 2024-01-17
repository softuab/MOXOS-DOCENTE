package com.moxos.uab.model.service.moodle;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MoodleWebServiceAuth {

    private static String token = null;
    private static String username = null;
    private static String password = null;
    private static String url = null;

    MoodleWebServiceAuth() {
    }

    public static void init(String url, String token) {
        MoodleWebServiceAuth.token = token;
        MoodleWebServiceAuth.url = url;
    }

    public static void init(String url, String username, String password) {
        MoodleWebServiceAuth.username = username;
        MoodleWebServiceAuth.password = password;
        MoodleWebServiceAuth.url = url;
    }

    public static String getToken() {
        return token;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getURL() {
        return url;
    }

    public static void setAuth(String token) {
        MoodleWebServiceAuth.token = token;
    }

    public static void setAuth(String username, String password) {
        MoodleWebServiceAuth.username = username;
        MoodleWebServiceAuth.password = password;
    }


    public static String getAuth() throws UnsupportedEncodingException {
        StringBuilder data = new StringBuilder();
        if (MoodleWebServiceAuth.getToken() != null) {
            data.append(URLEncoder.encode("wstoken", "UTF-8")).append("=").append(URLEncoder.encode(MoodleWebServiceAuth.getToken(), "UTF-8"));
            data.append("&");
            data.append(URLEncoder.encode("moodlewsrestformat", "UTF-8")).append("=").append(URLEncoder.encode("json", "UTF-8"));
        } else {
            if (MoodleWebServiceAuth.getUsername() != null && MoodleWebServiceAuth.getPassword() != null) {
                data.append(URLEncoder.encode("wsusername", "UTF-8")).append("=").append(URLEncoder.encode(MoodleWebServiceAuth.getUsername(), "UTF-8"));
                data.append("&"); // Fix by César Martínez
                data.append(URLEncoder.encode("wspassword", "UTF-8")).append("=").append(URLEncoder.encode(MoodleWebServiceAuth.getPassword(), "UTF-8"));
                data.append("&");
                data.append(URLEncoder.encode("moodlewsrestformat", "UTF-8")).append("=").append(URLEncoder.encode("json", "UTF-8"));
            } else {
                return null;
            }
        }
        return data.toString();
    }

    public static void setURL(String url) {
        MoodleWebServiceAuth.url = url;
    }
}
