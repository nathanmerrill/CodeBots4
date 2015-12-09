package codebots.gameobjects;

import java.util.List;
import java.util.stream.Collectors;

public final class ReadonlyLog {

    private final Log log;
    public ReadonlyLog(Log log){
        this.log = log;
    }

    public List<ReadonlyLogEntry> getCurrentTurnLogs(){
        return log.getCurrentTurnLogs().stream().map(ReadonlyLogEntry::new).collect(Collectors.toList());
    }

    public List<ReadonlyLogEntry> getLastTurnLogs(){
        return log.getLastTurnLogs().stream().map(ReadonlyLogEntry::new).collect(Collectors.toList());
    }

    public List<ReadonlyLogEntry> getAllLogs(){
        return log.getAllLogs().stream().map(ReadonlyLogEntry::new).collect(Collectors.toList());
    }

    public List<ReadonlyLogEntry> getLogsOnTurn(int turnNumber){
        return log.getLogsOnTurn(turnNumber).stream().map(ReadonlyLogEntry::new).collect(Collectors.toList());
    }

    public final static class ReadonlyLogEntry{
        private final Log.LogEntry entry;
        public ReadonlyLogEntry(Log.LogEntry entry){
            this.entry = entry;
        }
        public FunctionType getType(){
            return entry.type;
        }
        public IPAddress getAddress(){
            return entry.address;
        }
        public Message getMessage(){
            return entry.message;
        }
        public FunctionType getTargetFunction(){
            return entry.targetFunction;
        }
    }
}
