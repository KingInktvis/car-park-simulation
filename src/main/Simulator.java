package main;

import controller.*;
import main.*;
import model.*;
import runner.*;
import view.*;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;

public class Simulator {


    private SimulatorView simulatorView;
    private Controller controller;
    private CreateQueues queues;


    public Simulator() {
        queues = new CreateQueues();
        simulatorView = new SimulatorView(3, 6, 30);
        new Thread(new Controller(simulatorView, queues));
    }

}
