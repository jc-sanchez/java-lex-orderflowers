
package com.jcsanchez.helloworld.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class LexRequest {

    @SerializedName("currentIntent")
    @Expose
    private CurrentIntent currentIntent;

    @SerializedName("bot")
    @Expose
    private Bot bot;

    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("inputTranscript")
    @Expose
    private String inputTranscript;

    @SerializedName("invocationSource")
    @Expose
    private String invocationSource;

    @SerializedName("outputDialogMode")
    @Expose
    private String outputDialogMode;

    @SerializedName("messageVersion")
    @Expose
    private String messageVersion;

    @SerializedName("sessionAttributes")
    @Expose
    private Map<String, String> sessionAttributes;

    public CurrentIntent getCurrentIntent() {
        return currentIntent;
    }

    public void setCurrentIntent(CurrentIntent currentIntent) {
        this.currentIntent = currentIntent;
    }

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInputTranscript() {
        return inputTranscript;
    }

    public void setInputTranscript(String inputTranscript) {
        this.inputTranscript = inputTranscript;
    }

    public String getInvocationSource() {
        return invocationSource;
    }

    public void setInvocationSource(String invocationSource) {
        this.invocationSource = invocationSource;
    }

    public String getOutputDialogMode() {
        return outputDialogMode;
    }

    public void setOutputDialogMode(String outputDialogMode) {
        this.outputDialogMode = outputDialogMode;
    }

    public String getMessageVersion() {
        return messageVersion;
    }

    public void setMessageVersion(String messageVersion) {
        this.messageVersion = messageVersion;
    }

    public Map<String, String> getSessionAttributes() {
        return sessionAttributes;
    }

    public void setSessionAttributes(Map<String, String> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

}
