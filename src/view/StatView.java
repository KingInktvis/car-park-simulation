package view;

import controller.SimulatorNotView;
import controller.StatControls;
import model.CreateQueues;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Talitha on 10-Apr-16.
 */
public class StatView extends AbstractView {
    private StatControls controls;
    private SimulatorNotView simulatorNotView;
    private JFrame frame;
    private HashMap<String, Integer> amountOfCars;
    private ArrayList<Integer> occupancyRates;



    public StatView(SimulatorNotView simulatorNotView, JFrame frame, StatControls controls){
        super(simulatorNotView);
        this.frame = frame;
        this.controls = controls;
        this.simulatorNotView = simulatorNotView;
        occupancyRates = new ArrayList<>();
        //setLayout(null);

    }

    public JFrame getFrame(){
        return frame;
    }
    @Override
    public void updateView(){
        amountOfCars = simulatorNotView.countCars();
        int height = 80;
        if(amountOfCars.get("total") == 0){
            System.out.println("uh");
            repaint();
            return;
        }

        float carsPerPixel = height / new Float(amountOfCars.get("total"));
        int offset = 250;

        class StatBars extends JComponent{
            @Override
            public void paint(Graphics g){
                Font label = new Font("SansSerif", Font.PLAIN, 12);
                Font title = new Font("SansSerif", Font.BOLD, 16);
                int totalSpots = simulatorNotView.getNumberOfFloors() * simulatorNotView.getNumberOfRows() * simulatorNotView.getNumberOfPlaces();

                g.setColor(Color.black);
                g.setFont(title);
                g.drawString("Cars: " + amountOfCars.get("total"), 10, 35);
                g.setFont(label);
                g.setColor(Color.BLUE);
                g.fillRect(10, (offset - Math.round(carsPerPixel * amountOfCars.get("parkingPass"))), 30, Math.round(carsPerPixel * amountOfCars.get("parkingPass")));
                g.drawString(amountOfCars.get("parkingPass") + "", 12, offset+20);

                g.setColor(Color.RED);
                g.fillRect(50, (offset - Math.round(carsPerPixel * amountOfCars.get("adhoc"))), 30, Math.round(carsPerPixel * amountOfCars.get("adhoc")));
                g.drawString(amountOfCars.get("adhoc") + "", 52, offset+20);

                g.setColor(Color.GREEN);
                g.fillRect(90, (offset - Math.round(carsPerPixel * amountOfCars.get("reservations"))), 30, Math.round(carsPerPixel * amountOfCars.get("reservations")));
                g.drawString(amountOfCars.get("reservations") + "", 92, offset+20);

                g.setColor(Color.BLACK);
                int occupancyRate = (amountOfCars.get("total") == 0) ? 0 : Math.round(new Float(amountOfCars.get("total")) / new Float(totalSpots)  * 100) ;
                occupancyRates.add(occupancyRate);
                // Stat labels
                g.drawString("Occupancy Rate", 160, 35);
                g.drawString("Average O.R.", 160, 55);

                // Stats for the labels
                int avgOR = 0;
                int counter = 0;
                for(Integer el: occupancyRates){
                    counter++;
                    avgOR += el;
                }
                avgOR = avgOR / counter;
                g.drawString(occupancyRate + "%", 300, 35);
                g.drawString(avgOR + "%", 300, 55);
            }
        }
        StatBars statBars = new StatBars();

        Container content = frame.getContentPane();
        content.add(statBars);
        content.repaint();
        repaint();
    }
}
