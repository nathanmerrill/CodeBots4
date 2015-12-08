package codebots.controller;

import codebots.bot.Bot;
import codebots.bot.CodeBot;
import codebots.bot.ReadonlyBot;
import codebots.gameobjects.FunctionType;
import codebots.gameobjects.IPAddress;
import codebots.gameobjects.Message;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Round {

    private final List<Bot> allBots;
    private final HashMap<Class<? extends CodeBot>, Integer> IDs;
    private final HashMap<Bot, IPAddress> botToAddress;
    private final HashMap<IPAddress, Bot> addressToBot;
    private final HashMap<String, IPAddress> allAddresses;
    private final Random random;
    private int currentTurn;

    public Round(Collection<Class<? extends CodeBot>> botTypes, Random random){
        currentTurn = -1;
        IDs = new HashMap<>();
        allBots = new ArrayList<>();
        botToAddress = new HashMap<>();
        addressToBot = new HashMap<>();
        allAddresses = new HashMap<>();
        this.random = random;

        for (int i = 0; i < Globals.NUM_BOT_COPIES_PER_ROUND; i++) {
            allBots.addAll(CodeBotFactory.create(botTypes).stream()
                    .map(a -> new Bot(a, this))
                    .collect(Collectors.toList()));
        }
        botTypes.forEach(type -> IDs.put(type, random.nextInt()));

        allBots.forEach(bot -> botToAddress.put(bot, generateNextIPAddress()));
        botToAddress.entrySet().forEach(entry -> addressToBot.put(entry.getValue(), entry.getKey()));
        allBots.forEach(this::initializeBot);

    }

    public IPAddress fromString(String string){
        return allAddresses.get(string);
    }

    private IPAddress generateNextIPAddress(){
        IPAddress address = new IPAddress(random);
        if (allAddresses.containsKey(address.getAddress())){
            return generateNextIPAddress();
        }
        return address;
    }

    public Random getRandom(){
        return random;
    }

    public void run(){
        for (int i = 0; i < Globals.NUM_TURNS_IN_ROUND; i++){
            step();
        }
    }

    public Map<String, Integer> getScores() {
        return allBots.stream()
                .collect(Collectors.groupingBy(Bot::getFlag, Collectors.counting()))
                .entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry->entry.getValue().intValue()));
    }

    public void step(){
        currentTurn++;
        Collections.shuffle(allBots);
        sendMessages();
        attack();
    }

    private void sendMessages(){
        HashMap<Bot, Bot> recipients = new HashMap<>();
        allBots.forEach(bot -> recipients.put(bot, addressToBot.get(bot.selectMessageRecipient())));
        HashMap<Bot, Message> messages = new HashMap<>();
        allBots.forEach(bot -> messages.put(bot, bot.sendMessage()));
        recipients.entrySet().forEach(entry -> entry.getValue()
                .processMessage(botToAddress.get(entry.getKey()), messages.get(entry.getKey())));
    }

    private void attack(){
        Map<Bot, FunctionType> blocked = allBots.stream()
                .collect(Collectors.toMap(Function.identity(), Bot::selectFunctionToBlock));

        Map<Bot, List<Bot>> attacks = allBots.stream()
                .collect(Collectors.groupingBy(bot -> addressToBot.get(bot.selectAttackTarget()), Collectors.toList()));

        Map<Bot, List<Bot>> successfulAttacks = attacks.entrySet().stream()
                .filter(entry -> entry.getValue().size() >= Globals.NUM_REQUIRED_ATTACKERS)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        successfulAttacks.entrySet().forEach(attack ->
                attack.getValue().forEach(bot -> bot.readData(new ReadonlyBot(attack.getKey())))
        );

        for (Map.Entry<Bot, List<Bot>> attack: successfulAttacks.entrySet()){
            getDesiredReplacements(attack.getValue()).entrySet().stream()
                    .filter(entry -> entry.getKey() != blocked.get(attack.getKey()))
                    .forEach(entry -> replaceFunction(attack.getKey(), entry.getKey(), entry.getValue()));
        }
    }


    private Map<FunctionType, List<Bot>> getDesiredReplacements(List<Bot> attackers){
        Map<FunctionType, List<Bot>> replacements = new HashMap<>();
        for (Bot attacker: attackers){
            FunctionType functionToReplace = attacker.selectFunctionToReplace();
            replacements.putIfAbsent(functionToReplace, new ArrayList<>());
            replacements.get(functionToReplace).add(attacker);
        }
        return replacements;
    }

    private void replaceFunction(Bot target, FunctionType toReplace, List<Bot> candidates){
        target.replace(toReplace, candidates.get(random.nextInt(candidates.size())).getParent());
    }

    public IPAddress getRandomAddress(){
        return botToAddress.get(allBots.get(random.nextInt(allBots.size())));
    }

    public int getCurrentTurn(){
        return currentTurn;
    }

    public void initializeBot(Bot bot){
        bot.getVariables().add("ID", IDs.get(bot.getParent().getClass())+"");
        List<Bot> shuffled = new ArrayList<>(allBots);
        Collections.shuffle(shuffled);
        for (Bot connection : shuffled.subList(0, Globals.NUM_INITIAL_CONNECTIONS)){
            bot.getAddressBook().add(botToAddress.get(connection));
        }
    }
}
