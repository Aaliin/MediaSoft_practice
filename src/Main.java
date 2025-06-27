public class Main {
    public static void main(String[] args) {
        BankAccount account1 = new BankAccount("Julia");
        BankAccount account2 = new BankAccount("Alina");

        account1.deposit(1500);
        account2.deposit(7000);

        System.out.println(account1);
        System.out.println(account2);

        account1.transfer(account2, 800);
        System.out.println(account1.getBalance()); // 700
        System.out.println(account2.getBalance()); // 7800

        account2.withdraw(500);
        System.out.println(account2.getBalance()); // 7300
        account1.transfer(account2, 800); // false

        account2.setBlocked(true);
        account2.deposit(900); // false
    }
}