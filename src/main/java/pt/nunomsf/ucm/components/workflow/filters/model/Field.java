package pt.nunomsf.ucm.components.workflow.filters.model;

public class Field {
    private final String resultSet;
    private final String value;

    private Field(String resultSet, String value) {
        this.resultSet = resultSet;
        this.value = value;
    }

    public static Field of (String resultSet, String value) {
        return new Field(resultSet, value);
    }

    public Long asLong() {
        return Long.valueOf(this.value);
    }

    public String asString() {
        return this.value;
    }

}
