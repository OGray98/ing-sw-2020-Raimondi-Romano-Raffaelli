package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidCommunicatorStringException;
import it.polimi.ingsw.exception.InvalidTypeMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Message class is an abstract class which represent an abstraction of exchanged messages between server and client
 */
public abstract class Message {

    private static final List<String> correctedCommunicators = new ArrayList<>(
            List.of("PLAYER0", "PLAYER1", "PLAYER2", "SERVER")
    );
    protected final List<TypeMessage> correctedType;
    private final String sender;
    private final String receiver;
    private final TypeMessage type;
    private final String content;

    public Message(String sender, String receiver, TypeMessage type, String content, List<TypeMessage> correctedType) throws NullPointerException, InvalidCommunicatorStringException {
        if (sender == null)
            throw new NullPointerException("sender");
        if (receiver == null)
            throw new NullPointerException("receiver");
        if (content == null)
            throw new NullPointerException("content");
        if (correctedType == null)
            throw new NullPointerException("correctedType");

        this.correctedType = new ArrayList<>(correctedType);
        this.correctedType.add(TypeMessage.ERROR);

        if (isInvalidString(sender))
            throw new InvalidCommunicatorStringException("sender", sender);
        if (isInvalidString(receiver))
            throw new InvalidCommunicatorStringException("receiver", receiver);
        if (!isValidType(type))
            throw new InvalidTypeMessage(this.getClass().getName(), type);

        this.sender = sender;
        this.type = type;
        this.content = content;
        this.receiver = receiver;

    }

    /**
     * Method that check if the value of String communicator is valid
     *
     * @param communicator String that represent the name of communicator
     * @return true iff communicator coincides with a correct string
     * @throws NullPointerException if communicator is null
     */
    private boolean isInvalidString(String communicator) throws NullPointerException {
        if (communicator == null)
            throw new NullPointerException("communicator");
        return correctedCommunicators.stream().noneMatch(cor -> cor.equals(communicator));
    }

    /**
     * Method that check if the value of TypeMessage type is valid
     *
     * @param type represent the type of message
     * @return true iff type coincides with a correct TypeMessage
     */
    private boolean isValidType(TypeMessage type) {
        return correctedType.stream().anyMatch(cor -> cor.compareTo(type) == 0);
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public TypeMessage getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", receiver=" + receiver +
                ", content=" + content +
                '}';
    }
}
