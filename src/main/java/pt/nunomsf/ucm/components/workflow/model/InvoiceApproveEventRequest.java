package pt.nunomsf.ucm.components.workflow.model;

import java.util.Date;

public class InvoiceApproveEventRequest {
    private final Long docRevisionId;
    private final String fileName;
    private final String approver;
    private final Date approvedDate;
    private final String numFatura;
    private final String nifCliente;

    public InvoiceApproveEventRequest(Long docRevisionId, String fileName, String approver, Date approvedDate, String numFatura, String nifCliente) {
        this.docRevisionId = docRevisionId;
        this.fileName = fileName;
        this.approver = approver;
        this.approvedDate = approvedDate;
        this.numFatura = numFatura;
        this.nifCliente = nifCliente;
    }

    public Long getDocRevisionId() {
        return docRevisionId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getApprover() {
        return approver;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public String getNumFatura() {
        return numFatura;
    }

    public String getNifCliente() {
        return nifCliente;
    }
}
