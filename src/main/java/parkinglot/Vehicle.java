package parkinglot;

public class Vehicle {
    String colour;
    String brand;
    String plateNumber;

    public Vehicle(String brand, String colour, String plateNumber) {
        this.plateNumber = plateNumber;
        this.brand = brand;
        this.colour = colour;
    }

    public Vehicle() {
    }
}
