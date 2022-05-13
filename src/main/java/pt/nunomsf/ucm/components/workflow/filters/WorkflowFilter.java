package pt.nunomsf.ucm.components.workflow.filters;

import intradoc.common.ExecutionContext;
import intradoc.data.DataBinder;
import intradoc.data.DataException;
import intradoc.data.ResultSet;
import intradoc.data.Workspace;
import intradoc.shared.FilterImplementor;
import pt.nunomsf.ucm.components.workflow.constants.Constants;
import pt.nunomsf.ucm.components.workflow.filters.actions.IFilterAction;
import pt.nunomsf.ucm.components.workflow.filters.actions.amqp.InvoiceApproveAMQPPublisherFilterAction;
import pt.nunomsf.ucm.components.workflow.filters.actions.storeprocedure.InvoiceApproveStoreProcedureFilterAction;
import pt.nunomsf.ucm.components.workflow.filters.model.FilterActionData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WorkflowFilter implements FilterImplementor {

    private final Map<String, IFilterAction[]> filterActions;

    public WorkflowFilter() throws DataException {
        this.filterActions = new HashMap<>();
        this.filterActions.put("ARDocs", new IFilterAction[]{new InvoiceApproveStoreProcedureFilterAction(), new InvoiceApproveAMQPPublisherFilterAction()});
    }

    @Override
    public int doFilter(Workspace workspace, DataBinder dataBinder, ExecutionContext executionContext) throws DataException {
        String xIdcProfile = dataBinder.get(Constants.Fields.xIdcProfile);
        FilterActionData filterActionData = buildFilterActionData(workspace, dataBinder);

        IFilterAction[] actions = resolveActions(xIdcProfile);
        Arrays.stream(actions).forEach(a -> a.execute(workspace, filterActionData));
        return FilterImplementor.CONTINUE;
    }

    private FilterActionData buildFilterActionData (Workspace workspace, DataBinder dataBinder) throws DataException {
        Long docRevisionId = Long.valueOf(dataBinder.get(Constants.Fields.dID));
        ResultSet docInfoResultSet = readDocInfo(docRevisionId, workspace);
        Map<String, ResultSet> resultSets = new HashMap<>();
        resultSets.put(Constants.ResultSets.DOCINFO, docInfoResultSet);
        FilterActionData filterActionData = new FilterActionData(dataBinder, resultSets);
        return filterActionData;
    }

    private IFilterAction[] resolveActions(String xIdcProfile) {
        return Optional.ofNullable(this.filterActions.get(xIdcProfile)).orElse(new IFilterAction[0]);
    }

    private ResultSet readDocInfo(Long docRevisionId, Workspace workspace) throws DataException {
        DataBinder dataBinder = new DataBinder();
        dataBinder.putLocal(Constants.Fields.dID, String.valueOf(docRevisionId));
        return workspace.createResultSet(Constants.ResultSets.DOCINFO, dataBinder);
    }
}
