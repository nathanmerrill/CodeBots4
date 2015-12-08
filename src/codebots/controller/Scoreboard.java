package codebots.controller;

import java.util.*;
import java.util.stream.Collectors;

public class Scoreboard {
    private final HashMap<String, List<Integer>> scoreHistory;
    private final HashMap<String, Integer> totalScores;
    public Scoreboard(){
        scoreHistory = new HashMap<>();
        totalScores = new HashMap<>();
    }

    public void addScores(Map<String, Integer> scores){
        scores.entrySet().forEach(entry -> addScore(entry.getKey(), entry.getValue()));
    }

    public void addScore(String identifier, Integer score){
        scoreHistory.putIfAbsent(identifier, new ArrayList<>());
        scoreHistory.get(identifier).add(score);
        totalScores.compute(identifier, (a, b) -> b == null? score:b+score);
    }

    public List<String> rankPlayers(){
        return totalScores.entrySet().stream()
                .sorted((s1, s2) -> s2.getValue().compareTo(s1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public int getTotalScore(String identifier){
        return totalScores.get(identifier);
    }

    private IntSummaryStatistics getSummaryFor(String identifier){
        return scoreHistory.get(identifier).stream().collect(Collectors.summarizingInt(i -> i));
    }

    public double getAverageScore(String identifier){
        return getSummaryFor(identifier).getAverage();
    }

    public int getMinScore(String identifier){
        return getSummaryFor(identifier).getMin();
    }

    public int getMaxScore(String identifier){
        return getSummaryFor(identifier).getMax();
    }

    public int getFrequencyOfScore(String identifier, int score){
        return Collections.frequency(scoreHistory.get(identifier), score);
    }



}
