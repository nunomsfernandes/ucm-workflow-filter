package pt.nunomsf.ucm.components.workflow.filters;

import intradoc.common.ExecutionContext;
import intradoc.data.DataBinder;
import intradoc.data.DataException;
import intradoc.data.Workspace;
import intradoc.shared.FilterImplementor;
import pt.nunomsf.ucm.components.workflow.filters.actions.IFilterAction;
import pt.nunomsf.ucm.components.workflow.filters.actions.amqp.InvoiceApproveAMQPPublisherFilterAction;
import pt.nunomsf.ucm.components.workflow.filters.actions.storeprocedure.InvoiceApproveStoreProcedureFilterAction;

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
        String xIdcProfile = dataBinder.get("xIdcProfile");
        IFilterAction[] actions = resolveActions(xIdcProfile);
        Arrays.stream(actions).forEach(a -> a.execute(workspace, dataBinder));
        return FilterImplementor.CONTINUE;
    }

    private IFilterAction[] resolveActions(String xIdcProfile) {
        return Optional.ofNullable(this.filterActions.get(xIdcProfile)).orElse(new IFilterAction[0]);


    }
}
