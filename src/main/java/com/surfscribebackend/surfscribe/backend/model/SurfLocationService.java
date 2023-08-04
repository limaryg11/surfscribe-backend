package com.surfscribebackend.surfscribe.backend.model;

import com.surfscribebackend.surfscribe.backend.LocationIQService;
import com.surfscribebackend.surfscribe.backend.exceptions.EntityNotFoundException;
import com.surfscribebackend.surfscribe.backend.exceptions.ResourceNotFoundException;
import com.surfscribebackend.surfscribe.backend.repository.SurfLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SurfLocationService {

    private final SurfLocationRepository surfLocationRepository;
    private final LocationIQService locationIQService;

    @Autowired
    public SurfLocationService(SurfLocationRepository surfLocationRepository, LocationIQService locationIQService) {
        this.surfLocationRepository = surfLocationRepository;
        this.locationIQService = locationIQService;
    }

    public List<SurfLocation> getAllSurfLocations() {
        return surfLocationRepository.findAll();
    }

    public SurfLocation getSurfLocationById(String id) {
        return surfLocationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SurfLocation id not found"));
    }

    public SurfLocation addSurfLocation(SurfLocation surfLocation) {
        // Fetch latitude and longitude using LocationIQ API
        GeoCoordinates coordinates = locationIQService.fetchCoordinates(surfLocation.getName());
        surfLocation.setLatitude(coordinates.getLat());
        surfLocation.setLongitude(coordinates.getLon());

        return surfLocationRepository.save(surfLocation);
    }

    public void deleteSurfLocation(String id) {
        surfLocationRepository.deleteById(id);
    }

    public SurfLocation updateSurfLocation(String id, SurfLocation updatedSurfLocation) {
        if (!surfLocationRepository.existsById(id)) {
            throw new EntityNotFoundException("SurfLocation", id);
        }

        // Update latitude and longitude using LocationIQ API
        GeoCoordinates coordinates = locationIQService.fetchCoordinates(updatedSurfLocation.getName());
        updatedSurfLocation.setLatitude(coordinates.getLat());
        updatedSurfLocation.setLongitude(coordinates.getLon());

        updatedSurfLocation.setId(id);
        return surfLocationRepository.save(updatedSurfLocation);
    }
}
