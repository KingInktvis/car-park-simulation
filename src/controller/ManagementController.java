package controller;

import model.Car;
import model.Time;

import java.util.ArrayList;

/**
 * Created by Talitha on 13-Apr-16.
 */
public class ManagementController {
    SimulatorNotView controller;
    int daysRunning = 0;
    int minutes;
    int hours;
    Time time;
    int revenue;
    public ManagementController(SimulatorNotView snv){
        controller = snv;
        time = snv.getTime();
    }
    public void tick(){
        ArrayList<Car> x =  controller.getCars();

    }
    public String getTime(){
        //System.out.println(time.getMinute());
        return "" + time.getDay() + ":" + time.getHour() + ":" + time.getMinute();
    }
}
