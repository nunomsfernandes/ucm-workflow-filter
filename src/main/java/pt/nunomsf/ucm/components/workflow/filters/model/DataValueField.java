package pt.nunomsf.ucm.components.workflow.filters.model;

import java.util.Objects;

public class DataValueField {
    private final String value;

    private DataValueField(String resultSet, String value) {
        this.value = value;
    }

    public static DataValueField of (String resultSet, String value) {
        Objects.requireNonNull(value);
        return new DataValueField(resultSet, value);
    }

    public Long asLong() {
        return Long.valueOf(this.value);
    }

    public String asString() {
        return this.value;
    }

}
