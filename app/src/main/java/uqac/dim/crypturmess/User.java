package uqac.dim.crypturmess;

/**
 * User class represent a user.
 */
public class User {

    private String username;
    private String firstname;
    private String lastname;
    private int age;

    /**
     * Constructor
     * @param username
     */
    public User(String username) {
        setUsername(username);
    }

    /**
     * Constructor
     * @param username
     * @param firstname
     * @param lastname
     */
    public User(String username, String firstname, String lastname) {
        setUsername(username);
        setFirstname(firstname);
        setLastname(lastname);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
