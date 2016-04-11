package view;

import controller.ReservationController;
import controller.SimulatorNotView;
import model.Car;
import model.Location;
import model.Reservation;

import java.awt.*;

/**
 * Created by rik on 4/5/16.
 */

public class CarParkView extends AbstractView {

    private Dimension size;
    private Image carParkImage;
    private ReservationController reservationController;

    /**
     * Constructor for objects of class CarPark
     */
    public CarParkView(SimulatorNotView simulatorNotView, ReservationController reservationController) {
        super(simulatorNotView);
        size = new Dimension(0, 0);
        this.reservationController = reservationController;
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(800, 500);
    }

    /**
     * Overriden. The car park view component needs to be redisplayed. Copy the
     * internal image to screen.
     */
    public void paintComponent(Graphics g) {
        if (carParkImage == null) {
            return;
        }

        Dimension currentSize = getSize();
        if (size.equals(currentSize)) {
            g.drawImage(carParkImage, 0, 0, null);
        }
        else {
            // Rescale the previous image.
            g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
        }
    }

    @Override
    public void updateView() {
        // Create a new car park image if the size has changed.
        if (!size.equals(getSize())) {
            size = getSize();
            carParkImage = createImage(size.width, size.height);
        }
        Graphics graphics = carParkImage.getGraphics();
        for(int floor = 0; floor < simulatorNotView.getNumberOfFloors(); floor++) {
            for(int row = 0; row < simulatorNotView.getNumberOfRows(); row++) {
                for(int place = 0; place < simulatorNotView.getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = simulatorNotView.getCarAt(location);
                    Color color = Color.white;
                    if(reservationController.isReserved(location) == false){
                        color = color.lightGray;
                        System.out.println("het werkt");
                    }
                    if(car != null)
                        switch(car.getClass().getName()){
                            case "model.AdHocCar":
                                color = Color.red;
                                break;
                            case "model.ParkingPass":
                                color = Color.blue;
                                break;
                            case "model.Reservation":
                                color = Color.green;
                                break;
                        }
                   // Color color = car == null ? Color.white : Color.red;
                    drawPlace(graphics, location, color);
                }
            }
        }
        repaint();
    }

    /**
     * Paint a place on this car park view in a given color.
     */
    private void drawPlace(Graphics graphics, Location location, Color color) {
        graphics.setColor(color);
        graphics.fillRect(
                location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                60 + location.getPlace() * 10,
                20 - 1,
                10 - 1); // TODO use dynamic size or constants
    }
}
