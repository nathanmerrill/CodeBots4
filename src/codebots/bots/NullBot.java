package codebots.bots;
import codebots.gameobjects.*;
public class NullBot extends DefaultCodeBot {
    public IPAddress selectMessageRecipient() {
        return null;
    }
    public Message sendMessage() {
        return null;
    }
    public IPAddress selectAttackTarget() {
        return null;
    }
    public FunctionType selectFunctionToReplace() {
        return null;
    }
    public FunctionType selectFunctionToBlock() {
        return null;
    }
    public String getFlag(){
        return null;
    }
}