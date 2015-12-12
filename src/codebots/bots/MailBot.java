package codebots.bots;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import codebots.bot.ReadonlyBot;
import codebots.gameobjects.AddressBook;
import codebots.gameobjects.FunctionType;
import codebots.gameobjects.IPAddress;
import codebots.gameobjects.Message;

public class MailBot extends DefaultCodeBot {
    private final String TEAM = "Just your friendly neighborhood mail delivering robot.";
    private final String TEAMALT = "Mailmain";
    private final List<FunctionType> funcList;
    {
        List<FunctionType> list = new ArrayList<FunctionType>();
        list.add(FunctionType.SELECT_MESSAGE_RECIPIENTS);
        list.add(FunctionType.SEND_MESSAGE);
        list.add(FunctionType.PROCESS_MESSAGE);
        funcList = Collections.unmodifiableList(list);
    }

    @Override
    public IPAddress selectMessageRecipient() {
        AddressBook book = getAddressBook();
        IPAddress ip;
        List<IPAddress> l = book.getAddressesOfType(AddressBook.AddressType.TO_ATTACK);
        if(l.size() > 0) {
            ip = l.get(0);
            book.add(ip,AddressBook.AddressType.UNTRUSTED);
            return ip;
        }
        ip = book.getAddress(getRandom().nextInt(book.size()));
        book.add(ip,AddressBook.AddressType.UNTRUSTED);
        return ip;
    }

    @Override
    public Message sendMessage() {
        AddressBook book = getAddressBook();
        IPAddress ip;

        List<IPAddress> l = book.getAddressesOfType(AddressBook.AddressType.UNTRUSTED);
        if(l.size() > 0) {
            ip = l.get(0);
            book.add(ip,AddressBook.AddressType.TO_DEFEND);
            return new Message(Message.MessageType.INFORM,ip);
        }

        ip = book.getAddress(getRandom().nextInt(book.size()));
        book.add(ip,AddressBook.AddressType.UNTRUSTED);
        return new Message(Message.MessageType.INFORM,ip);
    }

    @Override
    public void processMessage(IPAddress source, Message message) {
        AddressBook book = getAddressBook();
        book.add(source,AddressBook.AddressType.TO_ATTACK);
        if(message.getAddress() != null)
            book.add(message.getAddress(),AddressBook.AddressType.TO_ATTACK);
    }

    @Override
    public FunctionType selectFunctionToBlock() {
        return FunctionType.SEND_MESSAGE;
    }

    @Override
    public IPAddress selectAttackTarget() {
        //Mailbot doesn't attack
        return null;
    }

    @Override
    public void readData(ReadonlyBot bot) { }

    @Override
    public FunctionType selectFunctionToReplace() {
        //if our attack selection gets overwritten,
        //then attack a message-based function
        return funcList.get(getTurnNumber()%3);
    }

    @Override
    public String getFlag() {
        return TEAM;
        //if flag is too long, use:
        //return TEAMALT;
    }
}