package task_2.number_2;

import java.util.*;

public class CarModels {
    public static void main(String[] args) {
        List<String> carModels = new ArrayList<>(Arrays.asList(
                "Toyota Camry", "BMW X5", "Honda Civic", "Land Rover",
                "Tesla Model X","Porsche", "Tesla Model X", "Jaguar",
                "Toyota Camry", "Tesla Model Y", "BMW X5", "Mitsubishi"
        ));
        System.out.println("Исходный список моделей:\n" + carModels);

        Set<String> uniqueModels = new TreeSet<>(Comparator.reverseOrder());
        uniqueModels.addAll(carModels);
        System.out.println("\nУникальные модели в обратном алфавитном порядке:\n" + uniqueModels);

        replaceTeslaModels(carModels);
        System.out.println("\nИсходный список после замены Tesla:\n" + carModels);
    }

    private static void replaceTeslaModels(List<String> models) {
        models.replaceAll(model -> model.contains("Tesla") ? "ELECTRO_CAR" : model);
    }
}
