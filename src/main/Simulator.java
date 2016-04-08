package main;

import controller.*;
import model.*;

public class Simulator {


    private SimulatorNotView simulatorNotView;
    private Controller controller;
    private CreateQueues queues;


    public Simulator() {
        queues = new CreateQueues();
        simulatorNotView = new SimulatorNotView(3, 6, 30);
        new Thread(new Controller(simulatorNotView, queues));
    }

}
