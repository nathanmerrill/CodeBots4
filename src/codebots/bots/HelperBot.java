package codebots.bots;

import codebots.bot.CodeBot;
import codebots.bot.ReadonlyBot;
import codebots.gameobjects.FunctionType;
import codebots.gameobjects.IPAddress;
import codebots.gameobjects.Message;

public class HelperBot extends CodeBot {

    @Override
    public IPAddress selectMessageRecipient() {
        getAddressBook().clear();
        return getAddressBook().getAddress(0);
    }

    @Override
    public Message sendMessage() {
        return new Message(Message.MessageType.INFORM);
    }

    @Override
    public void processMessage(IPAddress source, Message message) {

    }

    @Override
    public FunctionType selectFunctionToBlock() {
        return FunctionType.GET_FLAG;
    }

    @Override
    public IPAddress selectAttackTarget() {
        getAddressBook().clear();
        return getAddressBook().getAddress(0);
    }

    @Override
    public void readData(ReadonlyBot bot) {

    }

    @Override
    public FunctionType selectFunctionToReplace() {
        return FunctionType.GET_FLAG;
    }

    @Override
    public String getFlag() {
        return "I'm Helping!";
    }
}