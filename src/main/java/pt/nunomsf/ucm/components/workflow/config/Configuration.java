package pt.nunomsf.ucm.components.workflow.config;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;


public class Configuration {
    private final ConfigurationRules configurationRules;

    public Configuration (InputStream configurationInputStream) {
        Constructor constructor = new Constructor(ConfigurationRules.class);//Car.class is root
        Yaml yaml = new Yaml(constructor);
        this.configurationRules = yaml.load(configurationInputStream);
    }

    public ConfigurationRules getConfigurationRules() {
        return configurationRules;
    }
}
