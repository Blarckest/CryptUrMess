package uqac.dim.crypturmess;
import java.util.Date;

/**
 * This class represent a message.
 */
public class Message {
    private String message;
    private Date date;

    /**
     * Constructor
     * @param message
     * @param date
     */
    public Message(String message, Date date) {
        setMessage(message);
        setDate(date);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
