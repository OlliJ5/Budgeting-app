package budjetointisovellus.domain;

import java.util.Objects;

/**
 *
 * Luokka kuvaa sovelluksen käyttäjää
 */
public class User {

    private String username;
    private String name;
    private String password;

    /**
     *
     * @param username käyttäjän käyttäjätunnus
     * @param name käyttäjän nimi
     * @param password käyttäjän salasana
     */
    public User(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        User user = (User) obj;
        if (user.getUsername().equals(this.username)) {
            return true;
        }
        return false;
    }

}
