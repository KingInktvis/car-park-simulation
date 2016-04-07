package model;

/**
 * Created by rik on 4/7/16.
 */
public class CreateQueues {

    private CarQueue entranceCarQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;

    public CreateQueues(){
        entranceCarQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
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
}
