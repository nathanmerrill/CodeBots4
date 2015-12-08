package codebots.bot;

import codebots.controller.Round;
import codebots.gameobjects.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class Bot {
    private final Map<FunctionType, CodeBot> functionOwners;
    private final AddressBook addressBook;
    private final Log log;
    private final Variables variables;
    private final CodeBot parent;
    private final Random random;
    private final Round round;

    public Bot(CodeBot parent, Round round){
        functionOwners = new HashMap<>();
        for (FunctionType type: FunctionType.values()){
            functionOwners.put(type, parent);
        }
        addressBook = new AddressBook(round);
        log = new Log(round);
        variables = new Variables();
        this.parent = parent;
        this.random = round.getRandom();
        this.round = round;
    }

    public IPAddress fromString(String string){
        return round.fromString(string);
    }

    public Random getRandom(){
        return random;
    }

    public CodeBot getParent(){
        return parent;
    }

    public CodeBot getOwner(FunctionType type){
        CodeBot owner = functionOwners.get(type);
        owner.setUnderlyingBot(this);
        return owner;
    }

    public IPAddress selectMessageRecipient(){
        IPAddress recipient = getOwner(FunctionType.SELECT_MESSAGE_RECIPIENTS).selectMessageRecipient();
        log.addLog(new Log.LogEntry(FunctionType.SELECT_MESSAGE_RECIPIENTS).address(recipient));
        return recipient;
    }

    public Message sendMessage(){
        Message message = getOwner(FunctionType.SEND_MESSAGE).sendMessage();
        log.addLog(new Log.LogEntry(FunctionType.SEND_MESSAGE).message(message));
        return message;
    }

    public void processMessage(IPAddress source, Message message){
        getOwner(FunctionType.PROCESS_MESSAGE).processMessage(source, message);
        log.addLog(new Log.LogEntry(FunctionType.PROCESS_MESSAGE).address(source).message(message));
    }

    public IPAddress selectAttackTarget(){
        IPAddress address = getOwner(FunctionType.SELECT_ATTACK_TARGET).selectAttackTarget();
        log.addLog(new Log.LogEntry(FunctionType.SELECT_ATTACK_TARGET).address(address));
        return address;
    }
    public void readData(ReadonlyBot bot){
        getOwner(FunctionType.READ_DATA).readData(bot);
        log.addLog(new Log.LogEntry(FunctionType.READ_DATA));
    }
    public FunctionType selectFunctionToReplace(){
        FunctionType toReplace = getOwner(FunctionType.SELECT_FUNCTION_TO_REPLACE).selectFunctionToReplace();
        log.addLog(new Log.LogEntry(FunctionType.SELECT_FUNCTION_TO_REPLACE).targetFunction(toReplace));
        return toReplace;
    }
    public FunctionType selectFunctionToBlock(){
        FunctionType toBlock =  getOwner(FunctionType.SELECT_FUNCTION_TO_BLOCK).selectFunctionToBlock();
        log.addLog(new Log.LogEntry(FunctionType.SELECT_FUNCTION_TO_BLOCK).targetFunction(toBlock));
        return toBlock;
    }
    public String getFlag(){
        return getOwner(FunctionType.GET_FLAG).getFlag();
    }

    public AddressBook getAddressBook(){
        return addressBook;
    }

    public Log getLog(){
        return log;
    }

    public Variables getVariables(){
        return variables;
    }

    public void replace(FunctionType type, CodeBot bot){
        functionOwners.put(type, bot);
    }
    

}
