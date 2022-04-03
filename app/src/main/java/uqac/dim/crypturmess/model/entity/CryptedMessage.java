package uqac.dim.crypturmess.model.entity;

import java.util.Date;

import uqac.dim.crypturmess.utils.crypter.Crypter;
import uqac.dim.crypturmess.utils.crypter.RSACrypter;

/**
 * This class represent a message.
 */

public class CryptedMessage {
    private byte[] message;
    private int idSender;
    private int idReceiver;
    private Date date;

    /**
     * Constructor
     */
    public CryptedMessage(byte[] message, int idSender, int idReceiver, Date date) {
        setMessage(message);
        setIdSender(idSender);
        setIdReceiver(idReceiver);
        setDate(date);
    }

    public CryptedMessage(Message message) {
        Crypter crypter = new RSACrypter();
        /*this.message = crypter.encryptToSend(message.getMessage(),message.getIdReceiver());
        this.idSender = message.getIdSender();
        this.idReceiver = message.getIdReceiver();
        this.date = message.getDate();*/
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
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
