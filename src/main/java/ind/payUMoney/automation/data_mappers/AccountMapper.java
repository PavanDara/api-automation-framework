package ind.payUMoney.automation.data_mappers;

import ind.payUMoney.automation.utils.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class AccountMapper {


    public String getAccountData(String dataKey) {
        if (SessionVariableHolder.account_under_test.equals("")) {
            SessionVariableHolder.account_under_test = "default-user";
        }
        return (String) DataFactory.get(true, dataKey, "accounts", SessionVariableHolder.account_under_test, dataKey);
    }

    public JSONArray getJSONArray(String jsonArrayName) {
        return (JSONArray) DataFactory.get(true, jsonArrayName, "accounts", SessionVariableHolder.account_under_test, jsonArrayName);
    }

    public JSONObject getJSONObject(String jsonObjectName) {
        if (SessionVariableHolder.account_under_test.equals("")) {
            SessionVariableHolder.account_under_test = "default-user";
        }
        return (JSONObject)  DataFactory.get(true, jsonObjectName, "accounts", SessionVariableHolder.account_under_test, jsonObjectName);
    }

    public ArrayList<String> getMultipleFields(int numberOfFields, String jsonObjectName, String fieldStartName) {
        ArrayList<String> fieldValues = new ArrayList<>();
        for (int fieldCount = 1; fieldCount <= numberOfFields; fieldCount++) {
            fieldValues.add((String) DataFactory.get(true, "No Field", "accounts", SessionVariableHolder.account_under_test, jsonObjectName, fieldStartName + fieldCount));
        }
        return fieldValues;
    }

    public ArrayList<String> getMultipleFields(String jsonObjectName, String fieldStartName) {
        ArrayList<String> fieldValues = new ArrayList<>();
        for (int fieldCount = 1; ; fieldCount++) {
            String fieldValue = (String) DataFactory.get(true, "No Field", "accounts", SessionVariableHolder.account_under_test, jsonObjectName, fieldStartName + fieldCount);
            if (fieldValue == null || fieldValue.equals("")) {
                break;
            }
            fieldValues.add(fieldValue);
        }
        return fieldValues;
    }
}
