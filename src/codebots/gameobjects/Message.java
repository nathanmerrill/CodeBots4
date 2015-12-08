package codebots.gameobjects;

public final class Message {
    public enum MessageType {
        INFORM,
        AVOID,
        ATTACK,
        HELP,
        START,
        STOP,
        CONFIRM,
        REJECT,
    }
    private final MessageType type;
    private final IPAddress address;

    public Message(MessageType type){
        this(type, null);
    }

    public Message(MessageType type, IPAddress address){
        this.type = type;
        this.address = address;
    }

    public MessageType getType() {
        return type;
    }

    public IPAddress getAddress() {
        return address;
    }
}
