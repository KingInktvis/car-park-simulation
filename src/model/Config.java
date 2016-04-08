package model;

/**
 * Created by rik on 4/7/16.
 */
public abstract class Config {

    protected int tickPause = 5;
    protected int weekDayArrivals = 50; // average number of arriving cars per hour
    protected int weekendArrivals = 90; // average number of arriving cars per hour

    protected int enterSpeed = 3; // number of cars that can enter per minute
    protected int paymentSpeed = 10; // number of cars that can pay per minute
    protected int exitSpeed = 9; // number of cars that can leave per minute



}