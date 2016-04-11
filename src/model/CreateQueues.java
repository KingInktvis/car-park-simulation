package model;

/**
 * Created by rik on 4/7/16.
 */
public class CreateQueues {

    private CarQueue entranceCarQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private Time time;
    private Reservations reservations;

    public CreateQueues(){
        entranceCarQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        time = new Time();
        reservations = new Reservations();
    }

    public CarQueue getEntranceCarQueue() {
        return entranceCarQueue;
    }

    public CarQueue getExitCarQueue() {
        return exitCarQueue;
    }

    public CarQueue getPaymentCarQueue() {
        return paymentCarQueue;
    }

    public Time getTime() {
        return time;
    }

    public Reservations getReservations() {
        return reservations;
    }
}
