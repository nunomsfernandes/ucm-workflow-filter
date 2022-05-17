package pt.nunomsf.ucm.components.workflow.config;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class ConfigurationLoaderTest {

    @Test
    public void loadConfigurationTest() throws FileNotFoundException {
        Configuration loader = new Configuration("/home/nunomsf/projects/nunofern/ucm-workflow-filter/src/test/resources/filter-actions.yml");
        System.out.println(">" + loader);
    }

}
