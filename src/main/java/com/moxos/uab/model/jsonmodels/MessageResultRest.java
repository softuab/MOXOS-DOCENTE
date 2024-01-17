package com.moxos.uab.model.jsonmodels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageResultRest {

    @JsonProperty("Result")
    private boolean result;
    @JsonProperty("Message")
    private String message;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
