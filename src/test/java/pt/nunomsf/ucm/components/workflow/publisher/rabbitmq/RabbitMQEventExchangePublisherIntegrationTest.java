package pt.nunomsf.ucm.components.workflow.publisher.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pt.nunomsf.ucm.components.workflow.consumer.IConsumer;
import pt.nunomsf.ucm.components.workflow.consumer.rabbitmq.AMQPConsumeMessageConfiguration;
import pt.nunomsf.ucm.components.workflow.consumer.rabbitmq.RabbitMQEventConsumer;
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

    private static String appId = "test";
    private static String amqpUri;
    private static String exchangeName;
    private static String routingKey;
    private static Boolean exchangeDurable = Boolean.TRUE;
    private static Integer deliveryMode = 2;
    private static Integer priority = 1;

    @BeforeAll
    public static void init() {
        amqpUri = System.getProperty("amqpUri");
        exchangeName = System.getProperty("exchangeName");
        routingKey = System.getProperty("routingKey");
    }


    @Test
    public void publishSuccess() throws URISyntaxException, NoSuchAlgorithmException, IOException, KeyManagementException, TimeoutException, PublishException {
        BuiltinExchangeType exchangeType = BuiltinExchangeType.DIRECT;
        AMQPPublishMessageConfiguration messageConfiguration = new AMQPPublishMessageConfiguration(appId, exchangeName, exchangeType, exchangeDurable,
                    routingKey, new HashMap<>(), deliveryMode, priority, MessageSerializer.JSON);
        IPublisher<InvoiceApproveEventRequest, AMQPPublishMessageConfiguration> publisher = new RabbitMQEventPublisher<>(this.amqpUri);
        publisher.publish(new InvoiceApproveEventRequest(12L, "ARDoc112.pdf", "nuno", new Date(), "FA430943/2022", "234942123"), messageConfiguration);
    }

}
