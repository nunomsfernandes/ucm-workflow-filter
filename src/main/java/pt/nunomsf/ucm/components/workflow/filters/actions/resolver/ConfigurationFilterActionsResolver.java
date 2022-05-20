package pt.nunomsf.ucm.components.workflow.filters.actions.resolver;

import pt.nunomsf.ucm.components.workflow.config.ConfigurationRules;
import pt.nunomsf.ucm.components.workflow.config.Rule;
import pt.nunomsf.ucm.components.workflow.exceptions.ConfigurationException;
import pt.nunomsf.ucm.components.workflow.filters.actions.IFilterAction;
import pt.nunomsf.ucm.components.workflow.filters.model.Field;
import pt.nunomsf.ucm.components.workflow.filters.model.Fields;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConfigurationFilterActionsResolver implements  IFilterActionsResolver {

    private final ConfigurationRules configurationRules;

    public ConfigurationFilterActionsResolver(ConfigurationRules configurationRules) {
        this.configurationRules = configurationRules;
    }

    @Override
    public List<IFilterAction> resolveFilterActions(Fields contextFields) {
        List<Rule> rules = this.configurationRules.getRules();
        List<String> actions = rules.stream().filter(rule -> passConditions(rule, contextFields))
                .map(Rule::getActions).flatMap(Collection::stream).collect(Collectors.toList());
        return actions.stream().map(this::createClassFilterActionInstance).collect(Collectors.toList());
    }

    private IFilterAction createClassFilterActionInstance(String className) {
        try {
            return (IFilterAction) Class.forName(className).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new ConfigurationException(e);
        }
    }



    private Class createClassInstance(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ConfigurationException(e);
        }
    }

    private boolean passConditions(Rule rule, Fields contextFields) {
        Map<String, String> conditions = rule.getConditions();
        boolean passCondition = true;
        for (Map.Entry condition : conditions.entrySet()) {
            if (!passCondition(condition, contextFields)) {
                passCondition = false;
                break;
            }
        }
        return passCondition;
    }

    private boolean passCondition(Map.Entry<String, String> condition, Fields contextFields) {
        Optional<Field> executionValue = contextFields.getValue(condition.getKey());
        return executionValue.map(f -> f.asString().equals(condition.getValue())).orElse(false);
    }
}
