package pt.nunomsf.ucm.components.workflow.publisher.amqp;

public enum AMQPExchangeType {
    DIRECT("direct"),
    FANOUT("fanout"),
    TOPIC("topic"),
    HEADERS("headers");

    private final String type;

    AMQPExchangeType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
