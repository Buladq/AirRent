package ru.bul.springs.AirRent.util;

import ru.bul.springs.AirRent.models.AirTicketRent;

import java.util.Comparator;

public class AirRentIdComparator implements Comparator<AirTicketRent> {
    @Override
    public int compare(AirTicketRent o1, AirTicketRent o2) {
        return o1.getId()-o2.getId();
    }
}
