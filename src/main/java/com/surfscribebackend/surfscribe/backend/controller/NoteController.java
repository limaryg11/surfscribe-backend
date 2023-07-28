package com.surfscribebackend.surfscribe.backend.controller;

import com.surfscribebackend.surfscribe.backend.exceptions.ResourceNotFoundException;
import com.surfscribebackend.surfscribe.backend.model.Note;
import com.surfscribebackend.surfscribe.backend.model.SurfLocation;
import com.surfscribebackend.surfscribe.backend.repository.NoteRepository;
import com.surfscribebackend.surfscribe.backend.repository.SurfLocationRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/surf-locations")
public class NoteController {

    private final NoteRepository noteRepository;
    private final SurfLocationRepository surfLocationRepository;

    @Autowired
    public NoteController(NoteRepository noteRepository, SurfLocationRepository surfLocationRepository) {
        this.noteRepository = noteRepository;
        this.surfLocationRepository = surfLocationRepository;
    }
    // Create a new note for a specific surf location
//    @PostMapping("/{surfLocationId}/notes")
//    public ResponseEntity<Note> createNote(
//            @PathVariable String surfLocationId,
//            @RequestBody Note newNote
//    ) {
//        SurfLocation surfLocation = surfLocationRepository.findById(surfLocationId)
//                .orElseThrow(() -> new ResourceNotFoundException("SurfLocation not found with id: " + surfLocationId));
//
//        newNote.setTimeStamp(LocalDateTime.now());
//        surfLocation.addNote(newNote);
//        surfLocationRepository.save(surfLocation);
//
//        return ResponseEntity.ok(newNote);
//    }

//    @PostMapping("/{surfLocationId}/notes")
//    public ResponseEntity<Note> addNoteToSurfLocation(
//            @PathVariable String surfLocationId,
//            @RequestBody Note newNote
//    ) {
//        SurfLocation surfLocation = surfLocationRepository.findById(surfLocationId)
//                .orElseThrow(() -> new ResourceNotFoundException("SurfLocation not found with id: " + surfLocationId));
//
//        surfLocation.addNote(newNote);
//        surfLocationRepository.save(surfLocation);
//
//        return ResponseEntity.ok(newNote);
//    }

    @PostMapping("/{surfLocationId}/notes")
    public ResponseEntity<Note> addNoteToSurfLocation(
            @PathVariable String surfLocationId,
            @RequestBody Note note
    ) {
        Optional<SurfLocation> optionalSurfLocation = surfLocationRepository.findById(surfLocationId);
        if (optionalSurfLocation.isPresent()) {
            SurfLocation surfLocation = optionalSurfLocation.get();

            Note savedNote = noteRepository.save(note);
            surfLocation.getNotes().add(savedNote);
            surfLocationRepository.save(surfLocation);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedNote);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all notes for a specific surf location
    @GetMapping("/{surfLocationId}/notes")
    public ResponseEntity<List<Note>> getAllNotesForSurfLocation(@PathVariable String surfLocationId) {
        SurfLocation surfLocation = surfLocationRepository.findById(surfLocationId)
                .orElseThrow(() -> new ResourceNotFoundException("SurfLocation not found with id: " + surfLocationId));

        List<Note> notes = surfLocation.getNotes();
        return ResponseEntity.ok(notes);
    }

    // Update the text of a note
    @PutMapping("/{surfLocationId}/notes/{noteId}")
    public ResponseEntity<Note> updateNoteText(
            @PathVariable String surfLocationId,
            @PathVariable String noteId,
            @RequestBody Note updatedNote
    ) {
        Note existingNote = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + noteId));

        existingNote.setText(updatedNote.getText());
        existingNote.setTimeStamp(LocalDateTime.now());
        noteRepository.save(existingNote);

        return ResponseEntity.ok(existingNote);
    }

    // Delete a note
    @DeleteMapping("/{surfLocationId}/notes/{noteId}")
    public ResponseEntity<Void> deleteNote(
            @PathVariable String surfLocationId,
            @PathVariable String noteId
    ) {
        Note existingNote = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + noteId));

        noteRepository.delete(existingNote);

        return ResponseEntity.noContent().build();
    }

    // Delete all notes within a specific surf location
    @DeleteMapping("/{surfLocationId}/notes")
    public ResponseEntity<Void> deleteAllNotesForSurfLocation(@PathVariable String surfLocationId) {
        SurfLocation surfLocation = surfLocationRepository.findById(surfLocationId)
                .orElseThrow(() -> new ResourceNotFoundException("SurfLocation not found with id: " + surfLocationId));

        surfLocation.getNotes().clear();
        surfLocationRepository.save(surfLocation);

        return ResponseEntity.noContent().build();
    }
}












//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/notes")
//public class NoteController {
//    private final NoteRepository noteRepository;
//    private final SurfLocationRepository surfLocationRepository;
//
//    @Autowired
//    public NoteController(NoteRepository noteRepository, SurfLocationRepository surfLocationRepository) {
//        this.noteRepository = noteRepository;
//        this.surfLocationRepository = surfLocationRepository;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Note>> getAllNotes() {
//        List<Note> notes = noteRepository.findAll();
//        return new ResponseEntity<>(notes, HttpStatus.OK);
//    }
//
//    // Get a specific note by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<Note> getNoteById(@PathVariable String id) {
//        Optional<Note> optionalNote = noteRepository.findById(id);
//        return optionalNote.map(note -> new ResponseEntity<>(note, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    // Create a new note
//    @PostMapping
//    public ResponseEntity<Note> createNote(@RequestBody Note note) {
//        // Ensure that the surfLocation property references an existing SurfLocation
//        SurfLocation existingSurfLocation = surfLocationRepository.findById(note.getSurfLocation().getId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid SurfLocation ID"));
//
//        note.setSurfLocation(existingSurfLocation);
//
//        Note savedNote = noteRepository.save(note);
//        return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
//    }
//
//    // Update an existing note
//    @PutMapping("/{id}")
//    public ResponseEntity<Note> updateNote(@PathVariable String id, @RequestBody Note note) {
//        Optional<Note> optionalNote = noteRepository.findById(id);
//        if (optionalNote.isPresent()) {
//            Note existingNote = optionalNote.get();
//            existingNote.setText(note.getText());
//            existingNote.setTimeStamp(note.getTimeStamp());
//            existingNote.setSurfLocation(note.getSurfLocation());
//            Note updatedNote = noteRepository.save(existingNote);
//            return new ResponseEntity<>(updatedNote, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    // Delete a note
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Note> deleteNote(@PathVariable String id) {
//        Optional<Note> optionalNote = noteRepository.findById(id);
//        if (optionalNote.isPresent()) {
//            Note note = optionalNote.get();
//            noteRepository.delete(note);
//            return new ResponseEntity<>(note, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//}
