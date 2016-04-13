package controller;

import model.*;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SimulatorNotView extends JFrame {

    private CarParkView carParkView;
    private QueueView queueView;
    private StatView statView;
    private ManagementView manView;

    private ArrayList<AbstractView> views;
    private CreateQueues queues;
    private ReservationController reservationController;

    protected JFrame queueViewFrame;
    protected JFrame manViewFrame;
    protected JFrame statViewFrame;

    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private Car[][][] cars;
    private ArrayList<Integer> payments;

    private StatControls statControls;

    public SimulatorNotView(int numberOfFloors, int numberOfRows, int numberOfPlaces, CreateQueues q) {
        //this.controller = controller;
        this.queues = q;
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;


        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];

        statControls = new StatControls(this, q);

        queueViewFrame = makeFrame(new Dimension(250,100),"Queue Overview");
        statViewFrame = makeFrame(new Dimension(370,330), "Statistics");
        manViewFrame = makeFrame(new Dimension(400,250), "Revenue & Expectations");
        setResizable(false);
        statViewFrame.setResizable(false);

        manView = new ManagementView(this, new ManagementController(this), manViewFrame);
        statView = new StatView(this, statViewFrame, statControls);
        reservationController = new ReservationController(queues.getReservations(), this);
        carParkView = new CarParkView(this, reservationController);
        queueView = new QueueView(this, this.queueViewFrame, this.queues);

        payments = new ArrayList<>();
        views = new ArrayList<>();
        views.add(queueView);
        views.add(carParkView);
        views.add(statView);
        views.add(manView);

        Container contentPane = getContentPane();
        //contentPane.add(stepLabel, BorderLayout.NORTH);
        contentPane.add(carParkView, BorderLayout.CENTER);
        //contentPane.add(population, BorderLayout.SOUTH);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        updateView();


        reservationController.multiOfficeReserve(20);
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

    public ArrayList<Car> getCars(){
        ArrayList<Car> carList = new ArrayList<>();
        for(int x = 0; x < numberOfFloors; x++)
            for(int y = 0; y < numberOfRows; y++)
                for(int z = 0; z < numberOfPlaces; z++){
                    if(cars[x][y][z] != null){
                        carList.add(cars[x][y][z]);
                    }
                }
        return carList;
    }

    public Time getTime(){
        return queues.getTime();
    }

    public HashMap<String,Integer> countCars(){
        int counterAdHoc = 0;
        int counterParkingPass = 0;
        int counterReservation = 0;
        int counter = 0;
        for(int x = 0; x < numberOfFloors; x++)
            for(int y = 0; y < numberOfRows; y++)
                for(int z = 0; z < numberOfPlaces; z++){
                    if(cars[x][y][z] != null){
                        counter++;
                        switch(cars[x][y][z].getClass().getName()){
                            case "model.AdHocCar":
                                counterAdHoc++;
                                break;
                            case "model.ParkingPass":
                                counterParkingPass++;
                                break;
                            case "model.Reservation":
                                counterReservation++;
                        }
                    }
                }

        HashMap<String,Integer> counterMap = new HashMap<>();
        counterMap.put("total", counter);
        counterMap.put("adhoc", counterAdHoc);
        counterMap.put("parkingPass", counterParkingPass);
        counterMap.put("reservations", counterReservation);
        return counterMap;
    }

    public void addPayment(int money){
        payments.add(money);
    }
    public void flushPayments(){
        payments = null;
        payments = new ArrayList<>();
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
                    if (getCarAt(location) == null && !reservationController.isReserved(location)) {
                        return location;
                    }
                }
            }
        }
        return null;
    }

    public Location getFirstReservedLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null && reservationController.isReserved(location)) {
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

    public ArrayList<Integer> getPayments(){
        return this.payments;
    }
    public void addWest(JPanel west){
        //this.west = west;
        getContentPane().add(west, BorderLayout.WEST);
    }

    public void addEast(JPanel east){
        //this.east = east;
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