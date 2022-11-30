/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.Moodle.Servicios;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *
 * @author FNZABALETAA
 */
public class MoodleWebServiceAuth {

    private static String token = null;
    private static String username = null;
    private static String password = null;
    private static String url = null;

    MoodleWebServiceAuth() {
    }

    /**
     * One init method MUST be called before any other call to any other
     * MoodleRestXXXXXXX Classes to initialise the Moodle URL web server service
     * and authentication. url contains the URL of the REST server service
     * within the Moodle site. token Contains the token provided by the Moodle
     * installation for access to the configured REST web services.
     *
     * @param url String
     * @param token String
     */
    public static void init(String url, String token) {
        MoodleWebServiceAuth.token = token;
        MoodleWebServiceAuth.url = url;
    }

    /**
     * One init method MUST be called before any other call to any other
     * MoodleRestXXXXXXX Classes to initialise the Moodle URL web server service
     * and authentication. url contains the URL of the REST server service
     * within the Moodle site. username password pair Contains the username and
     * password for authentication to the Moodle configured REST web services.
     *
     * @param url String
     * @param username String
     * @param password String
     */
    public static void init(String url, String username, String password) {
        MoodleWebServiceAuth.username = username;
        MoodleWebServiceAuth.password = password;
        MoodleWebServiceAuth.url = url;
    }

    /**
     * Returns the currently configured token or null if not set.
     *
     * @return token String
     */
    public static String getToken() {
        return token;
    }

    /**
     * Returns the currently configured username or null if not set.
     *
     * @return username String
     */
    public static String getUsername() {
        return username;
    }

    /**
     * Returns the currently configured password or null if not set.
     *
     * @return password String
     */
    public static String getPassword() {
        return password;
    }

    /**
     * Returns the currently configured URL or null if not set.
     *
     * @return url String
     */
    public static String getURL() {
        return url;
    }

    /**
     * Sets the authentication token.
     *
     * @param token String
     */
    public static void setAuth(String token) {
        MoodleWebServiceAuth.token = token;
    }

    /**
     * Sets the username/password pair for authentication.
     *
     * @param username String
     * @param password String
     */
    public static void setAuth(String username, String password) {
        MoodleWebServiceAuth.username = username;
        MoodleWebServiceAuth.password = password;
    }

    /**
     * Returns the HTTP encoded string required for web service authentication.
     * Order of authentication methods: token then username/password, if token
     * not initialised or null if both methods not initialised.
     *
     * @return String containing HTTP encoded string or null.
     * @throws UnsupportedEncodingException
     */
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

    /**
     * Sets the URL to the REST server service within the Moodle site.
     *
     * @param url String
     */
    public static void setURL(String url) {
        MoodleWebServiceAuth.url = url;
    }
}
