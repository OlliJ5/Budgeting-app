package budjetointisovellus.domain;

import java.util.Objects;

/**
 *
 * Luokka kuvaa yksittäistä budjettia
 */
public class Budget {

    private String name;
    private double amount;

    /**
     *
     * @param name budjetin nimi
     * @param amount budjetin koko liukulukuna
     */
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

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Budget other = (Budget) obj;
        if (Double.doubleToLongBits(this.amount) != Double.doubleToLongBits(other.amount)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

}
