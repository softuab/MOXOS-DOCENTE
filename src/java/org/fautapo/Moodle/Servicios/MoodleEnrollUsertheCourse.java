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
import java.util.stream.Collectors;
import org.fautapo.Moodle.Entidades.MoodleCourse;
import org.fautapo.Moodle.Entidades.MoodleEnroll;
import org.fautapo.Moodle.Entidades.MoodleUsers;
import org.fautapo.Moodle.Exception.MoodleRestUserException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author hp
 */
public class MoodleEnrollUsertheCourse {

    public MoodleEnrollUsertheCourse() {
    }

    public static MoodleCourse GetEnroll(MoodleEnroll group) throws IOException, UnsupportedEncodingException, MalformedURLException, JSONException, MoodleRestUserException {
        List<MoodleCourse> response = new ArrayList<MoodleCourse>();
        response = find(group);
        if (response.isEmpty()) {
            return null;
        } else {
            List<MoodleCourse> sublista = response.stream().filter(p -> p.getId()== group.getCourseid()).collect(Collectors.toList());
            if (sublista.isEmpty()) {
                return null;
            } else {
                return sublista.get(0);
            }
        }
    }

    public static List<MoodleCourse> find(MoodleEnroll group) throws UnsupportedEncodingException, IOException, MalformedURLException, JSONException, MoodleRestUserException {
        List<MoodleCourse> response = new ArrayList<MoodleCourse>();
        MoodleCourse groups = new MoodleCourse();
        String functionName = "core_enrol_get_users_courses";
        String urlParameters = "userid=" + URLEncoder.encode(String.valueOf(group.getUserid()), "UTF-8");
        JsonResult result = MoodleRestWebService.call(urlParameters, functionName);
        if (result.equals("ERROR")) {
            groups.setERROR(result.getException().getMessage());
            response.add(groups);
            return response;
        } else {
            JSONArray arr = new JSONArray(result.getJsonresult());
            if (arr.length() != 0) {
                JSONArray array = arr;
                for (int i = 0; i < array.length(); i++) {
                    JSONObject json = array.getJSONObject(i);
                    MoodleCourse usr = new MoodleCourse();
                    usr.setMoodleCourseField(json);
                    response.add(usr);
                }
            }
            return response;
        }

    }
 public static List<MoodleUsers> GetUserEnrollCourse(int courseid) throws UnsupportedEncodingException, IOException, MalformedURLException, JSONException, MoodleRestUserException {
        List<MoodleUsers> response = new ArrayList<MoodleUsers>();
        MoodleUsers users = new MoodleUsers();
        String functionName = "core_enrol_get_enrolled_users";
        String urlParameters = "courseid=" + URLEncoder.encode(String.valueOf(courseid), "UTF-8");
        JsonResult result = MoodleRestWebService.call(urlParameters, functionName);
        if (result.equals("ERROR")) {
            users.setERROR(result.getException().getMessage());
            response.add(users);
            return response;
        } else {
            JSONArray arr = new JSONArray(result.getJsonresult());
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
    public static String create(MoodleEnroll user) throws MoodleRestUserException, MalformedURLException, JSONException, UnsupportedEncodingException, IOException {
        String response = "";
        MoodleEnroll usuario = user;
        String functionName = "enrol_manual_enrol_users";
        String urlParameters = "enrolments[0][roleid]=" + URLEncoder.encode(String.valueOf(user.getRoleid()), "UTF-8")
                + "&enrolments[0][userid]=" + URLEncoder.encode(String.valueOf(user.getUserid()), "UTF-8")
                + "&enrolments[0][courseid]=" + URLEncoder.encode(String.valueOf(user.getCourseid()), "UTF-8")
                + "&enrolments[0][suspend]=" + URLEncoder.encode(String.valueOf(user.getSuspend()), "UTF-8");
        JsonResult result = MoodleRestWebService.call(urlParameters, functionName);
        if (result.equals("ERROR")) {
            response = result.getException().getMessage();
        } else {
            response = null;
        }
        return response;
    }
}
