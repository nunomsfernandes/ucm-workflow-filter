package pt.nunomsf.ucm.components.workflow.filters.actions.impl;

import intradoc.data.Workspace;
import pt.nunomsf.ucm.components.workflow.audit.DatabaseInvoiceApproveAudit;
import pt.nunomsf.ucm.components.workflow.audit.IInvoiceApproveAudit;
import pt.nunomsf.ucm.components.workflow.constants.Constants;
import pt.nunomsf.ucm.components.workflow.exceptions.FilterActionException;
import pt.nunomsf.ucm.components.workflow.filters.actions.IFilterAction;
import pt.nunomsf.ucm.components.workflow.filters.model.DataValueField;
import pt.nunomsf.ucm.components.workflow.filters.model.DataFields;
import pt.nunomsf.ucm.components.workflow.model.InvoiceApproveEventRequest;
import pt.nunomsf.ucm.components.workflow.model.InvoiceApproveEventResponse;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class InvoiceApproveTemplateFilterAction implements IFilterAction {
    private final IInvoiceApproveAudit audit;

    public InvoiceApproveTemplateFilterAction() {
        this.audit = new DatabaseInvoiceApproveAudit();
    }

    @Override
    public void execute(Workspace workspace, DataFields data) throws FilterActionException {
        Optional<InvoiceApproveEventRequest> oEventRequest = createEventRequest(workspace, data);

        if (oEventRequest.isPresent()) {
            InvoiceApproveEventRequest eventRequest = oEventRequest.get();
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

    private Optional<InvoiceApproveEventRequest> createEventRequest(Workspace workspace, DataFields data) throws FilterActionException {

        Optional<DataValueField> oDocRevisionId = data.getValue(Constants.Queries.IARApproveDocumentAudit.Columns.dID.name());
        Optional<DataValueField> oFileName = data.getValue(Constants.Queries.IARApproveDocumentAudit.Columns.nomDocumento.name());
        Optional<DataValueField> oApprover = data.getValue(Constants.Queries.IARApproveDocumentAudit.Columns.codUtilizador.name());
        Optional<DataValueField> oNumFatura = data.getValue(Constants.Queries.IARApproveDocumentAudit.Columns.numFatura.name());
        Optional<DataValueField> oNifCliente = data.getValue(Constants.Queries.IARApproveDocumentAudit.Columns.nifCliente.name());

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
