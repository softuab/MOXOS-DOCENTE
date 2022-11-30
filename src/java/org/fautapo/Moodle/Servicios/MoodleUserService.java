/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.Moodle.Servicios; 

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.fautapo.Moodle.Entidades.MoodleUsers;
import org.fautapo.Moodle.Exception.MoodleRestUserException;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author hp
 */
public class MoodleUserService {

    public MoodleUserService() {
    }

    public static MoodleUsers createUser(MoodleUsers user) throws MoodleRestUserException, MalformedURLException, JSONException, IOException {
        List<MoodleUsers> usr = new ArrayList();
        MoodleUsers response = new MoodleUsers();
        usr = find(user);
        if (usr.isEmpty()) {
            usr = create(user);
            response = usr.get(0);
        } else {
            response = user;
            response.setId(usr.get(0).getId());
            String error = Update(response);
            if (error != null) {
                response.setERROR(error);
            }
        }
        return response;
    }
    public static MoodleUsers createUserByUserNameID(MoodleUsers user) throws MoodleRestUserException, MalformedURLException, JSONException, IOException {
        List<MoodleUsers> usr = new ArrayList();
        MoodleUsers response = new MoodleUsers();
        usr = findUserByid(user);
        if (usr.isEmpty()) {
            usr = create(user);
            response = usr.get(0);
        } else {
            response = user; 
            response.setId(usr.get(0).getId());
            String error = Update(response);
            if (error != null) {
                response.setERROR(error);
            }
        }
        return response;
    }
    public static MoodleUsers Get(String userid) throws MoodleRestUserException, MalformedURLException, JSONException, IOException {
        MoodleUsers user = new MoodleUsers();
        user.setIdnumber(userid);
        List<MoodleUsers> usr = new ArrayList();
        MoodleUsers response = new MoodleUsers();
        usr = find(user);
        if (usr.isEmpty()) {
            usr = create(user);
            response = usr.get(0);
        } else {
            response = usr.get(0);
        }
        return response;
    }

    public static List<MoodleUsers> findUserByid(MoodleUsers user) throws UnsupportedEncodingException, IOException, MalformedURLException, JSONException, MoodleRestUserException {
        List<MoodleUsers> users = find(user);
        if (!users.isEmpty()) {
            return users;
        } else {
            return findUserName(user);
        }
    }

    public static List<MoodleUsers> find(MoodleUsers user) throws UnsupportedEncodingException, IOException, MalformedURLException, JSONException, MoodleRestUserException {
        List<MoodleUsers> response = new ArrayList<MoodleUsers>();
        MoodleUsers usuario = user;
        String functionName = "core_user_get_users";
        String urlParameters = "criteria[0][key]=idnumber&criteria[0][value]=" + URLEncoder.encode(user.getIdnumber(), "UTF-8");
        JsonResult result = MoodleRestWebService.call(urlParameters, functionName);
        if (result.equals("ERROR")) {
            usuario.setERROR(result.getException().getMessage());
            response.add(usuario);
            return response;
        } else {
            JSONObject jsonResult = new JSONObject(result.getJsonresult());
            JSONArray arr = jsonResult.getJSONArray("users");
            if (arr.length() != 0) {
                JSONArray array = arr;
                for (int i = 0; i < array.length(); i++) {
                    JSONObject json = array.getJSONObject(i);
                    MoodleUsers usr = new MoodleUsers();
                    usr.setMoodleUserField(json);
                    response.add(usr);
                }
            }
            return response;
        }

    }

    public static List<MoodleUsers> findUserName(MoodleUsers user) throws UnsupportedEncodingException, IOException, MalformedURLException, JSONException, MoodleRestUserException {
        List<MoodleUsers> response = new ArrayList<MoodleUsers>();
        MoodleUsers usuario = user;
        String functionName = "core_user_get_users";
        String urlParameters = "criteria[0][key]=username&criteria[0][value]=" + URLEncoder.encode(user.getUsername(), "UTF-8");
        JsonResult result = MoodleRestWebService.call(urlParameters, functionName);
        if (result.equals("ERROR")) {
            usuario.setERROR(result.getException().getMessage());
            response.add(usuario);
            return response;
        } else {
            JSONObject jsonResult = new JSONObject(result.getJsonresult());
            JSONArray arr = jsonResult.getJSONArray("users");
            if (arr.length() != 0) {
                JSONArray array = arr;
                for (int i = 0; i < array.length(); i++) {
                    JSONObject json = array.getJSONObject(i);
                    MoodleUsers usr = new MoodleUsers();
                    usr.setMoodleUserFieldUserName(json);
                    response.add(usr);
                }
            }
            return response;
        }

    }

    private static List<MoodleUsers> create(MoodleUsers user) throws MoodleRestUserException, MalformedURLException, JSONException, UnsupportedEncodingException, IOException {
        List<MoodleUsers> response = new ArrayList<MoodleUsers>();
        MoodleUsers usuario = user;
        String functionName = "core_user_create_users";
        String urlParameters = "users[0][username]=" + URLEncoder.encode(user.getUsername().toLowerCase(), "UTF-8")
                + "&users[0][password]=" + URLEncoder.encode(user.getPassword().toLowerCase(), "UTF-8")
                + "&users[0][firstname]=" + URLEncoder.encode(user.getFirstname().toLowerCase(), "UTF-8")
                + "&users[0][lastname]=" + URLEncoder.encode(user.getLastname().toLowerCase(), "UTF-8")
                + "&users[0][email]=" + URLEncoder.encode(user.getEmail().toLowerCase(), "UTF-8")
                + "&users[0][idnumber]=" + URLEncoder.encode(user.getIdnumber(), "UTF-8");
        JsonResult result = MoodleRestWebService.call(urlParameters, functionName);
        if (result.equals("ERROR")) {
            usuario.setERROR(result.getException().getMessage());
            response.add(usuario);
        } else {
            JSONArray array = new JSONArray(result.getJsonresult());
            for (int i = 0; i < array.length(); i++) {
                JSONObject json = array.getJSONObject(i);
                usuario.setId(json.getInt("id"));
                response.add(usuario);
            }
        }
        return response;
    }

    private static String Update(MoodleUsers user) throws MoodleRestUserException, MalformedURLException, JSONException, UnsupportedEncodingException, IOException {
        String response = "";
        MoodleUsers usuario = user;
        String functionName = "core_user_update_users";
        String urlParameters = "users[0][username]=" + URLEncoder.encode(user.getUsername().toLowerCase(), "UTF-8")
                + "&users[0][password]=" + URLEncoder.encode(user.getPassword(), "UTF-8")
                + "&users[0][firstname]=" + URLEncoder.encode(user.getFirstname(), "UTF-8")
                + "&users[0][lastname]=" + URLEncoder.encode(user.getLastname(), "UTF-8")
                + "&users[0][email]=" + URLEncoder.encode(user.getEmail().toUpperCase(), "UTF-8")
                + "&users[0][id]=" + URLEncoder.encode(String.valueOf(user.getId()), "UTF-8");
        JsonResult result = MoodleRestWebService.call(urlParameters, functionName);
        if (result.equals("ERROR")) {
            response = result.getException().getMessage();
        } else {
            response = null;
        }
        return response;
    }

    public static String UpdatePassword(MoodleUsers user) throws MoodleRestUserException, MalformedURLException, JSONException, UnsupportedEncodingException, IOException {
        String response = "";
        MoodleUsers usuario = user;
        String functionName = "core_user_update_users";
        String urlParameters = "users[0][password]=" + URLEncoder.encode(user.getPassword(), "UTF-8") + "&users[0][username]=" + URLEncoder.encode(user.getUsername(), "UTF-8") + "&users[0][id]=" + URLEncoder.encode(String.valueOf(user.getId()), "UTF-8");
        JsonResult result = MoodleRestWebService.call(urlParameters, functionName);
        if (result.equals("ERROR")) {
            response = result.getException().getMessage();
        } else {
            response = null;
        }
        return response;
    } public static String UpdateIDNumber(MoodleUsers user) throws MoodleRestUserException, MalformedURLException, JSONException, UnsupportedEncodingException, IOException {
        String response = "";
        MoodleUsers usuario = user;
        String functionName = "core_user_update_users";
        String urlParameters = "users[0][idnumber]=" + URLEncoder.encode(user.getIdnumber(), "UTF-8") + "&users[0][id]=" + URLEncoder.encode(String.valueOf(user.getId()), "UTF-8");
        JsonResult result = MoodleRestWebService.call(urlParameters, functionName);
        if (result.equals("ERROR")) {
            response = result.getException().getMessage();
        } else {
            response = null;
        }
        return response;
    }
}
