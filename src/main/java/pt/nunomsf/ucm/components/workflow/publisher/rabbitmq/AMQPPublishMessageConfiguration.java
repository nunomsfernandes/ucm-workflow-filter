package pt.nunomsf.ucm.components.workflow.publisher.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import pt.nunomsf.ucm.components.workflow.publisher.MessageSerializer;

import java.util.Map;

public class AMQPPublishMessageConfiguration {
    private final String appId;
    private final String exchangeName;
    private final BuiltinExchangeType exchangeType;
    private final Boolean exchangeDurable;
    private final String routingKey;
    private final Map<String, Object> headers;
    private final Integer deliveryMode;
    private final Integer priority;
    private final MessageSerializer messageSerializer;

    public AMQPPublishMessageConfiguration(String appId, String exchangeName, BuiltinExchangeType exchangeType, Boolean exchangeDurable,
                                           String routingKey, Map<String, Object> headers, Integer deliveryMode, Integer priority, MessageSerializer messageSerializer) {
        this.appId = appId;
        this.exchangeName = exchangeName;
        this.exchangeType = exchangeType;
        this.exchangeDurable = exchangeDurable;
        this.routingKey = routingKey;
        this.headers = headers;
        this.deliveryMode = deliveryMode;
        this.priority = priority;
        this.messageSerializer = messageSerializer;
    }

    public String getAppId() {
        return appId;
    }
    public String getExchangeName() {
        return exchangeName;
    }
    public BuiltinExchangeType getExchangeType() {
        return exchangeType;
    }
    public Boolean getExchangeDurable() {
        return exchangeDurable;
    }
    public String getRoutingKey() {
        return routingKey;
    }
    public Map<String, Object> getHeaders() {
        return headers;
    }
    public Integer getDeliveryMode() {
        return deliveryMode;
    }
    public Integer getPriority() {
        return priority;
    }
    public MessageSerializer getMessageSerializer() {
        return this.messageSerializer;
    }
    public String getMessageContentType() {
        return this.messageSerializer.contentType();
    }
}
