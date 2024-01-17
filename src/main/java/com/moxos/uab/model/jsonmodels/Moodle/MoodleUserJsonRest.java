package com.moxos.uab.model.jsonmodels.Moodle;

import lombok.Data;

@Data
public class MoodleUserJsonRest {
    private long id = -1;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String idnumber;
    private boolean registrado_academico;
    private boolean registrado_moodle;
}
