package pt.nunomsf.ucm.components.workflow.publisher.amqp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pt.nunomsf.ucm.components.workflow.exceptions.PublishException;
import pt.nunomsf.ucm.components.workflow.model.InvoiceApproveEventRequest;
import pt.nunomsf.ucm.components.workflow.publisher.IPublisher;
import pt.nunomsf.ucm.components.workflow.publisher.MessageSerializer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class RabbitMQEventExchangePublisherIntegrationTest {

    private static final String appId = "test";
    private static final Boolean exchangeDurable = Boolean.TRUE;
    private static final Integer deliveryMode = 2;
    private static final Integer priority = 1;
    private static String amqpUri;
    private static String exchangeName;
    private static String routingKey;

    @BeforeAll
    public static void init() {
        amqpUri = System.getProperty("amqpUri");
        exchangeName = System.getProperty("exchangeName");
        routingKey = System.getProperty("routingKey");
    }

    @Test
    public void publishSuccess() throws URISyntaxException, NoSuchAlgorithmException, IOException, KeyManagementException, TimeoutException, PublishException {
        AMQPExchangeType exchangeType = AMQPExchangeType.DIRECT;
        AMQPPublishMessageConfiguration messageConfiguration = new AMQPPublishMessageConfiguration(appId, exchangeName, exchangeType, exchangeDurable,
                routingKey, new HashMap<>(), deliveryMode, priority, MessageSerializer.JSON);
        IPublisher<InvoiceApproveEventRequest, AMQPPublishMessageConfiguration> publisher = new RabbitMQEventPublisher<>(amqpUri);
        publisher.publish(new InvoiceApproveEventRequest(12L, "ARDoc112.pdf", "nuno", new Date(), "FA430943/2022", "234942123"), messageConfiguration);
    }

}
