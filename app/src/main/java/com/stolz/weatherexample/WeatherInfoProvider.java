package com.stolz.weatherexample;

import java.util.ArrayList;
import java.util.List;

public class WeatherInfoProvider {

    private static WeatherInfoProvider sWeatherInfoProvider;
    private List<City> mCities;

    public static WeatherInfoProvider getInstance() {
        if (sWeatherInfoProvider == null) {
            sWeatherInfoProvider = new WeatherInfoProvider();
        }
        return sWeatherInfoProvider;
    }

    private WeatherInfoProvider() {
        mCities = new ArrayList<>();
        mCities.add(new City(77096));
        mCities.add(new City(78751));
        mCities.add(new City(77584));
    }

    public List<City> getCities() {
        return mCities;
    }

    public City getCity(int zipcode) {
        for (City current : mCities) {
            if (current.getZipcode() == zipcode) {
                return current;
            }
        }
        return null;
    }
}
