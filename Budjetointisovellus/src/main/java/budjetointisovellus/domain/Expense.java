package budjetointisovellus.domain;

/**
 *
 * Luokka kuvaa yksittäistä kulua
 */
public class Expense {

    private String name;
    private double price;

    /**
     *
     * @param name kulun nimi
     * @param price kulun hinta
     */
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
