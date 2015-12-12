package codebots.bots;

import codebots.bot.ReadonlyBot;
import codebots.gameobjects.FunctionType;
import codebots.gameobjects.IPAddress;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thenumberone on 12/11/15.
 *
 * @author thenumberone
 */
public class Replacer extends DefaultCodeBot{

    private static final String NAME = "Replacer";
    private static final String BLOCK = NAME + ":BLOCK";
    private static final String ATTACK = NAME + ":ATTACK";
    private static final String REPLACE = NAME + ":REPLACE";
    private static final String READ = NAME + ":READ";
    private static final String FLAG = NAME + ":FLAG";
    private static final String SELECT = NAME + ":SELECT";
    private static final String SEND = NAME + ":SEND";

    private static final Map<String, FunctionType> commands;

    private static final String[] REPLACEMENT_ORDER = {
            REPLACE,
            FLAG,
            READ,
            ATTACK
    };

    private static final String DEFAULT = REPLACE;
    private static final String BLOCK_FUNCTION = FLAG;

    static {
        Map<String, FunctionType> c = new HashMap<>();
        c.put(BLOCK, FunctionType.SELECT_FUNCTION_TO_BLOCK);
        c.put(ATTACK, FunctionType.SELECT_ATTACK_TARGET);
        c.put(REPLACE, FunctionType.SELECT_FUNCTION_TO_REPLACE);
        c.put(READ, FunctionType.READ_DATA);
        c.put(FLAG, FunctionType.GET_FLAG);
        c.put(SELECT, FunctionType.SELECT_MESSAGE_RECIPIENTS);
        c.put(SEND, FunctionType.SEND_MESSAGE);
        commands = Collections.unmodifiableMap(c);
    }

    @Override
    public String getFlag() {
        return NAME;
    }

    @Override
    public void readData(ReadonlyBot bot) {
        for (String command : commands.keySet()){
            getVariables().remove(command);
        }
        for (String command : REPLACEMENT_ORDER){
            if (!functionsMatch(bot, commands.get(command))) {
                getVariables().add(command, "");
                return;
            }
        }
    }

    @Override
    public FunctionType selectFunctionToBlock() {
        return commands.get(BLOCK_FUNCTION);
    }

    @Override
    public IPAddress selectAttackTarget() {
        getAddressBook().clear();
        return getAddressBook().getAddress(0);
    }

    @Override
    public FunctionType selectFunctionToReplace() {
        for (String command : REPLACEMENT_ORDER){
            if (getVariables().has(command)) {
                getVariables().remove(command);
                return commands.get(command);
            }
        }
        return commands.get(DEFAULT);
    }
}