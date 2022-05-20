package pt.nunomsf.ucm.components.workflow.audit;

import pt.nunomsf.ucm.components.workflow.exceptions.AuditException;
import pt.nunomsf.ucm.components.workflow.model.InvoiceApproveEventRequest;
import pt.nunomsf.ucm.components.workflow.model.InvoiceApproveEventResponse;

public interface IInvoiceApproveAudit {
    void log(InvoiceApproveEventRequest eventRequest) throws AuditException;

    void log(InvoiceApproveEventResponse eventResponse) throws AuditException;
}
