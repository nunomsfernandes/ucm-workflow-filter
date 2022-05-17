package pt.nunomsf.ucm.components.workflow.filters.actions.storeprocedure;

import intradoc.data.CallableResults;
import intradoc.data.DataBinder;
import intradoc.data.DataException;
import intradoc.data.Workspace;
import intradoc.provider.Provider;
import intradoc.provider.Providers;
import intradoc.shared.SharedObjects;
import org.apache.commons.io.IOUtils;
import pt.nunomsf.ucm.components.workflow.constants.Constants;
import pt.nunomsf.ucm.components.workflow.exceptions.FilterActionException;
import pt.nunomsf.ucm.components.workflow.filters.actions.InvoiceApproveTemplateFilterAction;
import pt.nunomsf.ucm.components.workflow.model.InvoiceApproveEventRequest;
import pt.nunomsf.ucm.components.workflow.model.InvoiceApproveEventResponse;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class InvoiceApproveStoreProcedureFilterAction extends InvoiceApproveTemplateFilterAction {
    private final String fileDestinationPath;
    private final Provider storeProcedureDatabaseProvider;

    public InvoiceApproveStoreProcedureFilterAction() {
        super();
        String storeProcedureDatabaseProviderName = SharedObjects.getEnvironmentValue("InvoiceApproveStoreProcedureFilterAction.storeProcedureDatabaseProvider");
        this.fileDestinationPath = SharedObjects.getEnvironmentValue("InvoiceApproveStoreProcedureFilterAction.fileDestinationPath");
        this.storeProcedureDatabaseProvider = Providers.getProvider(storeProcedureDatabaseProviderName);
    }

    @Override
    public InvoiceApproveEventResponse executeAction(InvoiceApproveEventRequest eventRequest, Workspace workspace) throws FilterActionException {
        try {
            byte[] documentFileBytes = readDocumentFileBytes(eventRequest.getDocRevisionId(), workspace);
            saveFile(documentFileBytes, eventRequest.getFileName(), this.fileDestinationPath);
            return sendApprovedDocumentEvent(eventRequest);
        } catch (DataException e) {
            throw new FilterActionException(e.getMessage(), e);
        }
    }

    private InvoiceApproveEventResponse sendApprovedDocumentEvent(InvoiceApproveEventRequest eventRequest) throws DataException {
        DataBinder dataBinder = new DataBinder();
        Workspace storeProcedureWorkspace = (Workspace) this.storeProcedureDatabaseProvider.getProvider();
        CallableResults results = storeProcedureWorkspace.executeCallable("SomeStoreProcedure", dataBinder);
        Long storeProcedureIntegrationID = results.getLong("executionID");
        return InvoiceApproveEventResponse.createSuccessEventResponse(eventRequest.getDocRevisionId(), storeProcedureIntegrationID);
    }

    private void saveFile(byte[] bytes, String fileName, String fileLocation) throws DataException {
        String outputFilePath = resolveChildrenPath(fileLocation, fileName);
        try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
            IOUtils.write(bytes, fos);
        } catch (FileNotFoundException e) {
            throw new DataException(String.format("Could not create file on directory:%s", fileLocation), e);
        } catch (IOException e) {
            throw new DataException(String.format("Could not write bytes on file:%s", fileLocation), e);
        }
    }

    private String resolveChildrenPath(String path, String children) {
        String dir = path.endsWith("/") ? path : path + "/";
        return dir + children;
    }

    private byte[] readDocumentFileBytes(Long docRevisionId, Workspace workspace) throws DataException {
        DataBinder dataBinder = new DataBinder();
        dataBinder.putLocal(Constants.Queries.QGetFileByDid.Columns.dID.name(), docRevisionId.toString());
        dataBinder.putLocal(Constants.Queries.QGetFileByDid.Columns.isPrimary.name(), "1");
        dataBinder.putLocal(Constants.Queries.QGetFileByDid.Columns.isWebformat.name(), "0");
        dataBinder.putLocal(Constants.Queries.QGetFileByDid.Columns.rendition.name(), "primaryFile");
        CallableResults results = workspace.executeCallable(Constants.Queries.QGetFileByDid.name(), dataBinder);
        try (InputStream stream = results.getBinaryInputStream(Constants.Queries.QGetFileByDid.Columns.file.name())) {
            return IOUtils.toByteArray(stream);
        } catch (IOException e) {
            throw new DataException(String.format("Could not read bytes of file with dID:%s, isPrimary:1, isWebFormat:1, rendition:primaryFile does not exist in the database", docRevisionId), e);
        }
    }

}
