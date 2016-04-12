package controller;

import model.*;

/**
 * Created by rik on 4/11/16.
 */
public class ReservationController {
    private Reservations reservations;
    private SimulatorNotView simulatorNotView;
    private Time time;

    public ReservationController(Reservations reservations, SimulatorNotView simulatorNotView, Time time){
        this.reservations = reservations;
        this.simulatorNotView = simulatorNotView;
        this.time = time;
    }

    public void reserve(Time startTime, Time stopTime){
        Location location = simulatorNotView.getFirstFreeLocation();
        if(location != null){
            ReservedSpot reservedspot = new ReservedSpot(startTime, stopTime, location);
            reservations.addReservation(reservedspot);
        }

    }

    public void officeReserve(){
        reserve(new Time(8), new Time(20));
    }

    public void multiOfficeReserve(int amount){
        for(int i = 0; i < amount; i++){
            officeReserve();
        }
    }

    public boolean isReserved(Location location){
        int size = reservations.size();
        for(int i = 0; i < size ; i++){
            ReservedSpot spot = reservations.getSpot(i);
            if(location.isSame(spot.getLocation())){
                return true;
            }
        }
        return false;
    }

    public boolean isNowReserved(Location location){
        int size = reservations.size();
        for(int i = 0; i < size ; i++){
            ReservedSpot spot = reservations.getSpot(i);
            if(location.isSame(spot.getLocation())){
                if (time.getHour() > spot.getStartTime().getHour() && time.getHour() < spot.getStopTime().getHour()){
                    return true;
                }
            }
        }
        return false;
    }
}
