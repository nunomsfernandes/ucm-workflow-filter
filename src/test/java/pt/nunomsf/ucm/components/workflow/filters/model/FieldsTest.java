package pt.nunomsf.ucm.components.workflow.filters.model;

import intradoc.data.DataBinder;
import intradoc.data.DataResultSet;
import intradoc.data.ResultSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FieldsTest {

    @Test
    public void fieldsLoadTest() {
        DataResultSet dataResultSet1 = new DataResultSet(new String []{"xIdcProfile", "xClasseProjeto"});
        dataResultSet1.addRowWithList(Arrays.asList("ARDocs", "AR"));

        Map<String, ResultSet> resultSets = new HashMap<>();
        resultSets.put("QdocInfo", dataResultSet1);

        DataBinder dataBinder = new DataBinder();
        dataBinder.putLocal("dWfName", "ARDocs_Workflow");
        dataBinder.putLocal("dWfStepName", "WFS_ARDocs_Approve");
        dataBinder.putLocal("dAction", "APPROVE");

        Fields fields = new Fields(dataBinder, resultSets);

        Assertions.assertEquals("ARDocs_Workflow", fields.getValue("localData.dWfName").get().asString());
        Assertions.assertEquals("WFS_ARDocs_Approve", fields.getValue("localData.dWfStepName").get().asString());
        Assertions.assertEquals("APPROVE", fields.getValue("localData.dAction").get().asString());
        Assertions.assertEquals("ARDocs", fields.getValue("QdocInfo.xIdcProfile").get().asString());
        Assertions.assertEquals("AR",fields.getValue("QdocInfo.xClasseProjeto").get().asString());
    }

}
