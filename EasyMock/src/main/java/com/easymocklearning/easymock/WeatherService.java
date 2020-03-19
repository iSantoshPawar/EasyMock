package com.easymocklearning.easymock;

public interface WeatherService {
    void populateTemperature(Location location) throws ServiceUnavailableException;
}
