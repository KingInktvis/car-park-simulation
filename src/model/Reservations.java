package model;

import java.util.ArrayList;

/**
 * Created by rik on 4/8/16.
 */
public class Reservations {
    private ArrayList<ReservedSpot> reservedSpots;

    public Reservations (){
        reservedSpots = new ArrayList<ReservedSpot>();

    }

    public ArrayList<ReservedSpot> getReservedSpots() {
        return reservedSpots;
    }

    public void addReservation(ReservedSpot reservedSpot){
        reservedSpots.add(reservedSpot);
    }

    public ReservedSpot getSpot(int index){
        return reservedSpots.get(index);
    }

    public int size(){
        return reservedSpots.size();
    }
}
