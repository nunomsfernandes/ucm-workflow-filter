package pt.nunomsf.ucm.components.workflow.filters.actions;

import intradoc.data.DataBinder;
import intradoc.data.DataException;
import intradoc.data.ResultSet;
import intradoc.data.Workspace;
import intradoc.provider.Provider;
import intradoc.provider.Providers;
import intradoc.shared.SharedObjects;
import pt.nunomsf.ucm.components.workflow.audit.InvoiceApproveAudit;
import pt.nunomsf.ucm.components.workflow.exceptions.FilterActionException;
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
    public void execute(Workspace workspace, DataBinder dataBinder) throws FilterActionException {
        InvoiceApproveEventRequest eventRequest = createEventRequest(workspace, dataBinder);
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

    private InvoiceApproveEventRequest createEventRequest(Workspace workspace, DataBinder dataBinder) throws FilterActionException {
        try {
            Long docRevisionId = Long.valueOf(dataBinder.get("dID"));
            String fileName = dataBinder.getLocal("dOriginalName");
            String approver = dataBinder.getLocal("dUser");
            ResultSet docInfo = readDocInfo(workspace, docRevisionId);
            String numFatura = docInfo.getStringValueByName("xNumDocumento");
            String nifCliente = docInfo.getStringValueByName("xCodNifCliente");
            return new InvoiceApproveEventRequest(docRevisionId, fileName, approver, new Date(), numFatura, nifCliente);
        }catch (DataException e) {
            throw new FilterActionException(e.getMessage(), e);
        }
    }
    private ResultSet readDocInfo(Workspace workspace, Long docRevisionId) throws DataException {
        DataBinder dataBinder = new DataBinder();
        dataBinder.putLocal("dID", String.valueOf(docRevisionId));
        ResultSet resultSet = workspace.createResultSet("QdocInfo", dataBinder);
        return resultSet;
    }

}
