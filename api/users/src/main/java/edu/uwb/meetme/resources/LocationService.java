package edu.uwb.meetme.resources;

import edu.uwb.meetme.models.Location;
import edu.uwb.meetme.models.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public void updateLocation(Location location) {
        locationRepository.save(location);
    }
}
