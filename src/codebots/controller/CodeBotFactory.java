package codebots.controller;

import codebots.bot.CodeBot;
import codebots.bots.DefaultCodeBot;
import codebots.bots.RandomCodeBot;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CodeBotFactory {

    public final static Map<Class<? extends CodeBot>, Supplier<? extends CodeBot>> playerCreator = new HashMap<>();

    static {
        playerCreator.put(RandomCodeBot.class, RandomCodeBot::new);
        playerCreator.put(DefaultCodeBot.class, DefaultCodeBot::new);
    }

    public static Set<Class<? extends CodeBot>> getAllTypes() {
        return playerCreator.keySet();
    }

    public static List<CodeBot> create(Collection<Class<? extends CodeBot>> toUse) {
        return toUse
                .stream()
                .map(playerCreator::get)
                .map(Supplier::get)
                .collect(Collectors.toList());
    }
}
