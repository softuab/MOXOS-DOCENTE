/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.Moodle.Exception;

/**
 *
 * @author FNZABALETAA
 */
public class MoodleRestUserException extends MoodleRestException {

    public static final String TOKEN_NULL = "Tokenno puede ir vacio";
    public static final String USERNAME_NULL = "Usernameno puede ir vacio";
    public static final String PASSWORD_NULL = "Passwordno puede ir vacio";
    public static final String FIRSTNAME_NULL = "Firstnameno puede ir vacio";
    public static final String LASTNAME_NULL = "Lastnameno puede ir vacio";
    public static final String EMAIL_NULL = "Emailno puede ir vacio";
    public static final String URL_NULL = "URLno puede ir vacio";
    public static final String USER_NULL = "Userno puede ir vacio";
    public static final String INVALID_USERID = "id incorrecto";
    public static final String INVALID_USER = "usuario invalido";

    public MoodleRestUserException() {
    }

    public MoodleRestUserException(String msg) {
        super(msg); //To change body of generated methods, choose Tools | Templates.
    }

}
