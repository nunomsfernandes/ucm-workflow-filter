package pt.nunomsf.ucm.components.workflow.config;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class Configuration {
    private final Rules rules;

    public Configuration (String configurationFilePath) throws FileNotFoundException {
        this(new FileInputStream(configurationFilePath));
    }

    public Configuration (InputStream configurationInputStream) {
        Constructor constructor = new Constructor(Rules.class);//Car.class is root
        Yaml yaml = new Yaml(constructor);
        this.rules = yaml.load(configurationInputStream);
    }

    public Rules getRules() {
        return rules;
    }
}
