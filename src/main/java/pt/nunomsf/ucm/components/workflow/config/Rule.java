package pt.nunomsf.ucm.components.workflow.config;

import java.util.List;
import java.util.Map;

public class Rule {
    private String name;
    private Map<String,String> conditions;

    private List<String> actions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String,String> getConditions() {
        return conditions;
    }

    public void setConditions(Map<String,String> conditions) {
        this.conditions = conditions;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }
}


