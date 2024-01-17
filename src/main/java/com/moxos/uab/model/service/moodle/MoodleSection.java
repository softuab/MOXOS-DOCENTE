package com.moxos.uab.model.service.moodle;

import org.json.JSONException;
import org.json.JSONObject;

public class MoodleSection {

    private long id;
    private String name;
    private int section;
    private String ERROR;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }

    public void setMoodleSectionField(JSONObject json) throws JSONException {
        this.setId(json.getLong("id"));
        this.setName(json.getString("name"));
        this.setSection(json.getInt("section"));
    }
}
