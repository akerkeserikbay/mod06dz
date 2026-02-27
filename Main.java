import java.util.*;
// MAIN CLASS
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // STRATEGY PATTERN
        System.out.println("=== STRATEGY PATTERN (Payment) ===");
        System.out.println("Choose payment method:");
        System.out.println("1 - Credit Card");
        System.out.println("2 - PayPal");
        System.out.println("3 - Crypto");

        String choice = scanner.nextLine();

        PaymentContext context = new PaymentContext();

        switch (choice) {
            case "1":
                context.setStrategy(new CreditCardPayment());
                break;
            case "2":
                context.setStrategy(new PayPalPayment());
                break;
            case "3":
                context.setStrategy(new CryptoPayment());
                break;
            default:
                System.out.println("Invalid choice");
                return;
        }

        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        context.pay(amount);

        // OBSERVER PATTERN
        System.out.println("\n=== OBSERVER PATTERN (Currency Exchange) ===");

        CurrencyExchange exchange = new CurrencyExchange();

        Observer bank = new Bank();
        Observer investor = new Investor();
        Observer mobileApp = new MobileApp();

        exchange.addObserver(bank);
        exchange.addObserver(investor);
        exchange.addObserver(mobileApp);

        exchange.setRate("USD", 480);
        exchange.setRate("EUR", 510);

        exchange.removeObserver(investor);

        exchange.setRate("BTC", 30000000);
    }
}

// STRATEGY PATTERN
interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid " + amount + " by Credit Card");
    }
}

class PayPalPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid " + amount + " via PayPal");
    }
}

class CryptoPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Cryptocurrency");
    }
}

class PaymentContext {
    private PaymentStrategy strategy;

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void pay(double amount) {
        if (strategy == null) {
            System.out.println("Payment method not selected!");
            return;
        }
        strategy.pay(amount);
    }
}

// OBSERVER PATTERN

interface Observer {
    void update(String currency, double rate);
}

interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String currency, double rate);
}

class CurrencyExchange implements Subject {

    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void setRate(String currency, double rate) {
        System.out.println("\nExchange rate updated: " + currency + " = " + rate);
        notifyObservers(currency, rate);
    }

    public void notifyObservers(String currency, double rate) {
        for (Observer observer : observers) {
            observer.update(currency, rate);
        }
    }
}

class Bank implements Observer {
    public void update(String currency, double rate) {
        System.out.println("Bank received update: " + currency + " = " + rate);
    }
}

class Investor implements Observer {
    public void update(String currency, double rate) {
        System.out.println("Investor analyzing new rate: " + currency + " = " + rate);
    }
}

class MobileApp implements Observer {
    public void update(String currency, double rate) {
        System.out.println("Mobile App notification: " + currency + " = " + rate);
    }
}