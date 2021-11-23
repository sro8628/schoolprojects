package puzzles.jam.model;


import java.util.ArrayList;
import java.util.Objects;

public class Car  {

    private String car_name;
    private int start_x;
    private int start_y;
    private int end_y;
    private int end_x;


    public Car(String car_name,int start_x,int start_y,int end_x ,int end_y){ // constructer
        this.car_name= car_name;
        this.start_x = start_x;
        this.start_y = start_y;
        this.end_x = end_x;
        this.end_y = end_y;




    }
    public String getCar_name(){ // get the cars name
        return car_name;
    }
    public int getStart_x(){ // get the start x

        return start_x;
    }
    public int getStart_y(){ // get the start y

        return start_y;
    }
    public int getEnd_x(){ // get the end x

        return end_x;
    }
    public int getEnd_y(){ // get the end y

        return end_y;
    }

    public void setCar_name(String car_name) { // setters
        this.car_name = car_name;
    }

    public void setStart_x(int start_x) {
        this.start_x = start_x;
    }

    public void setEnd_x(int end_x) {
        this.end_x = end_x;
    }

    public void setEnd_y(int end_y) {
        this.end_y = end_y;
    }

    public void setStart_y(int start_y) {
        this.start_y = start_y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return start_x == car.start_x && start_y == car.start_y && end_y == car.end_y && end_x == car.end_x && car_name.equals(car.car_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(car_name, start_x, start_y, end_y, end_x);
    }
}
