package codebots.bots;

import codebots.bot.CodeBot;
import codebots.bot.ReadonlyBot;
import codebots.gameobjects.FunctionType;
import codebots.gameobjects.IPAddress;
import codebots.gameobjects.Message;

public class DefaultCodeBot extends CodeBot {

    @Override
    public FunctionType selectFunctionToBlock() {
        return FunctionType.GET_FLAG;
    }

    @Override
    public IPAddress selectAttackTarget() {
        return getAddressBook().getAddress(0);
    }

    @Override
    public FunctionType selectFunctionToReplace() {
        return FunctionType.GET_FLAG;
    }

    @Override
    public void readData(ReadonlyBot bot) {

    }

    @Override
    public IPAddress selectMessageRecipient() {
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
    public String getFlag() {
        return this.getClass().getName();
    }
}
