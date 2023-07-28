package com.surfscribebackend.surfscribe.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notes")
public class Note {
    @Id
    private String id;
    private String text;
    private LocalDateTime timeStamp;

    @DBRef
    private SurfLocation surfLocation;

    public Note() {
        // default constructor required for Spring Data MongoDB
    }

    public Note(String text) {
        this.text = text;
    }

    public Note(String text, LocalDateTime timeStamp) {
        this.text = text;
        this.timeStamp = timeStamp;
    }

    public Note(String text, LocalDateTime timeStamp, SurfLocation surfLocation) {
        this.text = text;
        this.timeStamp = timeStamp;
        this.surfLocation = surfLocation;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public SurfLocation getSurfLocation() {
        return surfLocation;
    }

    public void setSurfLocation(SurfLocation surfLocation) {
        this.surfLocation = surfLocation;
    }
}
