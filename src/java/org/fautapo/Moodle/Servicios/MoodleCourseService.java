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
import org.fautapo.Moodle.Entidades.MoodleCourse;
import org.fautapo.Moodle.Exception.MoodleRestUserException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author hp
 */
public class MoodleCourseService {

    public MoodleCourseService() {
    }

    public static MoodleCourse createCourse(MoodleCourse course) throws MoodleRestUserException, MalformedURLException, JSONException, IOException {
        List<MoodleCourse> courses = new ArrayList();
        courses = find(course);
        if (courses.isEmpty()) {
            courses = create(course);
        } else {
            String error = courses.get(0).getERROR();
            if (error != null) {
                courses.get(0).setERROR(courses.get(0).getERROR());
            }
        }
        return courses.stream().findFirst().get();
    }

    public static List<MoodleCourse> find(MoodleCourse _course) throws UnsupportedEncodingException, IOException, MalformedURLException, JSONException, MoodleRestUserException {
        List<MoodleCourse> response = new ArrayList<MoodleCourse>();
        MoodleCourse course = _course;
        String functionName = "core_course_get_courses_by_field";
        String urlParameters = "field=idnumber&value=" + URLEncoder.encode(_course.getIdnumber(), "UTF-8");
        JsonResult result = MoodleRestWebService.call(urlParameters, functionName);
        if (result.equals("ERROR")) {
            course.setERROR(result.getException().getMessage());
            response.add(course);
            return response;
        } else {
            JSONObject jsonResult = new JSONObject(result.getJsonresult());
            JSONArray arr = jsonResult.getJSONArray("courses");
            if (arr.length() != 0) {
                JSONArray array = arr;
                for (int i = 0; i < array.length(); i++) {
                    JSONObject json = array.getJSONObject(i);
                    MoodleCourse usr = new MoodleCourse();
                    usr.setMoodleUserField(json);
                    response.add(usr);
                }
            }
            return response;
        }

    }

    private static List<MoodleCourse> create(MoodleCourse _course) throws MoodleRestUserException, MalformedURLException, JSONException, UnsupportedEncodingException, IOException {
        List<MoodleCourse> response = new ArrayList<>();
        MoodleCourse course = _course;
        String functionName = "core_course_create_courses";
        String urlParameters = "courses[0][fullname]=" + URLEncoder.encode(_course.getFullname(), "UTF-8")
                + "&courses[0][shortname]=" + URLEncoder.encode(_course.getShortname(), "UTF-8")
                + "&courses[0][categoryid]=" + URLEncoder.encode(String.valueOf(_course.getCategoryid()), "UTF-8")
                + "&courses[0][idnumber]=" + URLEncoder.encode(_course.getIdnumber(), "UTF-8")
                + "&courses[0][visible]=" + URLEncoder.encode(String.valueOf(_course.getVisible()), "UTF-8");
        JsonResult result = MoodleRestWebService.call(urlParameters, functionName);
        if (result.equals("ERROR")) {
            course.setERROR(result.getException().getMessage());
            response.add(course);
        } else {
            JSONArray array = new JSONArray(result.getJsonresult());
            for (int i = 0; i < array.length(); i++) {
                JSONObject json = array.getJSONObject(i);
                course.setId(json.getInt("id"));
                response.add(course);
            }
        }
        return response;
    }

}
