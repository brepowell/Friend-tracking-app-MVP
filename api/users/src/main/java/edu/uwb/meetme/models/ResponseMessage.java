package edu.uwb.meetme.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseMessage {

    @JsonProperty
    private String message;

    public ResponseMessage() {
        message = "";
    }

    public ResponseMessage(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
