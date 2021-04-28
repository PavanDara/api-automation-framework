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
    private static Object parse_json_file = null;
    private static Object parsed_account_mapper_file = null;

    public static void parseDataFile(String nodeName, String endPointName) throws Exception {
        if (SessionVariableHolder.environment_under_test != null && !SessionVariableHolder.environment_under_test.equals("")) {
            JSONParser parser = new JSONParser();
//            BufferedReader reader = ResourceLoader.load("data/" + SessionVariableHolder.environment_under_test + ".json");
//            parse_json_file = parser.parse(reader);
            BufferedReader reader = ResourceLoader.load("data/" + nodeName + "/" + endPointName + "/" + endPointName + "_" + SessionVariableHolder.environment_under_test + ".json");
            parsed_account_mapper_file = parser.parse(reader);
        }
    }

    public static Object get(String mockValue, String lookup) {
        return get(mockValue, lookup.split("\\."));
    }

    public static Object get(String mockValue, String... lookup) {
        if (parse_json_file != null) {
            return mapData(parse_json_file, lookup);
        } else {
            return mockValue;
        }
    }

    public static Object get(boolean accountMapper, String mockValue, String... lookup) {
        if (accountMapper) {
            if (parsed_account_mapper_file != null) {
                return mapData(parsed_account_mapper_file, lookup);
            } else {
                return mockValue;
            }
        } else {
            if (parse_json_file != null) {
                return mapData(parse_json_file, lookup);
            } else {
                return mockValue;
            }
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
