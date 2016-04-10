package view;

import controller.SimulatorNotView;

import javax.swing.*;

/**
 * Created by Talitha on 8-4-2016.
 */
public abstract class AbstractView extends JPanel {

    protected SimulatorNotView simulatorNotView;

    public AbstractView(SimulatorNotView controller){
        simulatorNotView = controller;
    }

    public void updateView() { repaint(); }
}
