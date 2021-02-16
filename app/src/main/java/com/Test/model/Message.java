package com.Test.model;

public class Message {

    String content;
    Boolean isSend;

    public Message(String content, Boolean isSend) {
        this.content = content;
        this.isSend = isSend;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsSend() {
        return isSend;
    }

    public void setIsSend(Boolean isSend) {
        isSend = isSend;
    }
}
