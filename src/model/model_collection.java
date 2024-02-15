package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class model_collection {
    private static HashMap<String, List<String>> adminDB;

    static {
        adminDB = new HashMap<>();
    }

    public static void addDataInModels(String key, List<String> value) {
        adminDB.put(key, value);
    }

    public static HashMap<String, List<String>> getAdminDB() {
        return adminDB;
    }

    public static void main(String[] args) {
        String key = "admin1";
        // List<String> value = Arrays.asList("value1", "value2", "value3");


        // Testing
        System.out.println(getAdminDB());
    }
}

