package pt.nunomsf.ucm.components.workflow.filters.model;

import intradoc.data.DataBinder;
import intradoc.data.FieldInfo;
import intradoc.data.ResultSet;

import java.util.*;

public class FilterActionData {

    private Map<String, Object> localData = new HashMap<>();
    private Map<String, List<Map<String, Object>>> collections = new HashMap<>();

    public FilterActionData(DataBinder dataBinder, Map<String, ResultSet> collections) {
        Properties localDataProps = dataBinder.getLocalData();
        localDataProps.keySet().forEach(key -> this.localData.put(key.toString(), localDataProps.get(key)));

        for (String collectionKey : collections.keySet()) {
            ResultSet rs = collections.get(collectionKey);
            List<Map<String, Object>> rows = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> rsValues = new HashMap<>();
                int numFields = rs.getNumFields();
                for (int i = 0; i < numFields; i++) {
                    String fieldName = rs.getFieldName(i);
                    FieldInfo fieldInfo = new FieldInfo();
                    boolean exist = rs.getFieldInfo(fieldName, fieldInfo);
                    if (exist) {
                        int m_type = fieldInfo.m_type;
                        Object fieldValue = m_type == FieldInfo.DATE ? rs.getDateValue(i) : rs.getStringValue(i);
                        rsValues.put(fieldName, fieldValue);
                    }
                }
                rows.add(rsValues);
            }
            this.collections.put(collectionKey, rows);
        }
    }

    public Field getLocalValue (String fieldName) {
        return new Field(this.localData.get(fieldName));
    }

    public Field getCollectionValue(String collectionListName, Integer collectionRow, String fieldName) {
        List<Map<String, Object>> collection = Optional.ofNullable(this.collections.get(collectionListName)).orElse(new ArrayList<>());
        Map<String, Object> fields = collection.get(collectionRow);
        return new Field(Optional.ofNullable(fields.get(fieldName)).get());
    }


    public class Field {
        private Object fieldValue;
        private Field (Object fieldValue) {
            this.fieldValue = fieldValue;
        }

        public String asString() {
            return fieldValue.toString();
        }
        public Date asDate() {
            return (Date) fieldValue;
        }
        public Long asLong() {
            return Long.valueOf(fieldValue.toString());
        }



    }

}
