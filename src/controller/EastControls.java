package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rik on 4/8/16.
 */
public class EastControls extends JPanel {

    private Controller controller;
    private Thread simThread;
    private SimulatorNotView simulatorNotView;

    public EastControls(Controller controller, SimulatorNotView sim){
        this.controller = controller;
        this.simulatorNotView = sim;

        JButton qvButton = new JButton("Show Queues");
        qvButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent){
                simulatorNotView.queueView.makeFrame();
            }
        });

        JButton manButton = new JButton("Manage");
       /* qvButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent){
                simulatorNotView.queueView.makeFrame();
            }
        });*/
        JButton statButton = new JButton("Stats");
       /* qvButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent){
                simulatorNotView.queueView.makeFrame();
            }
        });*/


        add(qvButton, BorderLayout.EAST);
        add(manButton, BorderLayout.EAST);
        add(statButton, BorderLayout.EAST);
        setLayout(new GridLayout(2,1));
        add(qvButton);
    }
}
