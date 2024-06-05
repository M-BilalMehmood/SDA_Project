package buisness.services;

import buisness.models.Location;

public class LocationService {
    public Location getCurrentLocation() {
        // Logic to get current location using GPS or other methods
        // For now, returning a dummy location
        return new Location(33.6844, 73.0479);
    }
}