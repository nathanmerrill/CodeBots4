package codebots.gameobjects;

import java.util.List;

public final class ReadonlyVariables {

    private final Variables variables;

    public ReadonlyVariables(Variables variables){
        this.variables =variables;
    }

    public List<String> getAll(){
        return variables.getAll();
    }

    public String get(String name){
        return variables.get(name);
    }

}
