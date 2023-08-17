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
@CrossOrigin(origins={"https://main.d2o4jnykp1k7oq.amplifyapp.com/", "http://main.d2o4jnykp1k7oq.amplifyapp.com/",
        "http://www.surfscribe.org", "https://www.surfscribe.org", "http://surfscribe.org", "https://surfscribe.org",
        "http://localhost:3000", "https://localhost:3000"})
@RequestMapping("/surf-locations")
public class NoteController {

    private final NoteRepository noteRepository;
    private final SurfLocationRepository surfLocationRepository;

    @Autowired
    public NoteController(NoteRepository noteRepository, SurfLocationRepository surfLocationRepository) {
        this.noteRepository = noteRepository;
        this.surfLocationRepository = surfLocationRepository;
    }

    @PostMapping("/{surfLocationId}/notes")
    public ResponseEntity<Note> addNoteToSurfLocation(
            @PathVariable String surfLocationId,
            @RequestBody Note note
    ) {
        Optional<SurfLocation> optionalSurfLocation = surfLocationRepository.findById(surfLocationId);
        if (optionalSurfLocation.isPresent()) {
            SurfLocation surfLocation = optionalSurfLocation.get();

            note.setTimeStamp(LocalDateTime.now());

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
