import javax.persistence.*;

@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tripName;

    @ManyToOne
    @JoinColumn(name = "car_name")
    private Car car;

    public Trip() {}

    public Trip(String tripName, Car car) {
        this.tripName = tripName;
        this.car = car;
    }

    public Long getId() { return id; }

    public String getTripName() { return tripName; }
    public void setTripName(String tripName) { this.tripName = tripName; }

    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }
}
