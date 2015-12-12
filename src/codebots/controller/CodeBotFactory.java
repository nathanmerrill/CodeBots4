package codebots.controller;

import codebots.bot.CodeBot;
import codebots.bots.*;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CodeBotFactory {

    public final static Map<Class<? extends CodeBot>, Supplier<? extends CodeBot>> playerCreator = new HashMap<>();

    static {
        playerCreator.put(RandomCodeBot.class, RandomCodeBot::new);
        playerCreator.put(DefaultCodeBot.class, DefaultCodeBot::new);
        playerCreator.put(NullBot.class, NullBot::new);
        playerCreator.put(HelperBot.class, HelperBot::new);
        playerCreator.put(TrustBot.class, TrustBot::new);
        playerCreator.put(DisarmerBot.class, DisarmerBot::new);
        playerCreator.put(MarkedBot.class, MarkedBot::new);
        playerCreator.put(AmnesiaBot.class, AmnesiaBot::new);
        playerCreator.put(Chaos.class, Chaos::new);
        playerCreator.put(MailBot.class, MailBot::new);
        playerCreator.put(Replacer.class, Replacer::new);
        playerCreator.put(SwarmBot.class, SwarmBot::new);
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
