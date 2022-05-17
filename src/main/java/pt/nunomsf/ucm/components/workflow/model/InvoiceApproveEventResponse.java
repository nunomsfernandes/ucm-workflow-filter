package pt.nunomsf.ucm.components.workflow.model;

public class InvoiceApproveEventResponse {
    private final Long docRevisionId;
    private final InvoiceApproveEventStatus codStatus;
    private final Long integrationId;
    private final String message;

    public InvoiceApproveEventResponse(Long docRevisionId, InvoiceApproveEventStatus codStatus, Long integrationId, String message) {
        this.docRevisionId = docRevisionId;
        this.codStatus = codStatus;
        this.integrationId = integrationId;
        this.message = message;
    }

    public static InvoiceApproveEventResponse createSuccessEventResponse(Long docRevisionId, Long spIntegrationID) {
        return new InvoiceApproveEventResponse(docRevisionId, InvoiceApproveEventStatus.SUCCESS, spIntegrationID, "success");
    }

    public static InvoiceApproveEventResponse createErrorEventResponse(Long docRevisionId, String message) {
        return new InvoiceApproveEventResponse(docRevisionId, InvoiceApproveEventStatus.ERROR, Long.valueOf(-1), message);
    }

    public Long getDocRevisionId() {
        return docRevisionId;
    }

    public InvoiceApproveEventStatus getCodStatus() {
        return codStatus;
    }

    public Long getIntegrationId() {
        return integrationId;
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return InvoiceApproveEventStatus.ERROR.equals(this.codStatus);
    }
}
