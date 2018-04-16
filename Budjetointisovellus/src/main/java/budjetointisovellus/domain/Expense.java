package budjetointisovellus.domain;

public class Expense {

    private String name;
    private double price;

    public Expense(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

}
