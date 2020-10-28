package com.flink.cep;

public class LoginEvent {
    private Long id;
    private String username;
    private String eventType;
    private Long eventTime;

    public LoginEvent() {
    }

    public LoginEvent(Long id, String username, String eventType, Long eventTime) {
        this.id = id;
        this.username = username;
        this.eventType = eventType;
        this.eventTime = eventTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getEventTime() {
        return eventTime;
    }

    public void setEventTime(Long eventTime) {
        this.eventTime = eventTime;
    }
}
