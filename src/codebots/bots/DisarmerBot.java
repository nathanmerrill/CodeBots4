package codebots.bots;

import codebots.bot.CodeBot;
import codebots.bot.ReadonlyBot;
import codebots.gameobjects.*;

import java.util.List;

public class DisarmerBot extends CodeBot {
    public IPAddress selectMessageRecipient() { return null; }
    public Message sendMessage() { return null; }

    public void processMessage(IPAddress source, Message message) {
        if (message != null && message.getAddress() != null && message.getType() == Message.MessageType.ATTACK)
            getAddressBook().add(message.getAddress());
    }

    public IPAddress selectAttackTarget() {
        AddressBook book = getAddressBook();
        IPAddress address = book.getAddress(getRandom().nextInt(book.size()));
        book.clear();
        return address;
    }

    public void readData(ReadonlyBot bot) { getLog().clear(); /*Safety*/ }
    public FunctionType selectFunctionToReplace() { return FunctionType.SELECT_FUNCTION_TO_BLOCK; }
    public FunctionType selectFunctionToBlock() { return FunctionType.SELECT_FUNCTION_TO_BLOCK; }
    public String getFlag() { return "Expelliarmus!"; }
}