import Javax.persistence.*;

@Entity
public class Car {
    @Id
    private String registrationNumber;

    private String make;
    private String model;
    private int year;
    private double topSpeed;

    public Car() {}

    public Car(String registrationNumber, String make, String model, int year, double topSpeed) {
        this.registractionNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.year = year;
        this.topSpeed = topSpeed;
    }

    public String getRegistractionNumber() {
        return registrationNumber;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getTopSpeed() {
        return topSpeed;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(string model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTopSpeed(double topSpeed) {
        this.topSpeed = topSpeed;
    }

    @Override
    public String toString() {
        return registrationNumber + " | " + make + " | " + model + " | " + year + " | " + topSpeed + " km/h";
    }
}
