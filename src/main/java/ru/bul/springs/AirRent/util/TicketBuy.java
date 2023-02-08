package ru.bul.springs.AirRent.util;

public interface TicketBuy {

    public void CreateTicket(int idPer, int idFl);

    public void UpdateTicketInputBank(int idPer,int idTicket);

    public void BuyTicketAndConfThreeSec(int tick);
}
