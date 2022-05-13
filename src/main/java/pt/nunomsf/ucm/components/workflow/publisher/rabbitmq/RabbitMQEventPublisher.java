package pt.nunomsf.ucm.components.workflow.publisher.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import pt.nunomsf.ucm.components.workflow.exceptions.PublishException;
import pt.nunomsf.ucm.components.workflow.publisher.IPublisher;
import pt.nunomsf.ucm.components.workflow.exceptions.SerializationException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class RabbitMQEventPublisher<T> implements IPublisher<T, AMQPPublishMessageConfiguration> {

    private final Connection connection;

    public RabbitMQEventPublisher(String uri) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        this.connection = factory.newConnection("app:audit publish:ucm.rabbitmqeventpublisher");
    }

    public void publish(T message, AMQPPublishMessageConfiguration configuration) throws PublishException {
        try (Channel channel = this.connection.createChannel()) {
            AMQP.Exchange.DeclareOk response = channel.exchangeDeclare(configuration.getExchangeName(), configuration.getExchangeType(), configuration.getExchangeDurable());
            channel.basicPublish(configuration.getExchangeName(), configuration.getRoutingKey(),
                    new AMQP.BasicProperties.Builder()
                            .contentType(configuration.getMessageContentType())
                            .headers(configuration.getHeaders())
                            .deliveryMode(configuration.getDeliveryMode())
                            .priority(configuration.getPriority())
                            .appId(configuration.getAppId())
                            .build(),
                    configuration.getMessageSerializer().serialize(message));
        } catch (IOException | TimeoutException | SerializationException e) {
            throw new PublishException(e.getMessage(), e);
        }
    }


}
