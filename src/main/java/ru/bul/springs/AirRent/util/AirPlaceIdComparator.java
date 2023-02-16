package ru.bul.springs.AirRent.util;

import ru.bul.springs.AirRent.models.AirTicketPlace;

import java.util.Comparator;

public class AirPlaceIdComparator implements Comparator<AirTicketPlace> {
    @Override
    public int compare(AirTicketPlace o1, AirTicketPlace o2) {
        return o1.getId()-o2.getId();
    }
}
