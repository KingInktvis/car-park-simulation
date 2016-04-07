package view;

import controller.*;
import main.*;
import model.*;
import runner.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulatorView extends JFrame {

    private CarParkView carParkView;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private Car[][][] cars;
    private Controller controller;
    private Thread simThread;
    private JPanel west;

    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        //this.controller = controller;
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];



        carParkView = new CarParkView(this);
        /*JButton step1 = new JButton("1 step");
        step1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(simThread == null)
                    new Thread(controller).start();
                controller.start(1);
            }
        });
        JButton step100 = new JButton("100 steps");
        step100.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(simThread == null)
                    new Thread(controller).start();
                controller.start(100);
            }
        });

        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(simThread == null)
                    new Thread(controller).start();
                controller.start();
            }
        });

        JButton stop = new JButton("Stop");
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(simThread == null)
                    new Thread(controller).start();
                controller.stop();
            }
        });
        JPanel westControls = new JPanel();
        westControls.setLayout(new GridLayout(2,1));
        westControls.add(step1);
        westControls.add(step100);
        westControls.add(start);
        westControls.add(stop);*/
        Container contentPane = getContentPane();
        //contentPane.add(stepLabel, BorderLayout.NORTH);
        //contentPane.add(westControls, BorderLayout.WEST);
        contentPane.add(carParkView, BorderLayout.CENTER);
        //contentPane.add(population, BorderLayout.SOUTH);
        pack();
        setVisible(true);

        updateView();
    }



    public void updateView() {
        carParkView.updateView();
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