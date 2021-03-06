package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rik on 7-4-16.
 */
public class Controls extends JPanel{

    private Controller controller;
    private Thread simThread;
    private SimulatorNotView simulatorNotView;

    public Controls(Controller controller, SimulatorNotView sim){
        this.controller = controller;
        this.simulatorNotView = sim;
        JButton step1 = new JButton("1 step");
        step1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                makeThread();
                controller.start(1);
            }
        });
        JButton step100 = new JButton("100 steps");
        step100.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                makeThread();
                controller.start(100);
            }
        });

        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                makeThread();
                controller.start();
            }
        });

        JButton stop = new JButton("Stop");
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                makeThread();
                controller.stop();
            }
        });

        setLayout(new GridLayout(2,1));
        add(step1);
        add(step100);
        add(start);
        add(stop);



    }
    private void makeThread(){
        if(simThread == null){
            simThread = new Thread(controller);
            simThread.start();
        }
        else if(!simThread.isAlive()){
            simThread = null;
            makeThread();
        }
    }
}
