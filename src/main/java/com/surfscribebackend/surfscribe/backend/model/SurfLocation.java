package com.surfscribebackend.surfscribe.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonMerge;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Document(collection = "surfLocations")
public class SurfLocation {
    @Id
    private String id;
    @JsonMerge
    private String name;
    @JsonMerge
    private String description;

    @JsonManagedReference
    @DBRef
    @JsonMerge
    private List<Note> notes;

//    private String userId


    public SurfLocation() {

    }

    public SurfLocation(String name, String description) {
        this.name = name;
        this.description = description;
//        Note defaultNote = new Note("Default Note", LocalDateTime.now());
//        this.notes.add(defaultNote);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter for notes

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    // Method to add a note to the list of notes
    public void addNote(Note note) {
        this.notes.add(note);
    }

    // Setter for timestamp of a note
    public void setTimestampForNote(String noteId, LocalDateTime timestamp) {
        for (Note note : notes) {
            if (note.getId().equals(noteId)) {
                note.setTimeStamp(timestamp);
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "SurfLocation{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", notes=" + notes +
                '}';
    }


}
