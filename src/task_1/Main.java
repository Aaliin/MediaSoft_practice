package task_1;

public class Main {
    public static void main(String[] args) {
        BankAccount account1 = new BankAccount("Julia");
        BankAccount account2 = new BankAccount("Alina");

        account1.deposit(1500);
        account2.deposit(7000);

        System.out.println("Информация о счете 1: " + account1);
        System.out.println("Информация о счете 2: " + account2);

        account1.transfer(account2, 800);
        System.out.println("Баланс 1: " + account1.getBalance()); // 700
        System.out.println("Баланс 2: " + account2.getBalance()); // 7800

        account2.withdraw(500);
        System.out.println("Баланс 2 после снятия 500: " + account2.getBalance()); // 7300
        account1.transfer(account2, 800);

        account2.setBlocked(true);
        account2.deposit(900);

        BankAccount account3 = account1;
        System.out.println(account1.equals(account3));
        System.out.println(account1.equals(account2));

        System.out.println("Хэш-код счёта 1: " + account1.hashCode());
        System.out.println("Хэш-код счёта 2: " + account2.hashCode());
        System.out.println("Хэш-код счёта 3: " + account3.hashCode());
    }
}