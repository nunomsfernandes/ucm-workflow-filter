package pt.nunomsf.ucm.components.workflow.filters.actions;

import intradoc.data.Workspace;
import intradoc.provider.Provider;
import intradoc.provider.Providers;
import intradoc.shared.SharedObjects;
import pt.nunomsf.ucm.components.workflow.audit.InvoiceApproveAudit;
import pt.nunomsf.ucm.components.workflow.constants.Constants;
import pt.nunomsf.ucm.components.workflow.exceptions.FilterActionException;
import pt.nunomsf.ucm.components.workflow.filters.model.FilterActionData;
import pt.nunomsf.ucm.components.workflow.model.InvoiceApproveEventRequest;
import pt.nunomsf.ucm.components.workflow.model.InvoiceApproveEventResponse;

import java.util.Date;

public abstract class InvoiceApproveTemplateFilterAction implements IFilterAction {
    private final InvoiceApproveAudit audit;

    public InvoiceApproveTemplateFilterAction() {
        String auditDatabaseProviderName = SharedObjects.getEnvironmentValue("InvoiceApproveTemplateFilterAction.auditDatabaseProvider");
        Provider auditDatabaseProvider = Providers.getProvider(auditDatabaseProviderName);
        this.audit = new InvoiceApproveAudit(auditDatabaseProvider);
    }

    @Override
    public void execute(Workspace workspace, FilterActionData data) throws FilterActionException {
        InvoiceApproveEventRequest eventRequest = createEventRequest(workspace, data);
        try {
            this.audit.log(eventRequest);
            InvoiceApproveEventResponse eventResponse = executeAction(eventRequest, workspace);
            this.audit.log(eventResponse);
        }
        catch (Exception e) {
            InvoiceApproveEventResponse eventResponse = InvoiceApproveEventResponse.createErrorEventResponse(eventRequest.getDocRevisionId(), e.getMessage());
            this.audit.log(eventResponse);
            FilterActionException se = e.getClass().isAssignableFrom(FilterActionException.class) ? (FilterActionException) e : new FilterActionException(e.getMessage(), e);
            throw se;
        }
    }

    protected abstract InvoiceApproveEventResponse executeAction(InvoiceApproveEventRequest request, Workspace workspace) throws FilterActionException;

    private InvoiceApproveEventRequest createEventRequest(Workspace workspace, FilterActionData data) throws FilterActionException {
        Long docRevisionId = data.getLocalValue(Constants.Fields.dID).asLong();
        String fileName = data.getLocalValue(Constants.Fields.dOriginalName).asString();
        String approver = data.getLocalValue(Constants.Fields.dUser).asString();
        String numFatura = data.getCollectionValue(Constants.ResultSets.DOCINFO, 0, Constants.Fields.xNumDocumento).asString();
        String nifCliente = data.getCollectionValue(Constants.ResultSets.DOCINFO, 0, Constants.Fields.xCodNifCliente).asString();
        return new InvoiceApproveEventRequest(docRevisionId, fileName, approver, new Date(), numFatura, nifCliente);
    }

}
