package com.surfscribebackend.surfscribe.backend.repository;

import com.surfscribebackend.surfscribe.backend.model.Note;
import com.surfscribebackend.surfscribe.backend.model.SurfLocation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {
    // You can define custom query methods here if needed
    List<Note> findNotesBySurfLocationId(String surfLocationId);
}