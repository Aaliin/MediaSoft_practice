package task_2.number_1;

import java.util.Arrays;
import java.util.Random;

public class CarPark {
    public static void main(String[] args) {
        int[] productionYears = generateProductionYears(50);

        System.out.println("Все годы выпуска:");
        System.out.println(Arrays.toString(productionYears));

        System.out.println("\nМашины выпущенные после 2015 года:");
        printCarsAfter2015(productionYears);

        double averageAge = calculateAverageAge(productionYears);
        System.out.printf("\nСредний возраст автомобилей: %.2f года\n", averageAge);
    }

    private static int[] generateProductionYears(int count) {
        Random random = new Random();
        int[] years = new int[count];
        for (int i = 0; i < count; i++) {
            years[i] = 2000 + random.nextInt(26);
        }
        return years;
    }

    private static void printCarsAfter2015(int[] years) {
        int countCar = 0;
        for (int year : years) {
            if (year > 2015) {
                System.out.println(year);
                countCar += 1;
            }
        }
        System.out.println("\nВсего таких машин: " + countCar);
    }

    private static double calculateAverageAge(int[] years) {
        int totalAge = 0;

        for (int year : years) {
            totalAge += 2025 - year;
        }

        return (double) totalAge / years.length;
    }
}
