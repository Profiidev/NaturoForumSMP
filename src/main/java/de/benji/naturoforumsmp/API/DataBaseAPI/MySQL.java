package de.benji.naturoforumsmp.API.DataBaseAPI;

import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQL {
    private Connection connection;
    private final String ip;
    private final String database;
    private final String username;
    private final String password;
    private final int port;

    public MySQL(String ip, int port, String database, String username, String password) {
        this.ip = ip;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public void connect() {
        try {
            synchronized (this) {
                if(connection != null && !connection.isClosed()) {
                    return;
                }

                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + database, username, password);

                Bukkit.getLogger().info("MySQL connected!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        if(connection != null) {
            try {
                if(!connection.isClosed()) {
                    return true;
                }
            } catch (SQLException ignored) {}
        }
        return false;
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executeStatement(String statement, List<String> values) {
        try {
            PreparedStatement p = connection.prepareStatement(statement);
            if(values != null)
                for(int i = 0; i < values.size(); i++) {
                    p.setString(i + 1, values.get(i));
                }
            p.executeUpdate();
        } catch (SQLException e) {
            System.out.println(statement);
            e.printStackTrace();
        }
    }

    public void createTable(String table, List<String> keys) {
        String key = String.join(",", keys);
        executeStatement("CREATE TABLE IF NOT EXISTS " + table + " (" + key + ",PRIMARY KEY (`key`));", null);
    }

    public void clearTable(String table) {
        executeStatement("DELETE FROM " + table + ";", null);
    }

    public void addEntry(String table, List<String> keys, List<String> values) {
        String key = String.join("`,`", keys);
        key = "`" + key  + "`";
        List<String> valuePlayholders = new ArrayList<>();
        values.forEach(v -> valuePlayholders.add("?"));
        String value = String.join(",", valuePlayholders);

        executeStatement("INSERT INTO " + table + " (" + key + ") VALUES (" + value + ");", values);
    }

    public void addEntryWithDupe(String table, List<String> keys, List<String> values, List<String> onDupeKeys, List<String> onDupeValues) {
        String key = String.join("`,`", keys);
        key = "`" + key  + "`";
        List<String> valuePlayholders = new ArrayList<>();
        values.forEach(v -> valuePlayholders.add("?"));
        String value = String.join(",", valuePlayholders);
        StringBuilder onDupe = new StringBuilder();
        List<String> values1 = new ArrayList<>(values);
        for(int i = 0; i < onDupeKeys.size(); i++) {
            onDupe.append(" `")
                    .append(onDupeKeys.get(i))
                    .append("`=?");
            values1.add(onDupeValues.get(i));
        }
        executeStatement("INSERT INTO " + table + " (" + key + ") VALUES (" + value + ") ON DUPLICATE KEY UPDATE" + onDupe + ";", values1);
    }
}
