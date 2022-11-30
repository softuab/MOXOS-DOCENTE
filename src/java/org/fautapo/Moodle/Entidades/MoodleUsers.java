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
 * @author hp
 */
public class MoodleUsers {

    public static final boolean EMAIL_FORMAT_NONE = false;
    public static final boolean EMAIL_FORMAT_HTML = true;

    private long id = -1;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String idnumber;
    private boolean mailformat = EMAIL_FORMAT_HTML;
    private String ERROR;
    private boolean registrado_academico;
    private boolean registrado_moodle;

    /**
     * Constructor for bean requirements
     */
    public MoodleUsers() {
    }

    /**
     * Constructor which also sets the MoodleUser id attribute. Use this
     * constructor when fetching user information from Moodle. Once the users
     * information has been populated from Moodle it can then be used as a
     * template to update the same users information.
     *
     * @param id long
     */
    public MoodleUsers(String idnumber) {
        this.idnumber = idnumber;
    }

    public MoodleUsers(String username, String password, String firstname, String lastname, String email, String idnumber) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.idnumber = idnumber;
    }

    public void setMoodleUserField(String jsonresult) throws JSONException {
        JSONObject json = new JSONObject(jsonresult);
        this.setEmail(json.getString("email"));
        this.setFirstname(json.getString("firstname"));
        this.setIdnumber(json.getString("idnumber"));
        this.setLastname(json.getString("lastname"));
        this.setPassword(json.getString("password"));
        this.setUsername(json.getString("username"));
        this.setId(json.getLong("id"));
    }

    public void setMoodleUserField(JSONObject json) throws JSONException {
        this.setEmail(json.getString("email"));
        this.setFirstname(json.getString("firstname"));
        if (!json.isNull("idnumber")) {
            this.setIdnumber(json.getString("idnumber"));
        } else {
            this.setIdnumber("");
        }
        this.setLastname(json.getString("lastname"));
        this.setUsername(json.getString("username"));
        this.setId(json.getLong("id"));
    }

    public void setMoodleUserFieldUserName(JSONObject json) throws JSONException {
        this.setEmail(json.getString("email"));
        this.setFirstname(json.getString("firstname"));
        if (!json.isNull("idnumber")) {
            this.setIdnumber(json.getString("idnumber"));
        } else {
            this.setIdnumber("");
        }
        this.setLastname(json.getString("lastname"));
        this.setUsername(json.getString("username"));
        this.setId(json.getLong("id"));
    }

    public boolean isRegistrado_academico() {
        return registrado_academico;
    }

    public void setRegistrado_academico(boolean registrado_academico) {
        this.registrado_academico = registrado_academico;
    }

    public boolean isRegistrado_moodle() {
        return registrado_moodle;
    }

    public void setRegistrado_moodle(boolean registrado_moodle) {
        this.registrado_moodle = registrado_moodle;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }

}
