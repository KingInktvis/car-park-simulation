package model;

import java.util.ArrayList;

/**
 * Created by rik on 4/11/16.
 */
public class ReservedSpot {
    private Location location;
    private Time startTime;
    private Time stopTime;

    public ReservedSpot(Time startTime, Time stopTime, Location location){
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getStopTime() {
        return stopTime;
    }
}