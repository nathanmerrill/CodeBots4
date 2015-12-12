package codebots.bots;

import codebots.bot.CodeBot;
import codebots.bot.ReadonlyBot;
import codebots.gameobjects.*;

public class AmnesiaBot extends CodeBot {

    private void clear(){
        getAddressBook().clear();
        getAddressBook().add(getAddressBook().getAddress(0), AddressBook.AddressType.TRUSTED);
        getVariables().clear();
        getLog().clear();
    }

    @Override
    public IPAddress selectMessageRecipient() {
        clear();
        return getAddressBook().getAddress(0);
    }

    @Override
    public Message sendMessage() {
        clear();
        Message.MessageType[] values = Message.MessageType.values();
        return new Message(values[getRandom().nextInt(values.length)], getAddressBook().getAddress(0));
    }

    @Override
    public void processMessage(IPAddress source, Message message) {
        clear();
    }

    @Override
    public FunctionType selectFunctionToBlock() {
        clear();
        return getTurnNumber() % 2 == 0 ?
                FunctionType.GET_FLAG: FunctionType.SELECT_FUNCTION_TO_BLOCK;
    }

    @Override
    public IPAddress selectAttackTarget() {
        clear();
        return getAddressBook().getAddress(0);
    }

    @Override
    public void readData(ReadonlyBot bot) {
        clear();
    }

    @Override
    public FunctionType selectFunctionToReplace() {
        clear();
        FunctionType[] values =  FunctionType.values();
        return values[getRandom().nextInt(values.length)];
        //random gives a 7/8 chance of successes.
    }

    @Override
    public String getFlag() {
        return "Who Am I?";
    }
}