package pt.nunomsf.ucm.components.workflow.audit;

import intradoc.data.DataBinder;
import intradoc.data.DataException;
import intradoc.data.Workspace;
import intradoc.provider.Provider;
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
            dataBinder.putLocal("dID", String.valueOf(eventRequest.getDocRevisionId()));
            dataBinder.putLocal("numFatura", eventRequest.getNumFatura());
            dataBinder.putLocal("nifCliente", eventRequest.getNifCliente());
            dataBinder.putLocal("nomDocumento", eventRequest.getFileName());
            dataBinder.putLocal("codUtilizador", eventRequest.getApprover());
            dataBinder.putLocalDate("datCriacao", eventRequest.getApprovedDate());
            dataBinder.putLocal("codStatus", String.valueOf(InvoiceApproveEventStatus.CREATED.status));
            Workspace workspace = (Workspace) this.auditDatabaseProvider.getProvider();
            workspace.execute("IARApproveDocumentAudit", dataBinder);
        } catch (DataException e) {
            throw new AuditException(e);
        }
    }

    public void log(InvoiceApproveEventResponse eventResponse) throws AuditException {
        try {
            DataBinder dataBinder = new DataBinder();
            dataBinder.putLocal("dID", String.valueOf(eventResponse.getDocRevisionId()));
            dataBinder.putLocal("codStatus", String.valueOf(eventResponse.getCodStatus().status));
            dataBinder.putLocal("codResposta", String.valueOf(eventResponse.getIntegrationId()));
            dataBinder.putLocal("txtMessage", eventResponse.getMessage());
            dataBinder.putLocalDate("datAtualizacao", new Date());
            Workspace workspace = (Workspace) this.auditDatabaseProvider.getProvider();
            workspace.execute("UARApproveDocumentAudit", dataBinder);
        } catch (DataException e) {
            throw new AuditException(e);
        }
    }
}
