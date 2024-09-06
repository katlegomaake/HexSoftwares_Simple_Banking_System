import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

class BankAccount {
    private String accountNumber;
    private double balance;

    // Constructor
    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    // Method to deposit the money:
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit of " + amount + " successful. New balance: " + balance);
            showTransactionTime();
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    // Method to Withdraw Money:
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
        } else if (amount > balance) {
            System.out.println("Insufficient funds for this withdrawal.");
        } else {
            balance -= amount;
            System.out.println("Withdrawal of " + amount + " successful. New balance: " + balance);
            showTransactionTime();
        }
    }

    // Method to check the balance:
    public double checkBalance() {
        return balance;
    }

    // Method for an Account Number:
    public String getAccountNumber() {
        return accountNumber;
    }

    // This is for displaying the current date and time after withdrawing and depositing money:
    private void showTransactionTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentTime.format(formatter);
        System.out.println("Transaction completed on: " + formattedDateTime);
    }
}

class Customer {
    private String name;
    private String customerId;
    private ArrayList<BankAccount> accounts;

    // Constructor
    public Customer(String name, String customerId) {
        this.name = name;
        this.customerId = customerId;
        this.accounts = new ArrayList<>();
    }

    // Creating a new bank account
    public void createAccount(String accountNumber, double initialBalance) {
        BankAccount newAccount = new BankAccount(accountNumber, initialBalance);
        accounts.add(newAccount);
        System.out.println("Congratulations, " + name + "! You have created an account with account number: " + accountNumber);
    }

    // Find account by account number:
    public BankAccount findAccount(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        System.out.println("Account not found.");
        return null;
    }

    // Getting customer name:
    public String getName() {
        return name;
    }

    // Getting customer ID:
    public String getCustomerId() {
        return customerId;
    }
}

public class BankAccountApp{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // method to Create a new account:
        System.out.println("To create an Account you have to enter your names, customer ID, account number, and initial balance.");
        System.out.println("Enter your Fullname: ");
        String customerName = scanner.nextLine();

        System.out.println("Enter your customer ID: ");
        String customerId = scanner.nextLine();

        Customer customer = new Customer(customerName, customerId);

        // Create bank accounts:
        System.out.println("Enter an account number to create: ");
        String accountNumber = scanner.nextLine();

        System.out.println("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();

        customer.createAccount(accountNumber, initialBalance);

        // Main menu loop:
        boolean exit = false;
        while (!exit) {
            System.out.println("\nWhat would you like to do next?");
            System.out.println("1. Deposit Money");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            int choice = scanner.nextInt();

            BankAccount account = customer.findAccount(accountNumber);
            if (account == null) {
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 2:
                    System.out.println("Enter withdrawal amount: ");
                    double withdrawalAmount = scanner.nextDouble();
                    account.withdraw(withdrawalAmount);
                    break;
                case 3:
                    System.out.println("Your balance is: " + account.checkBalance());
                    break;
                case 4:
                    System.out.println("Thank you! Goodbye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice, please select again.");
            }
        }

        scanner.close();
    }
}
