package controller;

import model.Car;
import model.CreateQueues;
import model.Location;
import view.AbstractView;
import view.CarParkView;
import view.QueueView;
import view.StatView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SimulatorNotView extends JFrame {

    private CarParkView carParkView;
    private QueueView queueView;
    private StatView statView;
    //protected ManagementView manView;
    //protected StatsView statView;
    private ArrayList<AbstractView> views;
    private CreateQueues queues;

    protected JFrame queueViewFrame;
    private JFrame manViewFrame;
    private JFrame statViewFrame;

    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private Car[][][] cars;
    private JPanel west;
    private JPanel east;

    public SimulatorNotView(int numberOfFloors, int numberOfRows, int numberOfPlaces, CreateQueues q) {
        //this.controller = controller;
        this.queues = q;
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;

        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];


        queueViewFrame = makeFrame(new Dimension(250,100),"Queue Overview");
        statViewFrame = makeFrame(new Dimension(500,500), "Statistics");
        statView = new StatView(this, statViewFrame, new StatControls(this));
        ReservationController reservationController = new ReservationController(queues.getReservations(), this);
        carParkView = new CarParkView(this, reservationController);
        queueView = new QueueView(this, this.queueViewFrame, this.queues);

        views = new ArrayList<>();
        views.add(queueView);
        views.add(carParkView);

        Container contentPane = getContentPane();
        //contentPane.add(stepLabel, BorderLayout.NORTH);
        contentPane.add(carParkView, BorderLayout.CENTER);
        //contentPane.add(population, BorderLayout.SOUTH);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        updateView();
    }

    private JFrame makeFrame(Dimension d, String name){
        JFrame f = new JFrame(name);
        f.setVisible(false); // for clarity
        f.setSize(d);
        f.setDefaultCloseOperation(HIDE_ON_CLOSE);
        return f;
    }

    public void showFrame(JFrame f){
        f.setVisible(true);
    }
    public void updateView() {
        for(AbstractView e: views){
            e.updateView();
        }
    }

    public int getNumberOfFloors() {
            return numberOfFloors;
        }
    
    public int getNumberOfRows() {
            return numberOfRows;
        }
    
    public int getNumberOfPlaces() {
            return numberOfPlaces;
        }
    
    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }
    
    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            return true;
        }
        return false;
    }
    
    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        return car;
    }
    
    public Location getFirstFreeLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        return location;
                    }
                }
            }
        }

        return null;
    }

    public Location getFirstLastLocation() {
        for (int floor = getNumberOfFloors(); floor < 0; floor--) {
            for (int row = getNumberOfRows(); row < 0; row--) {
                for (int place = getNumberOfPlaces(); place < 0; place--) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        return location;
                    }
                }
            }
        }
        return null;
    }
    
    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    public void addWest(JPanel west){
        this.west = west;
        getContentPane().add(west, BorderLayout.WEST);
    }

    public void addEast(JPanel east){
        this.east = east;
        getContentPane().add(east, BorderLayout.EAST);
    }
    
    public void tick() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }
    

}