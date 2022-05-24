package pt.nunomsf.ucm.components.workflow.filters.model;

import intradoc.data.DataBinder;
import intradoc.data.ResultSet;

import java.util.*;

public class DataFields {

    private final Map<String, DataValueField> values = new HashMap<>();

    public DataFields(DataBinder dataBinder, Map<String, ResultSet> resultSets) {
        Objects.nonNull(dataBinder);
        Objects.nonNull(resultSets);
        loadLocalData(dataBinder);
        loadResultSets(resultSets);
    }
    private void loadLocalData(DataBinder dataBinder) {
        Properties localDataProps = dataBinder.getLocalData();
        localDataProps.keySet().forEach(key -> this.values.put("localData." + key.toString(), DataValueField.of("localData", localDataProps.get(key).toString())));
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
                this.values.put(resultSetName + "." + fieldName, DataValueField.of(resultSetName, fieldValue));
            }
            resultSet.next();
        }
    }

    public Optional<DataValueField> getValue(String fieldName) {
        Objects.requireNonNull(fieldName);
        return Optional.ofNullable(this.values.get(fieldName));
    }



}
