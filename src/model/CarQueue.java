package model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class CarQueue {
    private Queue<Car> queue = new LinkedList<>();

    public boolean addCar(Car car) {
        return queue.add(car);
    }

    public Car removeCar() {
        return queue.poll();
    }

    public int countCars(){
        Iterator<Car> i = queue.iterator();
        int count = 0;
        while(i.hasNext()){
            i.next();
            count++;
        }
        return count;
    }
}
