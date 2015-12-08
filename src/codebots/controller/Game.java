package codebots.controller;

import java.util.Random;

public class Game {
    private final Random random;
    private final Scoreboard scoreboard;

    public Game(){
        random = new Random();
        scoreboard = new Scoreboard();
    }

    public void run(){
        for (int i = 0; i < Globals.NUM_ROUNDS; i++){
            System.out.println("Round "+i);
            Round round = new Round(CodeBotFactory.getAllTypes(), this.random);
            round.run();
            scoreboard.addScores(round.getScores());
        }
    }

    public void printScores(){
        for (String identifier: scoreboard.rankPlayers()){
            System.out.println(scoreboard.getTotalScore(identifier)+"\t"+identifier);
        }
    }


}
