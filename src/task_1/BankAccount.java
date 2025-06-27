package task_1;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

public class BankAccount {
    private final String ownerName;
    private int balance;
    private final LocalDateTime openingDate;
    private boolean isBlocked;
    private final String number;

    public BankAccount(String ownerName) {
        this.ownerName = ownerName;
        this.balance = 0;
        this.openingDate = LocalDateTime.now();
        this.isBlocked = false;
        this.number = generateAccountNumber();
    }

    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public boolean deposit(int amount) {
        if (isBlocked || amount <= 0) {
            return false;
        }
        balance += amount;
        return true;
    }

    public boolean withdraw(int amount) {
        if (isBlocked || amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    public boolean transfer(BankAccount otherAccount, int amount) {
        if (otherAccount == null ||
                isBlocked ||
                otherAccount.isBlocked ||
                amount <= 0 ||
                amount > balance) {
            return false;
        }

        balance -= amount;
        otherAccount.balance += amount;
        return true;
    }

    @Override
    public String toString() {
        return "{\n\tname='" + ownerName + '\'' +
                "\n\tnumber='" + number + '\'' +
                "\n\tbalance=" + balance +
                "\n\ttime=" + openingDate +
                "\n\tstatus=" + (isBlocked ? "blocked" : "active") +
                "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getBalance() {
        return balance;
    }

    public LocalDateTime getOpeningDate() {
        return openingDate;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
