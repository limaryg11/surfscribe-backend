package com.surfscribebackend.surfscribe.backend.repository;

import com.surfscribebackend.surfscribe.backend.model.SurfLocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SurfLocationRepository extends MongoRepository<SurfLocation, String> {
}

