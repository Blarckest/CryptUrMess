package uqac.dim.crypturmess;

/**
 * User class represent a user.
 */
public class User {
    private int idUser;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private int age;

    /**
     * Constructor
     */
    public User(int id, String username) {
        setIdUser(id);
        setUsername(username);
    }

    /**
     * Constructor
     */
    public User(int id, String username, String firstname, String lastname, String email, int age) {
        setIdUser(id);
        setUsername(username);
        setFirstname(firstname);
        setLastname(lastname);
        setEmail(email);
        setAge(age);
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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
