package codebots.bots;

import codebots.bot.CodeBot;
import codebots.bot.ReadonlyBot;
import codebots.gameobjects.AddressBook;
import codebots.gameobjects.FunctionType;
import codebots.gameobjects.IPAddress;
import codebots.gameobjects.Message;

public class RandomCodeBot extends CodeBot {


    @Override
    public FunctionType selectFunctionToBlock() {
        FunctionType[] values =  FunctionType.values();
        return values[getRandom().nextInt(values.length)];
    }

    @Override
    public IPAddress selectAttackTarget() {
        AddressBook book = getAddressBook();
        return book.getAddress(getRandom().nextInt(book.size()));
    }

    @Override
    public FunctionType selectFunctionToReplace() {
        FunctionType[] values =  FunctionType.values();
        return values[getRandom().nextInt(values.length)];
    }

    @Override
    public void readData(ReadonlyBot bot) {

    }

    @Override
    public IPAddress selectMessageRecipient() {
        AddressBook book = getAddressBook();
        return book.getAddress(getRandom().nextInt(book.size()));
    }

    @Override
    public Message sendMessage() {
        Message.MessageType[] values = Message.MessageType.values();
        return new Message(values[getRandom().nextInt(values.length)]);
    }

    @Override
    public void processMessage(IPAddress source, Message message) {

    }

    @Override
    public String getFlag() {
        return "Random bot loves you";
    }
}
