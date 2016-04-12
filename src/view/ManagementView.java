package view;

import controller.ManagementController;
import controller.SimulatorNotView;
import model.Time;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Talitha on 13-Apr-16.
 */
public class ManagementView extends AbstractView{
    ManagementController controller;
    private JFrame frame;
    private JLabel timeLabel;

    public ManagementView(SimulatorNotView snv, ManagementController controller, JFrame frame){
        super(snv);
        this.controller = controller;
        this.frame = frame;
        Container c = this.frame.getContentPane();
        c.setLayout(new GridLayout(3,1));
        timeLabel = new JLabel(controller.getTime());
        c.add(timeLabel);
    }

    private String formatTime(){
        Time time = simulatorNotView.getTime();
        String days = "0" + time.getDay();
        String hours = time.getHour() < 10 ? "0" + time.getHour() : "" + time.getHour();
        String minutes = time.getMinute() < 10 ? "0" + time.getMinute() : "" + time.getMinute();
        return "" + days + ":" + hours + ":" + minutes;
       // return "";
    }
    @Override
    public void updateView(){
       // controller.tick();
        timeLabel.setText(formatTime());


        repaint();
    }
}
