
import java.util.*;
public class StockTradingSimulator {
    static Scanner sc=new Scanner(System.in);
    static Map<String, Stock> market=new HashMap<>();
    static Map<String, Holding> portfolio=new HashMap<>();
    static double cash=100000; 

    public static void main(String[] args) {
        initializeMarket();
        int choice;
        do {
            System.out.println("\n===== SIMULATED STOCK TRADING PLATFORM =====");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1: viewMarket(); break;
                case 2: buyStock(); break;
                case 3: sellStock(); break;
                case 4: viewPortfolio(); break;
                case 5: System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid choice.");
            }
            simulateMarket(); 

        } while (choice!=5);
    }
    static void initializeMarket() {
        market.put("AAPL", new Stock("AAPL", "Apple Inc.", 150));
        market.put("GOOG", new Stock("GOOG", "Alphabet Inc.", 2700));
        market.put("TSLA", new Stock("TSLA", "Tesla Inc.", 700));
        market.put("AMZN", new Stock("AMZN", "Amazon.com", 3300));
    }
    static void viewMarket() {
        System.out.println("\n--- Market Data ---");
        for (Stock stock : market.values()) {
            System.out.printf("%s (%s): $%.2f\n", stock.name, stock.symbol, stock.price);
        }
    }
    static void buyStock() {
        sc.nextLine(); 
        System.out.print("Enter stock symbol to buy: ");
        String symbol=sc.nextLine().toUpperCase();

        if (!market.containsKey(symbol)) {
            System.out.println("Invalid stock symbol.");
            return;
        }
        Stock stock=market.get(symbol);
        System.out.printf("Current price of %s: $%.2f\n", stock.symbol, stock.price);
        System.out.print("Enter quantity to buy: ");
        int qty=sc.nextInt();

        double cost=stock.price * qty;
        if (cost>cash) {
            System.out.println("Not enough funds.");
            return;
        }

        cash-=cost;
        Holding holding=portfolio.get(symbol);
        if (holding==null) {
            portfolio.put(symbol, new Holding(stock, qty, cost));
        } else {
            holding.quantity+=qty;
            holding.totalSpent+=cost;
        }
        System.out.printf("Purchased %d shares of %s for $%.2f\n", qty, symbol, cost);
    }
    static void sellStock() {
        sc.nextLine(); 
        System.out.print("Enter stock symbol to sell: ");
        String symbol=sc.nextLine().toUpperCase();

        if (!portfolio.containsKey(symbol)) {
            System.out.println("You do not own this stock.");
            return;
        }
        Holding holding=portfolio.get(symbol);
        System.out.printf("You have %d shares of %s at current price $%.2f\n",
                          holding.quantity, symbol, holding.stock.price);
        System.out.print("Enter quantity to sell: ");
        int qty=sc.nextInt();

        if (qty>holding.quantity) {
            System.out.println("You don't have that many shares.");
            return;
        }

        double revenue=qty * holding.stock.price;
        cash+=revenue;
        holding.quantity-=qty;
        holding.totalSpent*=((double) (holding.quantity) / (holding.quantity+qty));
        if (holding.quantity == 0) {
            portfolio.remove(symbol);
        }
        System.out.printf("Sold %d shares of %s for $%.2f\n", qty, symbol, revenue);
    }
    static void viewPortfolio() {
        System.out.println("\n--- Portfolio ---");
        if (portfolio.isEmpty()) {
            System.out.println("You don't own any stocks.");
        } else {
            double totalValue = 0;
            for (Holding h : portfolio.values()) {
                System.out.printf("%s: %d shares | Market Value: $%.2f | P/L: $%.2f\n",
                                  h.stock.symbol, h.quantity, h.marketValue(), h.profitLoss());
                totalValue+=h.marketValue();
            }
            System.out.printf("Total Portfolio Value: $%.2f\n", totalValue);
        }
        System.out.printf("Available Cash: $%.2f\n", cash);
    }

    static void simulateMarket() {
        for (Stock stock : market.values()) {
            stock.updatePrice();
        }
    }
}
class Stock {
    String symbol;
    String name;
    double price;

    Stock(String symbol, String name, double price) {
        this.symbol=symbol;
        this.name=name;
        this.price=price;
    }

    void updatePrice() {
        double change=(Math.random() - 0.5) * 10; 
        price=Math.max(1, price + change);
    }
}

class Holding {
    Stock stock;
    int quantity;
    double totalSpent;

    Holding(Stock stock, int quantity, double totalSpent) {
        this.stock=stock;
        this.quantity=quantity;
        this.totalSpent=totalSpent;
    }
    double marketValue() {
        return quantity * stock.price;
    }
    double profitLoss() {
        return marketValue() - totalSpent;
    }
}