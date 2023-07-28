package com.surfscribebackend.surfscribe.backend.controller;

import com.surfscribebackend.surfscribe.backend.exceptions.ResourceNotFoundException;
import com.surfscribebackend.surfscribe.backend.model.Note;
import com.surfscribebackend.surfscribe.backend.model.SurfLocation;
import com.surfscribebackend.surfscribe.backend.repository.NoteRepository;
import com.surfscribebackend.surfscribe.backend.repository.SurfLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/surf-locations")
public class SurfLocationController {
    private final SurfLocationRepository surfLocationRepository;
    private final NoteRepository noteRepository;

    @Autowired
    public SurfLocationController(SurfLocationRepository surfLocationRepository, NoteRepository noteRepository) {
        this.surfLocationRepository = surfLocationRepository;
        this.noteRepository = noteRepository;
    }

    @GetMapping
    public ResponseEntity<List<SurfLocation>> getAllSurfLocations() {
        List<SurfLocation> surfLocations = surfLocationRepository.findAll();
        return ResponseEntity.ok(surfLocations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurfLocation> getSurfLocationById(@PathVariable String id) {
        Optional<SurfLocation> surfLocation = surfLocationRepository.findById(id);
        return surfLocation.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

//    @PostMapping
//    public ResponseEntity<SurfLocation> addSurfLocation(@RequestBody SurfLocation surfLocation) {
//        Note defaultNote = new Note("Default Note", LocalDateTime.now());
//        surfLocation.getNotes().add(defaultNote);
//        SurfLocation savedSurfLocation = surfLocationRepository.save(surfLocation);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedSurfLocation);
//    }
    @PostMapping
    public ResponseEntity<SurfLocation> addSurfLocation(@RequestBody SurfLocation surfLocation) {
        if (surfLocation.getNotes() == null) {
            surfLocation.setNotes(new ArrayList<>());
        }
        // Create a default Note and save it to the database
        Note defaultNote = new Note("Default Note", LocalDateTime.now());
        noteRepository.save(defaultNote);

        // Add the default Note to the SurfLocation
        surfLocation.getNotes().add(defaultNote);

        // Save the SurfLocation
        SurfLocation savedSurfLocation = surfLocationRepository.save(surfLocation);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedSurfLocation);
    }


    @PutMapping("/{id}")
    public ResponseEntity<SurfLocation> updateSurfLocation(@PathVariable String id, @RequestBody SurfLocation updatedSurfLocation) {
        Optional<SurfLocation> existingSurfLocation = surfLocationRepository.findById(id);
        if (existingSurfLocation.isPresent()) {
            updatedSurfLocation.setId(id);
            SurfLocation savedSurfLocation = surfLocationRepository.save(updatedSurfLocation);
            return ResponseEntity.ok(savedSurfLocation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurfLocation(@PathVariable String id) {
        Optional<SurfLocation> existingSurfLocation = surfLocationRepository.findById(id);
        if (existingSurfLocation.isPresent()) {
            surfLocationRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
