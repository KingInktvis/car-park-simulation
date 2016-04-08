package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rik on 7-4-16.
 */
public class WestControls extends JPanel{

    private Controller controller;
    private Thread simThread;

    public WestControls(Controller controller){
        this.controller = controller;

        JButton step1 = new JButton("1 step");
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
        setLayout(new GridLayout(2,1));
        add(step1);
        add(step100);
        add(start);
        add(stop);
    }
}
