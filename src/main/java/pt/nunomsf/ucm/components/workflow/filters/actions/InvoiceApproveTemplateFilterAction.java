package pt.nunomsf.ucm.components.workflow.filters.actions;

import intradoc.data.Workspace;
import intradoc.provider.Provider;
import intradoc.provider.Providers;
import intradoc.shared.SharedObjects;
import pt.nunomsf.ucm.components.workflow.audit.InvoiceApproveAudit;
import pt.nunomsf.ucm.components.workflow.constants.Constants;
import pt.nunomsf.ucm.components.workflow.exceptions.FilterActionException;
import pt.nunomsf.ucm.components.workflow.filters.model.Field;
import pt.nunomsf.ucm.components.workflow.filters.model.Fields;
import pt.nunomsf.ucm.components.workflow.model.InvoiceApproveEventRequest;
import pt.nunomsf.ucm.components.workflow.model.InvoiceApproveEventResponse;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class InvoiceApproveTemplateFilterAction implements IFilterAction {
    private final InvoiceApproveAudit audit;

    public InvoiceApproveTemplateFilterAction() {
        String auditDatabaseProviderName = SharedObjects.getEnvironmentValue("InvoiceApproveTemplateFilterAction.auditDatabaseProvider");
        Provider auditDatabaseProvider = Providers.getProvider(auditDatabaseProviderName);
        this.audit = new InvoiceApproveAudit(auditDatabaseProvider);
    }

    @Override
    public void execute(Workspace workspace, Fields data) throws FilterActionException {
        Optional<InvoiceApproveEventRequest> oEventRequest = createEventRequest(workspace, data);
        InvoiceApproveEventRequest eventRequest = oEventRequest.get();
        if (oEventRequest.isPresent()) {
            try {
                this.audit.log(eventRequest);
                InvoiceApproveEventResponse eventResponse = executeAction(eventRequest, workspace);
                this.audit.log(eventResponse);
            }
            catch(Exception e) {
                InvoiceApproveEventResponse eventResponse = InvoiceApproveEventResponse.createErrorEventResponse(eventRequest.getDocRevisionId(), e.getMessage());
                this.audit.log(eventResponse);
                FilterActionException se = e.getClass().isAssignableFrom(FilterActionException.class) ? (FilterActionException) e : new FilterActionException(e.getMessage(), e);
                throw se;
            }
        }
    }

    protected abstract InvoiceApproveEventResponse executeAction(InvoiceApproveEventRequest request, Workspace workspace) throws FilterActionException;

    private Optional<InvoiceApproveEventRequest> createEventRequest(Workspace workspace, Fields data) throws FilterActionException {

        Optional<Field> oDocRevisionId = data.getValue(Constants.Queries.IARApproveDocumentAudit.Columns.dID.name());
        Optional<Field> oFileName = data.getValue(Constants.Queries.IARApproveDocumentAudit.Columns.nomDocumento.name());
        Optional<Field> oApprover = data.getValue(Constants.Queries.IARApproveDocumentAudit.Columns.codUtilizador.name());
        Optional<Field> oNumFatura = data.getValue(Constants.Queries.IARApproveDocumentAudit.Columns.numFatura.name());
        Optional<Field> oNifCliente = data.getValue(Constants.Queries.IARApproveDocumentAudit.Columns.nifCliente.name());

        if (Stream.of(oDocRevisionId, oFileName, oApprover, oNumFatura, oNifCliente).allMatch(Optional::isPresent)) {
            return Optional.of(new InvoiceApproveEventRequest(oDocRevisionId.get().asLong(),
                    oFileName.get().asString(),
                    oApprover.get().asString(),
                    new Date(),
                    oNumFatura.get().asString(),
                    oNifCliente.get().asString()));
        }
        else {
            return Optional.empty();
        }
    }

}
