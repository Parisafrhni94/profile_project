package org.launchcode.artgallery.models;

import java.util.Comparator;

public class CountryComparator implements Comparator<CountryInfo> {

    @Override
    public int compare(CountryInfo a1, CountryInfo a2) {
        return a1.getCommonName().compareTo(a2.getCommonName());
    }
}
