package pt.nunomsf.ucm.components.workflow.audit;

import intradoc.data.DataBinder;
import intradoc.data.DataException;
import intradoc.data.Workspace;
import intradoc.provider.Provider;
import intradoc.provider.Providers;
import intradoc.shared.SharedObjects;
import pt.nunomsf.ucm.components.workflow.constants.Constants;
import pt.nunomsf.ucm.components.workflow.exceptions.AuditException;
import pt.nunomsf.ucm.components.workflow.model.InvoiceApproveEventRequest;
import pt.nunomsf.ucm.components.workflow.model.InvoiceApproveEventResponse;
import pt.nunomsf.ucm.components.workflow.model.InvoiceApproveEventStatus;

import java.util.Date;

public class DatabaseInvoiceApproveAudit implements IInvoiceApproveAudit {

    private final Provider auditDatabaseProvider;

    public DatabaseInvoiceApproveAudit() {
        String auditDatabaseProviderName = SharedObjects.getEnvironmentValue("DatabaseInvoiceApproveAudit.auditDatabaseProvider");
        Provider auditDatabaseProvider = Providers.getProvider(auditDatabaseProviderName);
        this.auditDatabaseProvider = auditDatabaseProvider;
    }


    @Override
    public void log(InvoiceApproveEventRequest eventRequest) throws AuditException {
        try {
            DataBinder dataBinder = new DataBinder();
            dataBinder.putLocal(Constants.Queries.IARApproveDocumentAudit.Columns.dID.name(), String.valueOf(eventRequest.getDocRevisionId()));
            dataBinder.putLocal(Constants.Queries.IARApproveDocumentAudit.Columns.numFatura.name(), eventRequest.getNumFatura());
            dataBinder.putLocal(Constants.Queries.IARApproveDocumentAudit.Columns.nifCliente.name(), eventRequest.getNifCliente());
            dataBinder.putLocal(Constants.Queries.IARApproveDocumentAudit.Columns.nomDocumento.name(), eventRequest.getFileName());
            dataBinder.putLocal(Constants.Queries.IARApproveDocumentAudit.Columns.codUtilizador.name(), eventRequest.getApprover());
            dataBinder.putLocalDate(Constants.Queries.IARApproveDocumentAudit.Columns.datCriacao.name(), eventRequest.getApprovedDate());
            dataBinder.putLocal(Constants.Queries.IARApproveDocumentAudit.Columns.codStatus.name(), String.valueOf(InvoiceApproveEventStatus.CREATED.status));
            Workspace workspace = (Workspace) this.auditDatabaseProvider.getProvider();
            workspace.execute(Constants.Queries.IARApproveDocumentAudit.name(), dataBinder);
        } catch (DataException e) {
            throw new AuditException(e);
        }
    }

    @Override
    public void log(InvoiceApproveEventResponse eventResponse) throws AuditException {
        try {
            DataBinder dataBinder = new DataBinder();
            dataBinder.putLocal(Constants.Queries.UARApproveDocumentAudit.Columns.dID.name(), String.valueOf(eventResponse.getDocRevisionId()));
            dataBinder.putLocal(Constants.Queries.UARApproveDocumentAudit.Columns.codStatus.name(), String.valueOf(eventResponse.getCodStatus().status));
            dataBinder.putLocal(Constants.Queries.UARApproveDocumentAudit.Columns.codResposta.name(), String.valueOf(eventResponse.getIntegrationId()));
            dataBinder.putLocal(Constants.Queries.UARApproveDocumentAudit.Columns.txtMessage.name(), eventResponse.getMessage());
            dataBinder.putLocalDate(Constants.Queries.UARApproveDocumentAudit.Columns.datAtualizacao.name(), new Date());
            Workspace workspace = (Workspace) this.auditDatabaseProvider.getProvider();
            workspace.execute(Constants.Queries.UARApproveDocumentAudit.name(), dataBinder);
        } catch (DataException e) {
            throw new AuditException(e);
        }
    }
}
