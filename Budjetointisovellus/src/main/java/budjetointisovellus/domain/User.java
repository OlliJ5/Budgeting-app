
package budjetointisovellus.domain;


public class User {
    private String username;
    private String name;
    private double budget;
    
    public User(String username, String name, double budget) {
        this.username = username;
        this.name = name;
        this.budget = budget;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public Double getBudget() {
        return this.budget;
    }
    
}
