package pt.nunomsf.ucm.components.workflow.filters.actions.impl.amqp;

import intradoc.data.DataException;
import intradoc.data.Workspace;
import intradoc.shared.SharedObjects;
import pt.nunomsf.ucm.components.workflow.exceptions.FilterActionException;
import pt.nunomsf.ucm.components.workflow.exceptions.PublishException;
import pt.nunomsf.ucm.components.workflow.filters.actions.impl.InvoiceApproveTemplateFilterAction;
import pt.nunomsf.ucm.components.workflow.model.InvoiceApproveEventRequest;
import pt.nunomsf.ucm.components.workflow.model.InvoiceApproveEventResponse;
import pt.nunomsf.ucm.components.workflow.publisher.IPublisher;
import pt.nunomsf.ucm.components.workflow.publisher.MessageSerializer;
import pt.nunomsf.ucm.components.workflow.publisher.amqp.AMQPExchangeType;
import pt.nunomsf.ucm.components.workflow.publisher.amqp.AMQPPublishMessageConfiguration;
import pt.nunomsf.ucm.components.workflow.publisher.amqp.RabbitMQEventPublisher;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class InvoiceApproveAMQPPublisherFilterAction extends InvoiceApproveTemplateFilterAction {

    private final IPublisher publisher;
    private final AMQPPublishMessageConfiguration messageConfiguration;


    public InvoiceApproveAMQPPublisherFilterAction() throws DataException {
        super();
        String uri = SharedObjects.getEnvironmentValue("InvoiceApproveAMQPPublisherFilterAction.uri");
        String appId = SharedObjects.getEnvironmentValue("InvoiceApproveAMQPPublisherFilterAction.appId");
        String exchangeName = SharedObjects.getEnvironmentValue("InvoiceApproveAMQPPublisherFilterAction.exchangeName");
        String exchangeType = SharedObjects.getEnvironmentValue("InvoiceApproveAMQPPublisherFilterAction.exchangeType");
        Boolean exchangeDurable = Boolean.valueOf(SharedObjects.getEnvironmentValue("InvoiceApproveAMQPPublisherFilterAction.exchangeDurable"));
        String routingKey = SharedObjects.getEnvironmentValue("InvoiceApproveAMQPPublisherFilterAction.routingKey");
        Integer deliveryMode = Integer.valueOf(SharedObjects.getEnvironmentValue("InvoiceApproveAMQPPublisherFilterAction.deliveryMode"));
        Integer priority = Integer.valueOf(SharedObjects.getEnvironmentValue("InvoiceApproveAMQPPublisherFilterAction.priority"));

        try {
            this.messageConfiguration = new AMQPPublishMessageConfiguration(appId, exchangeName, AMQPExchangeType.valueOf(exchangeType), exchangeDurable, routingKey, null, deliveryMode, priority, MessageSerializer.JSON);
            this.publisher = new RabbitMQEventPublisher<>(uri);

        } catch (URISyntaxException | NoSuchAlgorithmException | IOException | KeyManagementException |
                 TimeoutException e) {
            throw new DataException(e.getMessage(), e);
        }
    }

    public InvoiceApproveAMQPPublisherFilterAction(String uri, AMQPPublishMessageConfiguration messageConfiguration) throws DataException {
        super();
        try {
            this.messageConfiguration = messageConfiguration;
            this.publisher = new RabbitMQEventPublisher<>(uri);
        } catch (URISyntaxException | NoSuchAlgorithmException | IOException | KeyManagementException |
                 TimeoutException e) {
            throw new DataException(e.getMessage(), e);
        }
    }

    @Override
    protected InvoiceApproveEventResponse executeAction(InvoiceApproveEventRequest eventRequest, Workspace workspace) throws FilterActionException {
        try {
            this.publisher.publish(eventRequest, this.messageConfiguration);
            return InvoiceApproveEventResponse.createSuccessEventResponse(eventRequest.getDocRevisionId(), 0L);
        } catch (PublishException e) {
            throw new FilterActionException(e);
        }
    }

}
