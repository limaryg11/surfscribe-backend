package com.surfscribebackend.surfscribe.backend.controller;

import com.surfscribebackend.surfscribe.backend.LocationIQService;
import com.surfscribebackend.surfscribe.backend.exceptions.ResourceNotFoundException;
import com.surfscribebackend.surfscribe.backend.model.GeoCoordinates;
import com.surfscribebackend.surfscribe.backend.model.Note;
import com.surfscribebackend.surfscribe.backend.model.SurfLocation;
import com.surfscribebackend.surfscribe.backend.repository.NoteRepository;
import com.surfscribebackend.surfscribe.backend.repository.SurfLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;

@RestController
@RequestMapping("/surf-locations")
public class SurfLocationController {
    private final SurfLocationRepository surfLocationRepository;
    private final NoteRepository noteRepository;
    private final LocationIQService locationIQService;

    @Autowired
    public SurfLocationController(SurfLocationRepository surfLocationRepository, NoteRepository noteRepository, LocationIQService locationIQService) {
        this.surfLocationRepository = surfLocationRepository;
        this.noteRepository = noteRepository;
        this.locationIQService = locationIQService;

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
        GeoCoordinates coordinates = locationIQService.fetchCoordinates(surfLocation.getName());
        surfLocation.setLatitude(coordinates.getLat());
        surfLocation.setLongitude(coordinates.getLon());
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

//    @PostMapping("/{id}/upload-photos")
//    public ResponseEntity<?> uploadPhotos(@PathVariable("id") String surfLocationId,
//                                          @RequestParam("photos") MultipartFile[] photos) {
//        // retrieve the surf location by its ID from the database
//        SurfLocation surfLocation = surfLocationRepository.findById(surfLocationId)
//                .orElseThrow(() -> new ResourceNotFoundException("Surf Location not found with ID: " + surfLocationId));
//
//        // Save the photos to the server
//        try {
//            String uploadDirectory = "uploads";
//
//            // Create a list to store photo paths
//            List<String> photoPaths = new ArrayList<>();
//
//            for (MultipartFile photo : photos) {
//                File photoFile = new File(uploadDirectory + photo.getOriginalFilename());
//                photo.transferTo(photoFile);
//                photoPaths.add(uploadDirectory + photo.getOriginalFilename());
//            }
//
//            // Save the photo paths in the database
//            surfLocation.setPhotoPaths(photoPaths);
//            surfLocationRepository.save(surfLocation);
//
//            return ResponseEntity.ok().build();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }





    @PutMapping("/{id}")
    public ResponseEntity<SurfLocation> updateSurfLocation(@PathVariable String id, @RequestBody SurfLocation updatedSurfLocation) {
        GeoCoordinates coordinates = locationIQService.fetchCoordinates(updatedSurfLocation.getName());
        updatedSurfLocation.setLatitude(coordinates.getLat());
        updatedSurfLocation.setLongitude(coordinates.getLon());

        Optional<SurfLocation> existingSurfLocation = surfLocationRepository.findById(id);
        if (existingSurfLocation.isPresent()) {
            updatedSurfLocation.setId(id);
            SurfLocation savedSurfLocation = surfLocationRepository.save(updatedSurfLocation);
            return ResponseEntity.ok(savedSurfLocation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SurfLocation> patchSurfLocation(@PathVariable String id, @RequestBody Map<String, Object> patchRequest) {
        // Find the existing SurfLocation in the database
        SurfLocation existingSurfLocation = surfLocationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SurfLocation not found with id: " + id));

        // Update the fields from the patchRequest if they are present
        if (patchRequest.containsKey("name")) {
            existingSurfLocation.setName((String) patchRequest.get("name"));
        }
        if (patchRequest.containsKey("description")) {
            existingSurfLocation.setDescription((String) patchRequest.get("description"));
        }

        // Save the updated SurfLocation to the database
        SurfLocation savedSurfLocation = surfLocationRepository.save(existingSurfLocation);

        return ResponseEntity.ok(savedSurfLocation);
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
