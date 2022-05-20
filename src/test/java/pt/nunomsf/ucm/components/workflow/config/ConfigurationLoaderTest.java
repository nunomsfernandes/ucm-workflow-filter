package pt.nunomsf.ucm.components.workflow.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ConfigurationLoaderTest {

    @Test
    public void loadConfigurationTest() throws FileNotFoundException {
        Path configurationPath = Paths.get("src","test","resources", "filter-actions.yml");
        String configurationAbsolutePath = configurationPath.toFile().getAbsolutePath();

        Configuration conf = new Configuration(new FileInputStream(configurationAbsolutePath));
        Assertions.assertEquals(1, conf.getConfigurationRules().getRules().size());
        Rule rule0 = conf.getConfigurationRules().getRules().get(0);
        Map<String, String> conditions0 = rule0.getConditions();
        List<String> actions = rule0.getActions();

        Assertions.assertEquals(5, conditions0.size());
        Assertions.assertEquals("ARDocs_Workflow", conditions0.get("localData.dWfName"));
        Assertions.assertEquals("WFS_ARDocs_Approve", conditions0.get("localData.dWfStepName"));
        Assertions.assertEquals("APPROVE", conditions0.get("localData.dAction"));
        Assertions.assertEquals("ARDocs", conditions0.get("QdocInfo.xIdcProfile"));
        Assertions.assertEquals("ARDocs", conditions0.get("QdocInfo.xClasseProjeto"));

        Assertions.assertEquals(2, actions.size());

        List<String> expectedActions = Arrays.asList("pt.nunomsf.ucm.components.workflow.filters.actions.impl.SystemOutFilterAction",
                "pt.nunomsf.ucm.components.workflow.filters.actions.impl.VoidFilterAction");
        Assertions.assertTrue(actions.containsAll(expectedActions));
    }

}
