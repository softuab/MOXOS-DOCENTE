package com.moxos.uab.model.service.moodle;

import org.json.JSONException;
import org.json.JSONObject;

public class MoodleEnroll {

    private int roleid;   //Role to assign to the user
    private long userid;  //The user that is going to be enrolled
    private int courseid;  //The course to enrol the user role in
    private int timestart; //Timestamp when the enrolment start
    private int timeend; //Timestamp when the enrolment end
    private int suspend; //set to 1 to suspend the enrolment
    private String ERROR;

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public int getTimestart() {
        return timestart;
    }

    public void setTimestart(int timestart) {
        this.timestart = timestart;
    }

    public int getTimeend() {
        return timeend;
    }

    public void setTimeend(int timeend) {
        this.timeend = timeend;
    }

    public int getSuspend() {
        return suspend;
    }

    public void setSuspend(int suspend) {
        this.suspend = suspend;
    }

    public void setMoodleUserField(String jsonresult) throws JSONException {
        JSONObject json = new JSONObject(jsonresult);
        this.setCourseid(json.getInt("courseid"));
        this.setRoleid(json.getInt("roleid"));
        this.setSuspend(json.getInt("suspend"));
        this.setTimeend(json.getInt("timeend"));
        this.setTimestart(json.getInt("timestart"));
        this.setUserid(json.getInt("id"));
    }

    public void setMoodleUserField(JSONObject json) throws JSONException {
        this.setCourseid(json.getInt("courseid"));
        this.setRoleid(json.getInt("roleid"));
        this.setSuspend(json.getInt("suspend"));
        this.setTimeend(json.getInt("timeend"));
        this.setTimestart(json.getInt("timestart"));
        this.setUserid(json.getInt("id"));
    }
}
