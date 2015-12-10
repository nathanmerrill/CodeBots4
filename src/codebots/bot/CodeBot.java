package codebots.bot;

import codebots.gameobjects.*;

import java.util.Random;

public abstract class CodeBot {

    private Bot underlyingBot;

    public final void setUnderlyingBot(Bot bot){
        this.underlyingBot = bot;
    }

    public abstract IPAddress selectMessageRecipient();
    public abstract Message sendMessage();
    public abstract void processMessage(IPAddress source, Message message);
    public abstract IPAddress selectAttackTarget();
    public abstract void readData(ReadonlyBot bot);
    public abstract FunctionType selectFunctionToReplace();
    public abstract FunctionType selectFunctionToBlock();
    public abstract String getFlag();

    protected final IPAddress fromString(String string){
        return underlyingBot.fromString(string);
    }

    protected final AddressBook getAddressBook(){
        return underlyingBot.getAddressBook();
    }

    protected final Log getLog(){
        return underlyingBot.getLog();
    }

    protected final Variables getVariables(){
        return underlyingBot.getVariables();
    }

    protected final Random getRandom(){
        return underlyingBot.getRandom();
    }

    protected final boolean functionsMatch(ReadonlyBot bot, FunctionType type){
        return bot.functionsMatch(underlyingBot, type);
    }

    protected final int getTurnNumber(){
        return underlyingBot.getTurnNumber();
    }

    protected final IPAddress personalAddress(){
        return underlyingBot.getSelfIPAddress();
    }
}
