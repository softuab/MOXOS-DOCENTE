/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.Moodle.Entidades;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author FNZABALETAA
 */
public class MoodleCourse {

    private long id;
    private String shortname;
    private int categoryid;
    private int categorysortorder;
    private String fullname;
    private String idnumber;
    private String summary;
    private int summaryformat;
    private String format;
    private int showgrades;
    private int newsitems;
    private int startdate;
    private int numsections;
    private int maxbytes;
    private int showreports;
    private int visible;
    private int hiddensections;
    private int groupmode;
    private int groupmodeforce;
    private int defaultgroupingid;
    private int timecreated;
    private int timemodified;
    private int enablecompletion;
    private int completionstartonenrol;
    private int completionnotify;
    private String lang;
    private String forcetheme;
    private String ERROR;
    private String categoryname;
    
    

    public MoodleCourse(int id) {
        this.id = id;
    }

    public MoodleCourse() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public int getCategorysortorder() {
        return categorysortorder;
    }

    public void setCategorysortorder(int categorysortorder) {
        this.categorysortorder = categorysortorder;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getSummaryformat() {
        return summaryformat;
    }

    public void setSummaryformat(int summaryformat) {
        this.summaryformat = summaryformat;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getShowgrades() {
        return showgrades;
    }

    public void setShowgrades(int showgrades) {
        this.showgrades = showgrades;
    }

    public int getNewsitems() {
        return newsitems;
    }

    public void setNewsitems(int newsitems) {
        this.newsitems = newsitems;
    }

    public int getStartdate() {
        return startdate;
    }

    public void setStartdate(int startdate) {
        this.startdate = startdate;
    }

    public int getNumsections() {
        return numsections;
    }

    public void setNumsections(int numsections) {
        this.numsections = numsections;
    }

    public int getMaxbytes() {
        return maxbytes;
    }

    public void setMaxbytes(int maxbytes) {
        this.maxbytes = maxbytes;
    }

    public int getShowreports() {
        return showreports;
    }

    public void setShowreports(int showreports) {
        this.showreports = showreports;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getHiddensections() {
        return hiddensections;
    }

    public void setHiddensections(int hiddensections) {
        this.hiddensections = hiddensections;
    }

    public int getGroupmode() {
        return groupmode;
    }

    public void setGroupmode(int groupmode) {
        this.groupmode = groupmode;
    }

    public int getGroupmodeforce() {
        return groupmodeforce;
    }

    public void setGroupmodeforce(int groupmodeforce) {
        this.groupmodeforce = groupmodeforce;
    }

    public int getDefaultgroupingid() {
        return defaultgroupingid;
    }

    public void setDefaultgroupingid(int defaultgroupingid) {
        this.defaultgroupingid = defaultgroupingid;
    }

    public int getTimecreated() {
        return timecreated;
    }

    public void setTimecreated(int timecreated) {
        this.timecreated = timecreated;
    }

    public int getTimemodified() {
        return timemodified;
    }

    public void setTimemodified(int timemodified) {
        this.timemodified = timemodified;
    }

    public int getEnablecompletion() {
        return enablecompletion;
    }

    public void setEnablecompletion(int enablecompletion) {
        this.enablecompletion = enablecompletion;
    }

    public int getCompletionstartonenrol() {
        return completionstartonenrol;
    }

    public void setCompletionstartonenrol(int completionstartonenrol) {
        this.completionstartonenrol = completionstartonenrol;
    }

    public int getCompletionnotify() {
        return completionnotify;
    }

    public void setCompletionnotify(int completionnotify) {
        this.completionnotify = completionnotify;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getForcetheme() {
        return forcetheme;
    }

    public void setForcetheme(String forcetheme) {
        this.forcetheme = forcetheme;
    }
    
    
    
    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }
     public void setMoodleUserField(String jsonresult) throws JSONException {
        JSONObject json = new JSONObject(jsonresult);
        this.setFullname(json.getString("fullname"));
        this.setShortname(json.getString("shortname"));
        this.setIdnumber(json.getString("idnumber"));
        this.setCategoryname(json.getString("categoryname")); 
        this.setCategoryid(json.getInt("categoryid"));
        this.setId(json.getLong("id"));
    }

    public void setMoodleUserField(JSONObject json) throws JSONException {
        this.setFullname(json.getString("fullname"));
        this.setShortname(json.getString("shortname"));
        this.setIdnumber(json.getString("idnumber"));
        this.setCategoryname(json.getString("categoryname")); 
        this.setCategoryid(json.getInt("categoryid"));
        this.setId(json.getLong("id"));
    }
    
      public void setMoodleCourseField(JSONObject json) throws JSONException {
        this.setFullname(json.getString("fullname"));
        this.setShortname(json.getString("shortname"));
        this.setIdnumber(json.getString("idnumber")); 
        this.setId(json.getLong("id"));
    }
}
