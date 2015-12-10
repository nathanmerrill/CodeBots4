package codebots.gameobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Variables {
    private final HashMap<String, String> variables;

    public Variables(){
        variables = new HashMap<>();
    }

    public void add(String name, String value){
        if (name == null || value == null){
            return;
        }
        variables.put(name, value);
    }

    public boolean has(String name){
        return variables.containsKey(name);
    }

    public List<String> getAll(){
        return new ArrayList<>(variables.keySet());
    }

    public String get(String name){
        if (name == null){
            return null;
        }
        return variables.get(name);
    }

    public void clear(){
        variables.clear();
    }

    public void remove(String name){
        variables.remove(name);
    }
}
