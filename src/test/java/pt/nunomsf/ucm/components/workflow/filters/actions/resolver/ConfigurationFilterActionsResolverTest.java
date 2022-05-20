package pt.nunomsf.ucm.components.workflow.filters.actions.resolver;

import intradoc.data.DataBinder;
import intradoc.data.DataResultSet;
import intradoc.data.ResultSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.nunomsf.ucm.components.workflow.config.Configuration;
import pt.nunomsf.ucm.components.workflow.filters.actions.IFilterAction;
import pt.nunomsf.ucm.components.workflow.filters.actions.impl.SystemOutFilterAction;
import pt.nunomsf.ucm.components.workflow.filters.actions.impl.VoidFilterAction;
import pt.nunomsf.ucm.components.workflow.filters.model.Fields;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationFilterActionsResolverTest {

    private final IFilterActionsResolver filterActionsResolver;

    public ConfigurationFilterActionsResolverTest() throws IOException {
        Path configurationPath = Paths.get("src","test","resources", "filter-actions.yml");
        String configurationAbsolutePath = configurationPath.toFile().getAbsolutePath();
        Configuration configuration = new Configuration(Files.newInputStream(Paths.get(configurationAbsolutePath)));
        this.filterActionsResolver = new ConfigurationFilterActionsResolver(configuration.getConfigurationRules());
    }

    @Test
    public void resolveActionsFromExistentRulesTest() {
        DataBinder dataBinder = new DataBinder();
        dataBinder.putLocal("dWfName", "ARDocs_Workflow");
        dataBinder.putLocal("dWfStepName", "WFS_ARDocs_Approve");
        dataBinder.putLocal("dAction", "APPROVE");
        DataResultSet dataResultSet1 = new DataResultSet(new String []{"xIdcProfile", "xClasseProjeto"});
        dataResultSet1.addRowWithList(Arrays.asList("ARDocs", "ARDocs"));
        Map<String, ResultSet> resultSets = new HashMap<>();
        resultSets.put("QdocInfo", dataResultSet1);
        Fields fields = new Fields(dataBinder, resultSets);
        List<IFilterAction> filterActions = this.filterActionsResolver.resolveFilterActions(fields);
        Assertions.assertEquals(2, filterActions.size());
        long soutCount = filterActions.stream().filter(fa -> fa instanceof SystemOutFilterAction).count();
        long voidCount = filterActions.stream().filter(fa -> fa instanceof VoidFilterAction).count();
        Assertions.assertEquals(1, soutCount);
        Assertions.assertEquals(1, voidCount);
    }

    @Test
    public void resolveActionsFromNonExistentRulesTest() {
        DataBinder dataBinder = new DataBinder();
        dataBinder.putLocal("dWfName", "INVALID");
        dataBinder.putLocal("dWfStepName", "WFS_ARDocs_Approve");
        dataBinder.putLocal("dAction", "APPROVE");
        DataResultSet dataResultSet1 = new DataResultSet(new String []{"xIdcProfile", "xClasseProjeto"});
        dataResultSet1.addRowWithList(Arrays.asList("ARDocs", "ARDocs"));
        Map<String, ResultSet> resultSets = new HashMap<>();
        resultSets.put("QdocInfo", dataResultSet1);
        Fields fields = new Fields(dataBinder, resultSets);
        List<IFilterAction> filterActions = this.filterActionsResolver.resolveFilterActions(fields);
        Assertions.assertEquals(0, filterActions.size());
    }
}
