package pt.nunomsf.ucm.components.workflow.filters.model;

import intradoc.data.DataBinder;
import intradoc.data.ResultSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class Fields {

    private final Map<String, Field> values = new HashMap<>();

    public Fields(DataBinder dataBinder, Map<String, ResultSet> resultSets) {
        loadLocalData(dataBinder);
        loadResultSets(resultSets);
    }
    private void loadLocalData(DataBinder dataBinder) {
        Properties localDataProps = dataBinder.getLocalData();
        localDataProps.keySet().forEach(key -> this.values.put("localData." + key.toString(), Field.of("localData", localDataProps.get(key).toString())));
    }
    private void loadResultSets(Map<String, ResultSet> resultSets) {
        for (String resultSetName : resultSets.keySet()) {
            ResultSet resultSet = resultSets.get(resultSetName);
            loadResultSet(resultSetName, resultSet);
        }
    }
    private void loadResultSet(String resultSetName, ResultSet resultSet) {
        while (resultSet.isRowPresent()) {
            int resultSetNumFields = resultSet.getNumFields();
            for (int i = 0; i < resultSetNumFields; i++) {
                String fieldName = resultSet.getFieldName(i);
                String fieldValue = resultSet.getStringValue(i);
                this.values.put(resultSetName + "." + fieldName, Field.of(resultSetName, fieldValue));
            }
            resultSet.next();
        }
    }

    public Optional<Field> getValue(String fieldName) {
        return Optional.ofNullable(this.values.get(fieldName));
    }



}
