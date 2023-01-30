package ru.bul.springs.AirRent.util;

import ru.bul.springs.AirRent.models.Flight;

import java.util.Comparator;

public class FlightPriceComparator implements Comparator<Flight> {
    @Override
    public int compare(Flight o1, Flight o2) {
        return o1.getPrice()-o2.getPrice();
    }
}
