package uqac.dim.crypturmess;

import java.util.ArrayList;

/**
 * This class represent a conversation between two users.
 */
public class Conversation {
    private ArrayList<Message> listMessages;

    /**
     * Construction
     */
    public Conversation() {
        listMessages = new ArrayList<>();
    }

    public ArrayList<Message> getListMessages() {
        return listMessages;
    }
}
