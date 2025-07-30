import javax.swing.*;
import javax.persistence.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Main {
    private static final String DB_URL = "objectdb:cars.odb";

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(DB_URL);

    public static void main(String[] args) {
        JFrame frame = new JFrame("Car Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new GridLayout(0, 2));

        JTextField regField = new JTextField();
        JTextField makeField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField speedField = new JTextField();
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);

        frame.add(new JLabel("Registration Number:")); frame.add(regField);
        frame.add(new JLabel("Make:")); frame.add(makeField);
        frame.add(new JLabel("Model:")); frame.add(modelField);
        frame.add(new JLabel("Year:")); frame.add(yearField);
        frame.add(new JLabel("Top Speed:")); frame.add(speedField);

        JButton saveBtn = new JButton("Save");
        JButton readBtn = new JButton("Read");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton avgBtn = new JButton("Calculate Average");

        frame.add(saveBtn); frame.add(readBtn);
        frame.add(updateBtn); frame.add(deleteBtn);
        frame.add(avgBtn);

        frame.add(new JScrollPane(outputArea));

        saveBtn.addActionListener(e -> {
            EntityManager em = emf.createEntityManager();
            try {
                Car car = new Car(
                    regField.getText(),
                    makeField.getText(),
                    modelField.getText(),
                    Integer.parseInt(yearField.getText()),
                    Double.parseDouble(speedField.getText())
                );
                em.getTransaction().begin();
                em.persist(car);
                em.getTransaction().commit();
                JOptionPane.showMessageDialog(frame, "Saved!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            } finally {
                em.close();
            }
        });

        readBtn.addActionListener(e -> {
            EntityManager em = emf.createEntityManager();
            try {
                String reg = regField.getText();
                if (!reg.isEmpty()) {
                    Car car = em.find(Car.class, reg);
                    outputArea.setText(car != null ? car.toString() : "Car not found.");
                } else {
                    List<Car> cars = em.createQuery("SELECT c FROM Car c", Car.class).getResultList();
                    outputArea.setText("");
                    for (Car c : cars) outputArea.append(c + "\n");
                }
            } finally {
                em.close();
            }
        });

        updateBtn.addActionListener(e -> {
            EntityManager em = emf.createEntityManager();
            try {
                em.getTransaction().begin();
                Car car = em.find(Car.class, regField.getText());
                if (car != null) {
                    car.setMake(makeField.getText());
                    car.setModel(modelField.getText());
                    car.setYear(Integer.parseInt(yearField.getText()));
                    car.setTopSpeed(Double.parseDouble(speedField.getText()));
                    em.getTransaction().commit();
                    JOptionPane.showMessageDialog(frame, "Updated!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Car not found.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            } finally {
                em.close();
            }
        });

        // Delete
        deleteBtn.addActionListener(e -> {
            EntityManager em = emf.createEntityManager();
            try {
                em.getTransaction().begin();
                Car car = em.find(Car.class, regField.getText());
                if (car != null) {
                    em.remove(car);
                    em.getTransaction().commit();
                    JOptionPane.showMessageDialog(frame, "Deleted.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Car not found.");
                }
            } finally {
                em.close();
            }
        });

        avgBtn.addActionListener(e -> {
            EntityManager em = emf.createEntityManager();
            try {
                Double avg = em.createQuery("SELECT AVG(c.topSpeed) FROM Car c", Double.class).getSingleResult();
                JOptionPane.showMessageDialog(frame, "Average speed: " + avg + " km/h");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error or empty DB.");
            } finally {
                em.close();
            }
        });

        frame.setVisible(true);
    }
}


