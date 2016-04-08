package view;

import controller.SimulatorNotView;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Talitha on 8-4-2016.
 */
public class QueueView extends AbstractView {

    private JWindow window;

    public QueueView(SimulatorNotView simulatorNotView){
        super(simulatorNotView);
    }

    public void makeFrame(){
        JFrame f = new JFrame();
        f.setVisible(true);
        Container c = f.getContentPane();

        f.setSize(new Dimension(300,300));
        f.repaint();
        updateView();
    }
}
