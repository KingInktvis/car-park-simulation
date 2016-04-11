package main;

import controller.*;
import model.*;

public class Simulator {


    private SimulatorNotView simulatorNotView;
    private Controller controller;
    private CreateQueues queues;
    private Reservations reservations;
    private ReservationController reservationController;


    public Simulator() {
        queues = new CreateQueues();
        simulatorNotView = new SimulatorNotView(3, 6, 30, queues);

        reservationController = new ReservationController(reservations, simulatorNotView);
        reservationController.multiOfficeReserve(20);
        new Thread(new Controller(simulatorNotView, queues));

    }

}
