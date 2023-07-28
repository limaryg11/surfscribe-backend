package com.surfscribebackend.surfscribe.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.awt.*;

@Document(collection="surfLocations")
public class SurfLocation {
    @Id
    private String id;
    private String name;
    private String description;
//    private String userId

    public SurfLocation() {

    }

    public SurfLocation(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SurfLocation{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description=" + description +
                '}';
    }
}
