package com.surfscribebackend.surfscribe.backend.controller;

import com.surfscribebackend.surfscribe.backend.model.SurfLocation;
import com.surfscribebackend.surfscribe.backend.repository.SurfLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/surf-locations")
public class SurfLocationController {
    private final SurfLocationRepository surfLocationRepository;

    @Autowired
    public SurfLocationController(SurfLocationRepository surfLocationRepository) {
        this.surfLocationRepository = surfLocationRepository;
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

    @PostMapping
    public ResponseEntity<SurfLocation> addSurfLocation(@RequestBody SurfLocation surfLocation) {
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
