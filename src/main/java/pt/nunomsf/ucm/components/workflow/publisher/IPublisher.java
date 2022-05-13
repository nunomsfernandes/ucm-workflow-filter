package pt.nunomsf.ucm.components.workflow.publisher;

import pt.nunomsf.ucm.components.workflow.exceptions.PublishException;

public interface IPublisher<T, C> {

    void publish(T message, C configuration) throws PublishException;

}
