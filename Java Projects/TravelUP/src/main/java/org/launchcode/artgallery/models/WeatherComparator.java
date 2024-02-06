package org.launchcode.artgallery.models;

import java.util.Comparator;

public class WeatherComparator implements Comparator<Weather> {

    @Override
    public int compare(Weather a1, Weather a2) {
        return a1.getName().compareTo(a2.getName());
    }
}