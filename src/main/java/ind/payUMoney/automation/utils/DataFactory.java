package ind.payUMoney.automation.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFactory {
    private static Object parsedJsonFile = null;

    public static void parseDataFile() {
        String testData = System.getProperty("testData");
        if (testData != null && !testData.equals("")) {
            JSONParser parser = new JSONParser();
            try {
                BufferedReader reader = ResourceLoader.load("data/" + testData);
                parsedJsonFile = parser.parse(reader);
            } catch (Exception e) {
                if (e instanceof IOException) {
                    throw new RuntimeException("File not found " + testData);
                } else if (e instanceof ParseException) {
                    throw new RuntimeException("Error parsing " + testData);
                } else {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * Example usage:
     * test.json
     * {
     *     "accounts": {
     *         "household": {
     *             "username": "RealUsername"
     *         }
     *     }
     * }
     *
     * DataFactory.get("household", "accounts.household.username");
     * DataFactory.get("household", "accounts", "household", "username");
     * Returns:
     *      If testData flag is provided: "RealUsername"
     *      If no flag is provided: "household"
     *
     * Also works with arrays: DataFactory.get("household", "accounts.household[0].username");
     */
    public static Object get(String mockValue, String lookup) {
        return get(mockValue, lookup.split("\\."));
    }

    public static Object get(String mockValue, String... lookup) {
        if (parsedJsonFile != null) {
            return mapData(parsedJsonFile, lookup);
        } else {
            return mockValue;
        }
    }

    private static Object mapData(Object jsonContent, String... keys) {
        return Arrays.stream((Object[]) keys)
                .reduce(jsonContent, (result, keyObject) -> {
                    String key = (String) keyObject;
                    JSONObject jsonResult = (JSONObject) result;
                    return isArrayLookup(key) ? getObjectFromArrayBasedOnKey(key, jsonResult) : jsonResult.get(key);
                });
    }

    private static boolean isArrayLookup(String s) {
        return s.matches("([\\w_]*)\\[\\d+]+");
    }

    private static int getIndex(String s) {
        Pattern p = Pattern.compile("\\[(\\d+)]");
        Matcher m = p.matcher(s);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        } else {
            throw new RuntimeException("Index parsing failed " + s);
        }
    }

    private static Object getObjectFromArrayBasedOnKey(String keyWithIndex, JSONObject result) {
        int i = getIndex(keyWithIndex);
        String keyWithoutIndex = keyWithIndex.split("\\[" + i + "]")[0];
        JSONArray array = (JSONArray) result.get(keyWithoutIndex);
        return array.get(i);
    }
}
