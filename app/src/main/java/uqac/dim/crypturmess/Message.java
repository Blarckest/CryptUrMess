package uqac.dim.crypturmess;
import java.util.Date;

/**
 * This class represent a message.
 */
public class Message {
    private String message;
    private int idSender;
    private int idReceiver;
    private Date date;

    /**
     * Constructor
     */
    public Message(String message, int idSender, int idReceiver, Date date) {
        setMessage(message);
        setIdSender(idSender);
        setIdReceiver(idReceiver);
        setDate(date);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIdSender() {
        return idSender;
    }

    public void setIdSender(int idSender) {
        this.idSender = idSender;
    }

    public int getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(int idReceiver) {
        this.idReceiver = idReceiver;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
