package budjetointisovellus.domain;

public class Budget {

    private String name;
    private double amount;

    public Budget(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

}
