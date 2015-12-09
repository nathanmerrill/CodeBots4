package codebots;

import codebots.controller.Game;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
        game.printScores();
    }
}

