package de.benji.naturoforumsmp.API.DatsBaseAPI;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DatabaseAPI {
    private final MySQL mySQL;

    public DatabaseAPI(MySQL mySQL) {
        this.mySQL = mySQL;
    }

    public void saveUUIDList(String table, List<UUID> list) {
        if(!mySQL.isConnected()) {
            return;
        }
        mySQL.createTable(table, Arrays.asList("id INT(100)" , "uuid VARCHAR(100)"));
        mySQL.clearTable(table);
        for (int i = 0; i < list.size(); i++) {
            mySQL.insertIntoDatabase(table, Arrays.asList("id", "uuid"), Arrays.asList(String.valueOf(i), "\"" + list.get(i) + "\""));
        }
    }

    public void saveStringBooleanHashMap(String table, HashMap<String, Boolean> map) {
        if(!mySQL.isConnected()) {
            return;
        }
        mySQL.createTable(table, Arrays.asList("id INT(100)" , "string VARCHAR(100)", "boolean BOOLEAN"));
        mySQL.clearTable(table);
        int i = 0;
        for(String s: map.keySet()) {
            mySQL.insertIntoDatabase(table, Arrays.asList("id", "string", "boolean"), Arrays.asList(String.valueOf(i), "\"" + s + "\"", String.valueOf(map.get(s))));
            i++;
        }
    }

    public List<UUID> loadUUIDList(String table) {
        if(!mySQL.isConnected()) {
            return null;
        }
        List<UUID> list = new ArrayList<>();
        int rows = mySQL.getRowCount(table);
        for(int i = 0; i < rows; i++) {
            try {
                ResultSet r = mySQL.getResult(table, i);
                r.next();
                list.add(UUID.fromString(r.getString("uuid")));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
