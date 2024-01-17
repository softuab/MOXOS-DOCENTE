package com.moxos.uab.model.service.moodle;

import org.json.JSONException;
import org.json.JSONObject;

public class MoodleGroup {

    private int id;
    private String name;
    private String idnumber;
    private String description;
    private int descriptionformat;
    private long parent;
    private long sortorder;
    private long coursecount;
    private int visible;

    private int visibleold;
    private long timemodified;
    private long depth;
    private String path;
    private String theme;
    private String ERROR;

    public MoodleGroup(int id) {
        this.id = id;
    }

    public MoodleGroup() {
    }

    public void setMoodleUserField(String jsonresult) throws JSONException {
        JSONObject json = new JSONObject(jsonresult);
        this.setId(json.getInt("id"));
        this.setName(json.getString("name"));
        this.setIdnumber(json.getString("idnumber"));
        this.setPath(json.getString("path"));
    }

    public void setMoodleUserField(JSONObject json) throws JSONException {
        this.setId(json.getInt("id"));
        this.setName(json.getString("name"));
        this.setIdnumber(json.getString("idnumber"));
        this.setPath(json.getString("path"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDescriptionformat() {
        return descriptionformat;
    }

    public void setDescriptionformat(int descriptionformat) {
        this.descriptionformat = descriptionformat;
    }

    public long getParent() {
        return parent;
    }

    public void setParent(long parent) {
        this.parent = parent;
    }

    public long getSortorder() {
        return sortorder;
    }

    public void setSortorder(long sortorder) {
        this.sortorder = sortorder;
    }

    public long getCoursecount() {
        return coursecount;
    }

    public void setCoursecount(long coursecount) {
        this.coursecount = coursecount;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getVisibleold() {
        return visibleold;
    }

    public void setVisibleold(int visibleold) {
        this.visibleold = visibleold;
    }

    public long getTimemodified() {
        return timemodified;
    }

    public void setTimemodified(long timemodified) {
        this.timemodified = timemodified;
    }

    public long getDepth() {
        return depth;
    }

    public void setDepth(long depth) {
        this.depth = depth;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }
}
