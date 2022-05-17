package pt.nunomsf.ucm.components.workflow.constants;

public class Column {

    private final String name;

    private Column(String name) {
        this.name = name;
    }

    public static Column of(String name) {
        return new Column(name);
    }

    public String name() {
        return this.name;
    }
}
