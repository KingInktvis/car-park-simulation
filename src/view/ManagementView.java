package view;

import controller.ManagementController;
import controller.SimulatorNotView;
import model.Time;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Talitha on 13-Apr-16.
 */
public class ManagementView extends AbstractView{
    ManagementController controller;
    private JFrame frame;
    private JLabel timeValue;
    private JLabel weekendLabel;
    private JLabel revenue;
    private JLabel expectedRevenue;
    private JLabel totalRevenue;
    private JLabel difference;
    private int dayAtm;
    private int daysRun = 0;
    private int totalAdd;

    public ManagementView(SimulatorNotView snv, ManagementController controller, JFrame frame){
        super(snv);
        this.controller = controller;
        this.frame = frame;
        Container c = this.frame.getContentPane();
        c.setLayout(new GridLayout(7,2));

        dayAtm = 0;
        totalAdd = 0;
        weekendLabel = new JLabel("");
        JLabel emptyLabel = new JLabel("");
        JLabel timeLabel = new JLabel("Time");
        JLabel revenueTodayLabel = new JLabel("Revenue for Today");
        JLabel revenueExpectedLabel = new JLabel("Expected Revenue this Month");
        JLabel revenueTotalRevenue = new JLabel("Total revenue");
        JLabel differenceLabel = new JLabel("Difference");

        revenue = new JLabel("");
        expectedRevenue = new JLabel("");
        totalRevenue = new JLabel("");
        difference = new JLabel("");


        timeValue = new JLabel(controller.getTime());

        c.add(weekendLabel);

        c.add(emptyLabel);
        c.add(emptyLabel);
        c.add(emptyLabel);

        c.add(timeLabel);
        c.add(timeValue);
        c.add(revenueTodayLabel);
        c.add(revenue);
        c.add(revenueExpectedLabel);
        c.add(expectedRevenue);
        c.add(revenueTotalRevenue);
        c.add(totalRevenue);
        c.add(differenceLabel);
        c.add(difference);

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
        timeValue.setText(formatTime());
        ArrayList<Integer> payments = simulatorNotView.getPayments();


        int total = totalAdd;
        int day = 0;
        for(Integer el: payments){
            total += el;
            day += el;
        }
        if(dayAtm != simulatorNotView.getTime().getDay()){
            daysRun++;
            dayAtm = simulatorNotView.getTime().getDay();
            simulatorNotView.flushPayments();
            totalAdd = totalAdd+total;
        }
        if(daysRun == 0){
            expectedRevenue.setText("Calculating...");
            difference.setText("");
        }else{
            expectedRevenue.setText((total/daysRun*30) + "" );
            if(total > (total/daysRun)){
                difference.setText("" + (total - (total/daysRun*30)));
            }
            else{
                difference.setText("" + (total - (total/daysRun*30)));
            }
        }
        weekendLabel.setText("");
        if(dayAtm == 5 || dayAtm == 6)
            weekendLabel.setText("Weekend");
        revenue.setText(day + "");
        totalRevenue.setText(total + "");

        repaint();
    }
}
