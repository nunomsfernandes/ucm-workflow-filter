package pt.nunomsf.ucm.components.workflow.filters;

import intradoc.common.ExecutionContext;
import intradoc.data.DataBinder;
import intradoc.data.DataException;
import intradoc.data.ResultSet;
import intradoc.data.Workspace;
import intradoc.shared.FilterImplementor;
import pt.nunomsf.ucm.components.workflow.config.Configuration;
import pt.nunomsf.ucm.components.workflow.constants.Constants;
import pt.nunomsf.ucm.components.workflow.filters.actions.IFilterAction;
import pt.nunomsf.ucm.components.workflow.filters.actions.resolver.ConfigurationFilterActionsResolver;
import pt.nunomsf.ucm.components.workflow.filters.actions.resolver.IFilterActionsResolver;
import pt.nunomsf.ucm.components.workflow.filters.model.Fields;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkflowFilter implements FilterImplementor {

    private final IFilterActionsResolver filterActionsResolver;

    public WorkflowFilter() throws IOException {
        String confPath = resolveConfigurationPath();
        Configuration configuration = new Configuration(new FileInputStream(confPath));
        this.filterActionsResolver = new ConfigurationFilterActionsResolver(configuration.getConfigurationRules());
    }

    @Override
    public int doFilter(Workspace workspace, DataBinder dataBinder, ExecutionContext executionContext) throws DataException {
        Fields fields = buildContextFields(workspace, dataBinder);
        List<IFilterAction> actions = this.filterActionsResolver.resolveFilterActions(fields);
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