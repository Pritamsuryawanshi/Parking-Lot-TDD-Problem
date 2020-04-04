package parkinglot;

public class Vehicle {
    String colour;
    String brand;
    String plateNumber;
    String row;

    public Vehicle(String brand, String colour, String plateNumber, String row) {
        this.row = row;
        this.plateNumber = plateNumber;
        this.brand = brand;
        this.colour = colour;
    }

    public Vehicle() {
    }
}
