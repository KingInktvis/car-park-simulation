package controller;

import model.CreateQueues;

import javax.swing.*;

/**
 * Created by Wouter on 10-Apr-16.
 */
public class StatControls extends JPanel {
    private SimulatorNotView simulatorNotView;
    private CreateQueues info;

    public StatControls(SimulatorNotView snv, CreateQueues information){
        simulatorNotView = snv;
        info = information;
    }

}
