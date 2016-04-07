package controller;

import controller.*;
import main.*;
import model.*;
import runner.*;
import view.*;

import java.util.Random;

/**
 * Created by rik on 4/7/16.
 */
public class Controller extends Config implements Runnable{

    private CreateQueues queues;
    private CarQueue entranceCarQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;

    private int tickPause = 5;
    int weekDayArrivals = 50; // average number of arriving cars per hour
    int weekendArrivals = 90; // average number of arriving cars per hour
    int enterSpeed = 3; // number of cars that can enter per minuteint paymentSpeed = 10; // number of cars that can pay per minute
    int exitSpeed = 9; // number of *cars that can leave per minute

    private boolean run = false;
    private int timesToRun = 0;
    private SimulatorView simulatorView;
    private Time time;
    private WestControls westControls;

    public Controller(SimulatorView simulatorView, CreateQueues queues){
        this.simulatorView = simulatorView;
        this.queues = queues;
        setQueues();
        westControls = new WestControls(this);
        simulatorView.addWest(westControls);
        simulatorView.pack();
    }

    private void setQueues(){
        entranceCarQueue = queues.getEntranceCarQueue();
        paymentCarQueue = queues.getPaymentCarQueue();
        exitCarQueue = queues.getExitCarQueue();
        this.time = queues.getTime();
    }

    private void minuteTick(){
        time.minuteIncrement();
    }

    private void addEntranceCars(int numberOfCars){
        // Add the cars to the back of the queue.
        for (int i = 0; i < numberOfCars; i++) {
            double rand = Math.random();
            Car car;
            if(rand < 0.7) {
                car = new AdHocCar();
            }else{
                car = new ParkingPass();
            }
            entranceCarQueue.addCar(car);
        }
    }

    private void parkCar(Random random){
        // Remove car from the front of the queue and assign to a parking space.
        for (int i = 0; i < enterSpeed; i++) {
            // Find a space for this car.
            Location freeLocation = simulatorView.getFirstFreeLocation();
            if (freeLocation != null) {
                Car car = entranceCarQueue.removeCar();
                if (car == null) {
                    break;
                }
                simulatorView.setCarAt(freeLocation, car);
                int stayMinutes = (int) (15 + random.nextFloat() * 10 * 60);
                car.setMinutesLeft(stayMinutes);
            }
        }
    }

    private void leavingCars(){
        // Add leaving cars to the exit queue.
        while (true) {
            Car car = simulatorView.getFirstLeavingCar();
            if (car == null) {
                break;
            }

            if(car instanceof ParkingPass){
                simulatorView.removeCarAt(car.getLocation());
                exitCarQueue.addCar(car);
            }else{
                car.setIsPaying(true);
                paymentCarQueue.addCar(car);
            }
        }
    }

    private void payTicket(){
        // Let cars pay.
        for (int i = 0; i < paymentSpeed; i++) {
            Car car = paymentCarQueue.removeCar();
            if (car == null) {
                break;
            }
            // TODO Handle payment.
            simulatorView.removeCarAt(car.getLocation());
            exitCarQueue.addCar(car);
        }
    }

    private void carsExit(){
        // Let cars leave.
        for (int i = 0; i < exitSpeed; i++) {
            Car car = exitCarQueue.removeCar();
            if (car == null) {
                break;
            }
            // Bye!
        }
    }

    private int averageCarsPerHour(){
        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = time.getDay() < 5
                ? weekDayArrivals
                : weekendArrivals;
        return averageNumberOfCarsPerHour;
    }

    private int carPerMinute(Random random){
        int averageNumberOfCarsPerHour = averageCarsPerHour();
        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.1;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        int numberOfCarsPerMinute = (int)Math.round(numberOfCarsPerHour / 60);
        return numberOfCarsPerMinute;
    }

    private void tick() {
        Random random = new Random();
        minuteTick();
        int numberOfCarsPerMinute = carPerMinute(random);

        addEntranceCars(numberOfCarsPerMinute);

        parkCar(random);

        // Perform car park tick.
        simulatorView.tick();
        payTicket();
        leavingCars();

        carsExit();

        // Update the car park view.
        simulatorView.updateView();


    }

    public void start(){
        run = true;
    }
    public void start(int times){
        run = true;
        timesToRun = times;
        run();
    }
    public void stop(){
        run = false;
    }

    @Override
    public void run(){
        if(timesToRun == 0)
            while (run) {
                tick();
                pause();
            }
        else {
            for (int i = 0; i < timesToRun && run; i++) {
                tick();
                System.out.println(i);
                pause();
            }
            timesToRun = 0;
        }
        stop();
    }
    public void pause(){
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
