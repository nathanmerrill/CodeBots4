package codebots.bots;

import codebots.bot.CodeBot;
import codebots.bot.ReadonlyBot;
import codebots.gameobjects.*;

public class MarkedBot extends CodeBot {

    @Override
    public IPAddress selectMessageRecipient() {
        Variables v = getVariables();
        AddressBook ab = getAddressBook();
        if(getTurnNumber()==0)
            v.add(v.get("ID"),"true");
        if("true".equals(v.get("hasOurFlag"))){
            ab.remove(ab.getAddress(0));
            v.remove("hasOurFlag");
        }
        return ab.getAddress(0);
    }

    @Override
    public Message sendMessage() {
        return new Message(Message.MessageType.STOP);
    }

    @Override
    public void processMessage(IPAddress source, Message message) {
        if(message.getType() != Message.MessageType.STOP)
            getAddressBook().add(source, AddressBook.AddressType.TO_ATTACK);
    }

    @Override
    public FunctionType selectFunctionToBlock() {
        Variables v = getVariables();
        if("true".equals(v.get(v.get("ID"))))
            return FunctionType.GET_FLAG;
        return FunctionType.SELECT_FUNCTION_TO_BLOCK;
    }

    @Override
    public IPAddress selectAttackTarget() {
        Variables v = getVariables();
        if("true".equals(v.get(v.get("ID"))))
            return getAddressBook().getAddress(0);
        else
            return null;
    }

    @Override
    public void readData(ReadonlyBot bot) {
        Variables v = getVariables();
        if(functionsMatch(bot, FunctionType.GET_FLAG))
            v.add("hasOurFlag", "true");
        else if("false".equals(v.get("hasOurFlag")))
            v.add("hasOurFlag", "false2");
        else
            v.add("hasOurFlag", "false");
    }

    @Override
    public FunctionType selectFunctionToReplace() {
        Variables v = getVariables();
        if("true".equals(v.get(v.get("ID"))))
            if(!v.has("hasOurFlag") || "false".equals(v.get("hasOurFlag")))
                return FunctionType.GET_FLAG;
            else if("false2".equals(v.get("hasOurFlag")))
                return FunctionType.SELECT_FUNCTION_TO_BLOCK;
            else
                return FunctionType.SEND_MESSAGE;
        return FunctionType.SELECT_FUNCTION_TO_REPLACE;
    }

    @Override
    public String getFlag() {
        return this.getClass().getName();
    }
}