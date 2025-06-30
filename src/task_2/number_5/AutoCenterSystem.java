package task_2.number_5;

import java.util.*;
import java.util.stream.Collectors;

enum CarType {
    SEDAN, SUV, ELECTRIC, HATCHBACK, UNIVERSAL, SPORT
}

class Car {
    private final String vin;
    private final String model;
    private final String manufacturer;
    private final int year;
    private final int mileage;
    private final double price;
    private final CarType type;

    public Car(String vin, String model, String manufacturer, int year, int mileage, double price, CarType type) {
        this.vin = vin;
        this.model = model;
        this.manufacturer = manufacturer;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
        this.type = type;
    }

    public String getVin() { return vin; }
    public String getModel() { return model; }
    public String getManufacturer() { return manufacturer; }
    public int getYear() { return year; }
    public int getMileage() { return mileage; }
    public double getPrice() { return price; }
    public CarType getType() { return type; }

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
    public String toString() {
        return  "\t" + manufacturer +
                " " + model +
                " " + year +
                " (type: " + type +
                ", " + mileage +
                " km) " + price +
                " $";
    }
}

class CarDealership {
    private final Set<Car> cars = new HashSet<>();

    public boolean addCar(Car car) {
        return cars.add(car);
    }

    public List<Car> findCarsByManufacturer(String manufacturer) {
        return cars.stream()
                .filter(car -> car.getManufacturer().equalsIgnoreCase(manufacturer))
                .collect(Collectors.toList());
    }

    public double getAveragePriceByType(CarType type) {
        return cars.stream()
                .filter(car -> car.getType() == type)
                .collect(Collectors.averagingDouble(Car::getPrice));
    }

    public List<Car> getCarsSortedByYear() {
        return cars.stream()
                .sorted(Comparator.comparingInt(Car::getYear).reversed())
                .collect(Collectors.toList());
    }

    public Map<CarType, Long> getCountCar() {
        return cars.stream()
                .collect(Collectors.groupingBy(Car::getType, Collectors.counting()));
    }

    public Optional<Car> getOldestCar() {
        return cars.stream()
                .min(Comparator.comparingInt(Car::getYear));
    }

    public Optional<Car> getNewestCar() {
        return cars.stream()
                .max(Comparator.comparingInt(Car::getYear));
    }
}

public class AutoCenterSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CarDealership dealership = new CarDealership();

    public static void main(String[] args) {
        initData();

        while (true) {
            printMenu();
            int choice = getIntInput("Выберите действие: ");

            switch (choice) {
                case 1 -> addCar();
                case 2 -> findCarsByManufacturer();
                case 3 -> showAveragePriceByType();
                case 4 -> showCarsSortedByYear();
                case 5 -> showStatistics();
                case 0 -> {
                    System.out.println("Выход из программы");
                    return;
                }
                default -> System.out.println("Неверный выбор!");
            }
        }
    }

    private static void initData() {
        dealership.addCar(new Car("VIN001", "Camry", "Toyota", 2020, 45_000, 25_000, CarType.SEDAN));
        dealership.addCar(new Car("VIN002", "Model 3", "Tesla", 2022, 12_000, 45_000, CarType.ELECTRIC));
        dealership.addCar(new Car("VIN003", "RAV4", "Toyota", 2021, 30_000, 32_000, CarType.SUV));
        dealership.addCar(new Car("VIN004", "F-150", "Ford", 2019, 65_000, 38_000, CarType.SUV));
        dealership.addCar(new Car("VIN005", "Mustang", "Ford", 2023, 5_000, 52_000, CarType.SPORT));
        dealership.addCar(new Car("VIN006", "Leaf", "Nissan", 2021, 22_000, 28_000, CarType.ELECTRIC));
        dealership.addCar(new Car("VIN007", "Golf", "Volkswagen", 2018, 72_000, 18_000, CarType.HATCHBACK));
        dealership.addCar(new Car("VIN008", "XC90", "Volvo", 2022, 15_000, 62_000, CarType.SUV));
        dealership.addCar(new Car("VIN009", "Model S", "Tesla", 2023, 8_000, 85_000, CarType.ELECTRIC));
        dealership.addCar(new Car("VIN010", "Accord", "Honda", 2021, 35_000, 27_000, CarType.SEDAN));
    }

    private static void printMenu() {
        System.out.println("\n\tМеню Автоцентра");
        System.out.println("1. Добавить автомобиль");
        System.out.println("2. Найти автомобили по производителю");
        System.out.println("3. Показать среднюю цену по типу авто");
        System.out.println("4. Показать автомобили (от новых к старым)");
        System.out.println("5. Показать статистику");
        System.out.println("0. Выход");
    }

    private static void addCar() {
        System.out.println("\nДобавление нового автомобиля:");
        String vin = getStringInput("VIN: ");
        String model = getStringInput("Модель: ");
        String manufacturer = getStringInput("Производитель: ");
        int year = getIntInput("Год выпуска: ");
        int mileage = getIntInput("Пробег (км): ");
        double price = getDoubleInput("Цена ($): ");

        System.out.println("Доступные типы: " + Arrays.toString(CarType.values()));
        CarType type = getCarTypeInput("Тип автомобиля: ");

        Car car = new Car(vin, model, manufacturer, year, mileage, price, type);
        if (dealership.addCar(car)) {
            System.out.println("Автомобиль успешно добавлен!");
        } else {
            System.out.println("Ошибка: автомобиль с таким VIN уже существует!");
        }
    }

    private static void findCarsByManufacturer() {
        String manufacturer = getStringInput("Введите производителя: ");
        List<Car> result = dealership.findCarsByManufacturer(manufacturer);

        System.out.println("\nНайдено автомобилей: " + result.size());
        result.forEach(System.out::println);
    }

    private static void showAveragePriceByType() {
        System.out.println("Доступные типы: " + Arrays.toString(CarType.values()));
        CarType type = getCarTypeInput("Введите тип автомобиля: ");

        double avgPrice = dealership.getAveragePriceByType(type);
        System.out.printf("Средняя цена %s автомобилей: %,.2f$\n", type, avgPrice);
    }

    private static void showCarsSortedByYear() {
        List<Car> sortedCars = dealership.getCarsSortedByYear();
        System.out.println("\nАвтомобили (от новых к старым):");
        sortedCars.forEach(System.out::println);
    }

    private static void showStatistics() {
        System.out.println("\nСтатистика автоцентра:");

        Map<CarType, Long> typeStats = dealership.getCountCar();
        System.out.println("\nКоличество автомобилей по типам:");
        typeStats.forEach((type, count) ->
                System.out.printf("- %s: %d шт.\n", type, count));

        dealership.getOldestCar().ifPresentOrElse(
                car -> System.out.printf("\nСамая старая машина: %s", car),
                () -> System.out.println("\nСамая старая машина: не найдено")
        );

        dealership.getNewestCar().ifPresentOrElse(
                car -> System.out.printf("\nСамая новая машина: %s\n", car),
                () -> System.out.println("\nСамая новая машина: не найдено")
        );
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число!");
            }
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число!");
            }
        }
    }

    private static CarType getCarTypeInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return CarType.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: неверный тип автомобиля!");
                System.out.println("Доступные типы: " + Arrays.toString(CarType.values()));
            }
        }
    }
}
