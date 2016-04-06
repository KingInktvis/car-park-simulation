import java.util.Random;

public class Simulator {

    private CarQueue entranceCarQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private SimulatorView simulatorView;

    private int day = 0;
    private int hour = 0;
    private int minute = 0;

    private int tickPause = 100;

    int weekDayArrivals = 50; // average number of arriving cars per hour
    int weekendArrivals = 90; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 10; // number of cars that can pay per minute
    int exitSpeed = 9; // number of cars that can leave per minute

    public Simulator() {
        entranceCarQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        simulatorView = new SimulatorView(this, 3, 6, 30);
    }

    public void run(int number) {
        for (int i = 0; i < number; i++) {
            tick();
        }
    }
    private void minuteTick(){
        // Advance the time by one minute.
        minute++;
        if (minute > 59) {
            minute -= 60;
            hour++;

            if (hour > 23) {
                hour -= 24;
                day++;
                if (day > 6) {
                    day -= 7;
                }
            }

        }
    }

    private void addEntranceCars(int numberOfCars){
        // Add the cars to the back of the queue.
        for (int i = 0; i < numberOfCars; i++) {
            double rand = Math.random();
            Car car;
            if(rand < 0.7) {
                car = new AdHocCar();
            }else{
                car = new ParkingPass();
            }
            entranceCarQueue.addCar(car);
        }
    }

    private void parkCar(Random random){
        // Remove car from the front of the queue and assign to a parking space.
        for (int i = 0; i < enterSpeed; i++) {
            Car car = entranceCarQueue.removeCar();
            if (car == null) {
                break;
            }
            // Find a space for this car.
            Location freeLocation = simulatorView.getFirstFreeLocation();
            if (freeLocation != null) {
                simulatorView.setCarAt(freeLocation, car);
                int stayMinutes = (int) (15 + random.nextFloat() * 10 * 60);
                car.setMinutesLeft(stayMinutes);
            }
        }
    }

    private void leavingCars(){
        // Add leaving cars to the exit queue.
        while (true) {
            Car car = simulatorView.getFirstLeavingCar();
            if (car == null) {
                break;
            }

            if(car instanceof ParkingPass){
                simulatorView.removeCarAt(car.getLocation());
                exitCarQueue.addCar(car);
            }else{
                car.setIsPaying(true);
                paymentCarQueue.addCar(car);
            }
        }
    }

    private void payTicket(){
        // Let cars pay.
        for (int i = 0; i < paymentSpeed; i++) {
            Car car = paymentCarQueue.removeCar();
            if (car == null) {
                break;
            }
            // TODO Handle payment.
            simulatorView.removeCarAt(car.getLocation());
            exitCarQueue.addCar(car);
        }
    }

    private void carsExit(){
        // Let cars leave.
        for (int i = 0; i < exitSpeed; i++) {
            Car car = exitCarQueue.removeCar();
            if (car == null) {
                break;
            }
            // Bye!
        }
    }

    private int averageCarsPerHour(){
        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDayArrivals
                : weekendArrivals;
        return averageNumberOfCarsPerHour;
    }

    private int carPerMinute(Random random){
        int averageNumberOfCarsPerHour = averageCarsPerHour();
        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.1;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        int numberOfCarsPerMinute = (int)Math.round(numberOfCarsPerHour / 60);
        return numberOfCarsPerMinute;
    }

    private void tick() {
        Random random = new Random();

        int numberOfCarsPerMinute = carPerMinute(random);

        addEntranceCars(numberOfCarsPerMinute);

        parkCar(random);

        // Perform car park tick.
        simulatorView.tick();

        leavingCars();

        carsExit();

        // Update the car park view.
        simulatorView.updateView();

        // Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
