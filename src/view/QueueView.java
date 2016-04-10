package view;

import controller.SimulatorNotView;
import model.CarQueue;
import model.CreateQueues;
import model.Location;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Talitha on 8-4-2016.
 */
public class QueueView extends AbstractView {

    private JFrame frame;
    private JLabel labelForEntering;
    private JLabel labelForPaying;
    private JLabel labelForLeaving;
    private CreateQueues queue;


    public QueueView(SimulatorNotView simulatorNotView, JFrame frame, CreateQueues q){
        super(simulatorNotView);
        this.queue = q;
        this.frame = frame;

        labelForEntering = new JLabel("0");
        labelForLeaving = new JLabel("0");
        labelForPaying = new JLabel("0");

        JLabel enteringText = new JLabel("Entering");
        JLabel leavingText = new JLabel("Leaving");
        JLabel payingText = new JLabel("Paying");

        setLocation(30,0);
        Container c = this.frame.getContentPane();
        c.setLayout(new GridLayout(3,1));
        //c.setLocation(30,30);

        c.add(enteringText);
        c.add(labelForEntering);

        c.add(leavingText);
        c.add(labelForLeaving);

        c.add(payingText);
        c.add(labelForPaying);

    }

    @Override
    public void updateView(){
        int entering = queue.getEntranceCarQueue().countCars();
        int leaving = queue.getExitCarQueue().countCars();
        int paying = queue.getPaymentCarQueue().countCars();
        labelForEntering.setText("" + entering);
        labelForLeaving.setText("" + leaving);
        labelForPaying.setText("" + paying);

        repaint();
    }

}
