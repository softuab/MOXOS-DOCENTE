package com.moxos.uab.model.service.moodle;

import org.json.JSONException;
import org.json.JSONObject;

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

