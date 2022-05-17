package pt.nunomsf.ucm.components.workflow.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pt.nunomsf.ucm.components.workflow.exceptions.SerializationException;

import java.io.IOException;

public enum MessageSerializer {

    JSON {
        @Override
        public <T> byte[] serialize(T object) throws SerializationException {
            try {
                ObjectMapper mapper = new ObjectMapper();
                String serializedObject = mapper.writeValueAsString(object);
                return serializedObject.getBytes();
            } catch (JsonProcessingException e) {
                throw new SerializationException(e);
            }
        }

        @Override
        public <T> T deserialize(byte[] bytes, Class<T> clazz) throws SerializationException {
            try {
                ObjectMapper mapper = new ObjectMapper();
                T deserialized = mapper.readValue(bytes, clazz);
                return deserialized;
            } catch (IOException e) {
                throw new SerializationException(e);
            }
        }

        @Override
        public String contentType() {
            return "application/json";
        }
    };

    public abstract <T> byte[] serialize(T object) throws SerializationException;

    public abstract <T> T deserialize(byte[] object, Class<T> clazz) throws SerializationException;

    public abstract String contentType();

}
