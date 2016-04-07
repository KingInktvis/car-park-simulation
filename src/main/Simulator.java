package main;

import controller.*;
import main.*;
import model.*;
import runner.*;
import view.*;

import java.util.Random;

public class Simulator {


    private SimulatorView simulatorView;
    private Time time;
    private Controller controller;
    private CreateQueues queues;




    public Simulator() {
        queues = new CreateQueues();
        time = new Time();
        controller = new Controller();
        simulatorView = new SimulatorView(controller, 3, 6, 30);
    }


}
