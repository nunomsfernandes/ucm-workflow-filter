package pt.nunomsf.ucm.components.workflow.audit;

import intradoc.data.DataBinder;
import intradoc.data.DataException;
import intradoc.data.Workspace;
import intradoc.provider.Provider;
import pt.nunomsf.ucm.components.workflow.constants.Constants;
import pt.nunomsf.ucm.components.workflow.exceptions.AuditException;
import pt.nunomsf.ucm.components.workflow.model.InvoiceApproveEventRequest;
import pt.nunomsf.ucm.components.workflow.model.InvoiceApproveEventResponse;
import pt.nunomsf.ucm.components.workflow.model.InvoiceApproveEventStatus;

import java.util.Date;

public class InvoiceApproveAudit {

    private final Provider auditDatabaseProvider;

    public InvoiceApproveAudit(Provider auditDatabaseProvider) {
        this.auditDatabaseProvider = auditDatabaseProvider;
    }

    public void log(InvoiceApproveEventRequest eventRequest) throws AuditException {
        try {
            DataBinder dataBinder = new DataBinder();
            dataBinder.putLocal(Constants.Queries.IARApproveDocumentAudit.Collumns.dID, String.valueOf(eventRequest.getDocRevisionId()));
            dataBinder.putLocal(Constants.Queries.IARApproveDocumentAudit.Collumns.numFatura, eventRequest.getNumFatura());
            dataBinder.putLocal(Constants.Queries.IARApproveDocumentAudit.Collumns.nifCliente, eventRequest.getNifCliente());
            dataBinder.putLocal(Constants.Queries.IARApproveDocumentAudit.Collumns.nomDocumento, eventRequest.getFileName());
            dataBinder.putLocal(Constants.Queries.IARApproveDocumentAudit.Collumns.codUtilizador, eventRequest.getApprover());
            dataBinder.putLocalDate(Constants.Queries.IARApproveDocumentAudit.Collumns.datCriacao, eventRequest.getApprovedDate());
            dataBinder.putLocal(Constants.Queries.IARApproveDocumentAudit.Collumns.codStatus, String.valueOf(InvoiceApproveEventStatus.CREATED.status));
            Workspace workspace = (Workspace) this.auditDatabaseProvider.getProvider();
            workspace.execute(Constants.Queries.IARApproveDocumentAudit.name, dataBinder);
        } catch (DataException e) {
            throw new AuditException(e);
        }
    }

    public void log(InvoiceApproveEventResponse eventResponse) throws AuditException {
        try {
            DataBinder dataBinder = new DataBinder();
            dataBinder.putLocal(Constants.Queries.UARApproveDocumentAudit.Collumns.dID, String.valueOf(eventResponse.getDocRevisionId()));
            dataBinder.putLocal(Constants.Queries.UARApproveDocumentAudit.Collumns.codStatus, String.valueOf(eventResponse.getCodStatus().status));
            dataBinder.putLocal(Constants.Queries.UARApproveDocumentAudit.Collumns.codResposta, String.valueOf(eventResponse.getIntegrationId()));
            dataBinder.putLocal(Constants.Queries.UARApproveDocumentAudit.Collumns.txtMessage, eventResponse.getMessage());
            dataBinder.putLocalDate(Constants.Queries.UARApproveDocumentAudit.Collumns.datAtualizacao, new Date());
            Workspace workspace = (Workspace) this.auditDatabaseProvider.getProvider();
            workspace.execute(Constants.Queries.UARApproveDocumentAudit.name, dataBinder);
        } catch (DataException e) {
            throw new AuditException(e);
        }
    }
}
