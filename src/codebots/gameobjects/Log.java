package codebots.gameobjects;


import codebots.controller.Round;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Log {
    private final HashMap<Integer, List<LogEntry>> logs;
    private List<LogEntry> currentLog;
    private List<LogEntry> lastLog;
    private int currentTurn;
    private final Round round;
    public Log(Round round){
        logs = new HashMap<>();
        this.round = round;
        currentTurn = this.round.getCurrentTurn();
        currentLog = new ArrayList<>();
        lastLog = new ArrayList<>();
    }

    public void addLog(LogEntry entry){
        if (round.getCurrentTurn() != currentTurn){
            currentTurn++;
            lastLog = currentLog;
            currentLog = new ArrayList<>();
            logs.put(currentTurn, currentLog);
        }
        currentLog.add(entry);
    }

    public void clear(){
        logs.clear();
        currentLog.clear();
        lastLog.clear();
    }

    public void clearTurn(int turnNumber){
        logs.getOrDefault(turnNumber, new ArrayList<>()).clear();
    }

    public void clearMessage(int turnNumber, LogEntry message){
        logs.getOrDefault(turnNumber, new ArrayList<>()).remove(message);
    }

    public List<LogEntry> getCurrentTurnLogs(){
        return new ArrayList<>(currentLog);
    }

    public List<LogEntry> getLastTurnLogs(){
        return new ArrayList<>(lastLog);
    }

    public List<LogEntry> getAllLogs(){
        List<LogEntry> comprehension = new ArrayList<>();
        logs.values().forEach(comprehension::addAll);
        return comprehension;
    }

    public List<LogEntry> getLogsOnTurn(int turnNumber){
        return new ArrayList<>(logs.getOrDefault(turnNumber, new ArrayList<>()));
    }

    public static class LogEntry {
        public final FunctionType type;

        public IPAddress address;
        public Message message;
        public FunctionType targetFunction;
        public LogEntry(FunctionType type){
            this.type = type;
        }
        public LogEntry message(Message message){
            this.message = message;
            return this;
        }
        public LogEntry address(IPAddress address){
            this.address = address;
            return this;
        }
        public LogEntry targetFunction(FunctionType type){
            this.targetFunction = type;
            return this;
        }
    }



}