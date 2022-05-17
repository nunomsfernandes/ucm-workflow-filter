package pt.nunomsf.ucm.components.workflow.model;

public enum InvoiceApproveEventStatus {
    CREATED(0), SUCCESS(1), ERROR(2);
    public final int status;

    InvoiceApproveEventStatus(int status) {
        this.status = status;
    }
}
