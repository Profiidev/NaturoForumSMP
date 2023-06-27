package de.benji.naturoforumsmp.API.DataBaseAPI;

import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    private void executeStatement(String statement) {
        try {
            PreparedStatement p = connection.prepareStatement(statement);
            p.executeUpdate();
        } catch (SQLException e) {
            System.out.println(statement);
            e.printStackTrace();
        }
    }

    public void createTable(String table, List<String> keys) {
        String key = String.join(",", keys);
        executeStatement("CREATE TABLE IF NOT EXISTS " + table + " (" + key + ",PRIMARY KEY (`key`));");
    }

    public void clearTable(String table) {
        executeStatement("DELETE FROM " + table + ";");
    }

    public void addEntry(String table, List<String> keys, List<String> values) {
        String key = String.join("`,`", keys);
        key = "`" + key  + "`";
        String value = String.join("','", values);
        value = "'" + value + "'";
        executeStatement("INSERT INTO " + table + " (" + key + ") VALUES (" + value + ");");
    }

    public void addEntryWithDupe(String table, List<String> keys, List<String> values, List<String> onDupeKeys, List<String> onDupeValues) {
        String key = String.join("`,`", keys);
        key = "`" + key  + "`";
        String value = String.join("','", values);
        value = "'" + value + "'";
        StringBuilder onDupe = new StringBuilder();
        for(int i = 0; i < onDupeKeys.size(); i++) {
            onDupe.append("`").append(onDupeKeys.get(i)).append("`='").append(onDupeValues.get(i)).append("'");
        }
        executeStatement("INSERT INTO " + table + " (" + key + ") VALUES (" + value + ") ON DUPLICATE KEY UPDATE " + onDupe + ";");
    }
}
