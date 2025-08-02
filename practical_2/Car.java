import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Car {
    @Id
    private String name;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trip> trips;

    public Car() {}

    public Car(String name) {
        this.name = name;
        this.trips = new ArrayList<Trip>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Trip> getTrips() { return trips; }
    public void addTrip(Trip trip) { this.trips.add(trip); }
    public void setTrips(List<Trip> trips) { this.trips = trips; }
}
