import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

class BankAccount {
    private String accountNumber;
    private double balance;
    private String pin;

    // Constructor
    public BankAccount(String accountNumber, double initialBalance, String pin) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.pin = pin;
    }

    // Method to deposit money:
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit of " + amount + " successful. New balance: " + balance);
            showTransactionTime();
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    // Method to withdraw money with PIN validation:
    public void withdraw(double amount, String enteredPin) {
        if (!enteredPin.equals(pin)) {
            System.out.println("Incorrect PIN. Withdrawal denied.");
            return;
        }

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

    // Method for getting the account number:
    public String getAccountNumber() {
        return accountNumber;
    }

    // This is for displaying the current date and time after transactions:
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

    // Constructor:
    public Customer(String name, String customerId) {
        this.name = name;
        this.customerId = customerId;
        this.accounts = new ArrayList<>();
    }


    public void createAccount(String accountNumber, double initialBalance, String pin) {
        if (accountNumber.length() != 6) { // Validate 6-digit account number
            System.out.println("Error: Account number must be exactly 6 digits.");
            return;
        }

        if (pin.length() != 4) {
            System.out.println("Error: PIN must be exactly 4 digits.");
            return;
        }

        BankAccount newAccount = new BankAccount(accountNumber, initialBalance, pin);
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
        System.out.println("Account  Number  not found.");
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

public class BankAccountApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Creating a new account:
        System.out.println("To create an account, you have to enter your name, customer ID, account number, initial balance, and PIN.");
        System.out.print("Enter your Full Name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter your customer ID: ");
        String customerId = scanner.nextLine();

        Customer customer = new Customer(customerName, customerId);

        // Create a bank account:
        System.out.print("Enter an account number (6 digits): "); // Prompt for 6 digits
        String accountNumber = scanner.nextLine();

        System.out.print("Enter a 4-digit PIN: ");
        String pin = scanner.nextLine();

        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();

        customer.createAccount(accountNumber, initialBalance, pin);

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
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = scanner.nextDouble();

                    System.out.print("Enter your 4-digit PIN: ");
                    String enteredPin = scanner.next();

                    account.withdraw(withdrawalAmount, enteredPin);
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
