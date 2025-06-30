package task_2.number_3;

import java.util.*;

public class Car implements Comparable<Car> {
    private final String vin;
    private String model;
    private String manufacturer;
    private int year;
    private int mileage;
    private double price;

    public Car(String vin, String model, String manufacturer, int year, int mileage, double price) {
        this.vin = vin;
        this.model = model;
        this.manufacturer = manufacturer;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car that = (Car) o;
        return Objects.equals(vin, that.vin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin);
    }

    @Override
    public int compareTo(Car other) {
        return Integer.compare(other.year, this.year);
    }

    @Override
    public String toString() {
        return  manufacturer +
                " " + model +
                " (" + year +
                ") " + vin;
    }

    public String getVin() { return vin; }
    public String getModel() { return model; }
    public String getManufacturer() { return manufacturer; }
    public int getYear() { return year; }
    public int getMileage() { return mileage; }
    public double getPrice() { return price; }


    public static void main(String[] args) {
        List<Car> cars = Arrays.asList(
                new Car("VIN01", "Camry", "Toyota", 2020, 15_000, 25_000),
                new Car("VIN02", "Model 3", "Tesla", 2022, 5_000, 45_000),
                new Car("VIN03", "Mustang", "Ford", 2023, 1_000, 55_000),
                new Car("VIN02", "Focus", "Ford", 2015, 80_000, 15_000),
                new Car("VIN01", "RAV4", "Toyota", 2021, 25_000, 30_000),
                new Car("VIN04", "Accord", "Honda", 2019, 35_000, 25_000)
        );
        Set<Car> carSet = new HashSet<>(cars);

        System.out.println("Автомобили в HashSet (VIN уникальны):");
        for (Car car : carSet) {
            System.out.println(car);
        }

        List<Car> carList = new ArrayList<>(carSet);
        Collections.sort(carList);

        System.out.println("\nСортировка по году выпуска (новые -> старые):");
        for (Car car : carList) {
            System.out.println(car);
        }
    }
}
