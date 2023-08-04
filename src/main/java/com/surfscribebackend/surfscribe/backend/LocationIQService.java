package com.surfscribebackend.surfscribe.backend;
import com.surfscribebackend.surfscribe.backend.model.GeoCoordinates;
import com.surfscribebackend.surfscribe.backend.model.LocationIQResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocationIQService {

    private final RestTemplate restTemplate;
    private final String locationIQApiKey;
    private final String locationIQBaseUrl;

    public LocationIQService(RestTemplate restTemplate, @Value("${locationiq.api.key}") String locationIQApiKey, @Value("${locationiq.api.baseurl}") String locationIQBaseUrl) {
        this.restTemplate = restTemplate;
        this.locationIQApiKey = locationIQApiKey;
        this.locationIQBaseUrl = locationIQBaseUrl;
    }

    public GeoCoordinates fetchCoordinates(String locationName) {
        String apiUrl = locationIQBaseUrl + "search.php?key=" + locationIQApiKey + "&q=" + locationName + "&format=json";

        LocationIQResponse[] response = restTemplate.getForObject(apiUrl, LocationIQResponse[].class);

        if (response != null && response.length > 0) {
            double lat = response[0].getLat();
            double lon = response[0].getLon();
            return new GeoCoordinates(lat, lon);
        } else {
            throw new RuntimeException("Unable to fetch coordinates for location: " + locationName);
        }
    }
}



