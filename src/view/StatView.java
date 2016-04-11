package view;

import controller.SimulatorNotView;
import controller.StatControls;
import model.CreateQueues;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Wouter on 10-Apr-16.
 */
public class StatView extends AbstractView {
    private StatControls controls;
    private JFrame frame;



    public StatView(SimulatorNotView simulatorNotView, JFrame frame, StatControls controls){
        super(simulatorNotView);
        this.frame = frame;
        this.controls = controls;


    }

    @Override
    public void updateView(){


        repaint();
    }
}
