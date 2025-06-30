package task_2.number_4;

import java.util.*;
import java.util.stream.Collectors;

class Car {
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

    public String getVin() { return vin; }
    public String getModel() { return model; }
    public String getManufacturer() { return manufacturer; }
    public int getYear() { return year; }
    public int getMileage() { return mileage; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return  "\t" + manufacturer +
                " " + model +
                " (" + mileage +
                " km) " + price +
                " $";
    }
}

public class CarStreamAPI {
    public static void main(String[] args) {
        List<Car> cars = Arrays.asList(
                new Car("VIN001", "Camry", "Toyota", 2020, 45_000, 25_000),
                new Car("VIN002", "Model 3", "Tesla", 2022, 12_000, 45_000),
                new Car("VIN003", "Civic", "Honda", 2018, 60_000, 20_000),
                new Car("VIN004", "Focus", "Ford", 2015, 85_000, 15_000),
                new Car("VIN005", "Accord", "Honda", 2019, 38_000, 23_000),
                new Car("VIN006", "Model S", "Tesla", 2021, 8_000, 75_000),
                new Car("VIN007", "RAV4", "Toyota", 2020, 52_000, 28_000),
                new Car("VIN008", "Mustang", "Ford", 2022, 5_000, 42_000)
        );

        List<Car> lowMileageCars = cars.stream()
                .filter(car -> car.getMileage() < 50_000)
                .collect(Collectors.toList());

        System.out.println("Машины с пробегом < 50_000 км:");
        lowMileageCars.forEach(System.out::println);

        List<Car> sortedByPrice = cars.stream()
                .sorted(Comparator.comparingDouble(Car::getPrice).reversed())
                .collect(Collectors.toList());

        System.out.println("\nВсе машины отсортированные по цене (убывание):");
        sortedByPrice.forEach(System.out::println);

        System.out.println("\nТоп-3 самые дорогие машины:");
        sortedByPrice.stream()
                .limit(3)
                .forEach(System.out::println);

        double averageMileage = cars.stream()
                .collect(Collectors.averagingDouble(Car::getMileage));

        System.out.printf("\nСредний пробег всех машин: %,.2f км\n", averageMileage);

        Map<String, List<Car>> carsByManufacturer = cars.stream()
                .collect(Collectors.groupingBy(Car::getManufacturer));

        System.out.println("\nГруппировка по производителю:");
        carsByManufacturer.forEach((manufacturer, carList) -> {
            System.out.println("\n" + manufacturer + ":");
            carList.forEach(System.out::println);
        });
    }
}