package com.moxos.uab.model.service.moodle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MoodleGroupService {

    public MoodleGroupService() {
    }

    public static MoodleGroup GetGroup(String idnumber) throws IOException, UnsupportedEncodingException, MalformedURLException, JSONException, MoodleRestUserException {
        List<MoodleGroup> response = new ArrayList<MoodleGroup>();
        MoodleGroup group = new MoodleGroup();
        group.setIdnumber(idnumber);
        response = find(group);
        return response.get(0);
    }

    public static List<MoodleGroup> find(MoodleGroup group) throws UnsupportedEncodingException, IOException, MalformedURLException, JSONException, MoodleRestUserException {
        List<MoodleGroup> response = new ArrayList<MoodleGroup>();
        MoodleGroup groups = group;
        String functionName = "core_course_get_categories";
        String urlParameters = "criteria[0][key]=idnumber&criteria[0][value]=" + URLEncoder.encode(group.getIdnumber(), "UTF-8");
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
                    MoodleGroup usr = new MoodleGroup();
                    usr.setMoodleUserField(json);
                    response.add(usr);
                }
            }
            return response;
        }

    }

}
