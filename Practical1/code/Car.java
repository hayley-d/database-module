import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Car implements Serializable {
    
    private static long serialVersionUid = 1L;
    
    @Id
    private String regNumber; 
    private String make;
    private String model;
    private int year;
    private double topSpeed;

    public Car() {} 

    public Car(String regNumber, String make, String model, int year, double topSpeed) {
        this.regNumber = regNumber;
        this.make = make;
        this.model = model;
        this.year = year;
        this.topSpeed = topSpeed;
    }

    public String getRegNumber() { return regNumber; }
    public void setRegNumber(String regNumber) { this.regNumber = regNumber; }
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public double getTopSpeed() { return topSpeed; }
    public void setTopSpeed(double topSpeed) { this.topSpeed = topSpeed; }
}
