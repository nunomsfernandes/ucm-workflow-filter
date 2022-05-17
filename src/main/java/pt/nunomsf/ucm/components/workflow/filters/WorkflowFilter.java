package pt.nunomsf.ucm.components.workflow.filters;

import intradoc.common.ExecutionContext;
import intradoc.data.DataBinder;
import intradoc.data.DataException;
import intradoc.data.ResultSet;
import intradoc.data.Workspace;
import intradoc.shared.FilterImplementor;
import pt.nunomsf.ucm.components.workflow.config.Configuration;
import pt.nunomsf.ucm.components.workflow.config.Rule;
import pt.nunomsf.ucm.components.workflow.constants.Constants;
import pt.nunomsf.ucm.components.workflow.exceptions.ConfigurationException;
import pt.nunomsf.ucm.components.workflow.filters.actions.IFilterAction;
import pt.nunomsf.ucm.components.workflow.filters.model.Field;
import pt.nunomsf.ucm.components.workflow.filters.model.Fields;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class WorkflowFilter implements FilterImplementor {

    private final Configuration configuration;

    public WorkflowFilter() throws IOException {
        String confPath = resolveConfigurationPath();
        this.configuration = new Configuration(confPath);
    }

    @Override
    public int doFilter(Workspace workspace, DataBinder dataBinder, ExecutionContext executionContext) throws DataException {
        Fields fields = buildContextFields(workspace, dataBinder);
        List<IFilterAction> actions = resolveContextActions(fields);
        actions.forEach(a -> a.execute(workspace, fields));
        return FilterImplementor.CONTINUE;
    }

    private Fields buildContextFields(Workspace workspace, DataBinder dataBinder) throws DataException {
        Long docRevisionId = Long.valueOf(dataBinder.get(Constants.Tables.Revisions.dID.name()));
        ResultSet docInfoResultSet = readDocInfo(docRevisionId, workspace);
        Map<String, ResultSet> resultSets = new HashMap<>();
        resultSets.put(Constants.Queries.QdocInfo.name(), docInfoResultSet);
        return new Fields(dataBinder, resultSets);
    }

    private List<IFilterAction> resolveContextActions(Fields contextFields) {
        List<Rule> rules = this.configuration.getRules().getRules();
        List<String> actions = rules.stream().filter(rule -> passConditions(rule, contextFields))
                .map(Rule::getActions).flatMap(Collection::stream).collect(Collectors.toList());
        return actions.stream().map(this::createClassInstance).collect(Collectors.toList());
    }

    private IFilterAction createClassInstance(String className) {
        try {
            return (IFilterAction) Class.forName(className).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
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

    private ResultSet readDocInfo(Long docRevisionId, Workspace workspace) throws DataException {
        DataBinder dataBinder = new DataBinder();
        dataBinder.putLocal(Constants.Queries.QdocInfo.Columns.dID.name(), String.valueOf(docRevisionId));
        return workspace.createResultSet(Constants.Queries.QdocInfo.name(), dataBinder);
    }

    private String resolveConfigurationPath() throws IOException {
        String cnp = new java.io.File(".").getCanonicalPath();
        return new java.io.File(cnp + "/lib/").getAbsolutePath() + "/filter-actions.yaml";
    }
}