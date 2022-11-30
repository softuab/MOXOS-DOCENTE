/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.Moodle.Servicios; 
import org.fautapo.Moodle.Exception.MoodleException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author FNZABALETAA
 */
public class JsonResult {

    public JsonResult(String result) throws JSONException {
        this.result = result;
        if (result.contains("exception")) {
            MoodleException exception = new MoodleException();
            JSONObject json = new JSONObject(result);
            exception.setException(json.getString("exception"));
            exception.setErrorcode(json.getString("errorcode"));
            exception.setMessage(json.getString("message"));
            this.exception = exception;
            this.result = "ERROR";
        } else {
            this.jsonresult = result;
            this.result = "SUCCES";
        }
    }

    private MoodleException exception;
    private String jsonresult;
    private String result;

    public String getJsonresult() {
        return jsonresult;
    }

    public String getResult() {
        return result;
    }
    public MoodleException getException() {
        return exception;
    }

}
