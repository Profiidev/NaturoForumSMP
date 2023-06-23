package de.benji.naturoforumsmp.API.DatsBaseAPI;

import java.sql.*;
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

        connect();
    }

    private void connect() {
        try {
            synchronized (this) {
                if(connection != null && !connection.isClosed()) {
                    return;
                }

                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + database, username, password);

                System.out.println("MySQL connected!");
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

    public void createTable(String table, List<String> keys) {
        StringBuilder key = new StringBuilder(keys.get(0));
        for(int i = 1; i < keys.size(); i++) {
            key.append(",").append(keys.get(i));
        }
        try {
            PreparedStatement p = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + table + " (" + key + ")");
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearTable(String table) {
        try {
            PreparedStatement p = connection.prepareStatement("DELETE FROM " + table);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getRowCount(String table) {
        try {
            PreparedStatement p = connection.prepareStatement("SELECT COUNT(*) FROM " + table);
            ResultSet r = p.executeQuery();
            r.next();
            return r.getInt("COUNT(*)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ResultSet getResult(String table, int id, int subid) {
        try {
            PreparedStatement p = connection.prepareStatement("SELECT * FROM " + table + " WHERE (id=? AND subid=?)");
            p.setInt(1, id);
            p.setInt(2, subid);

            return p.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getResult(String table, int id) {
        try {
            PreparedStatement p = connection.prepareStatement("SELECT * FROM " + table + " WHERE id=?");
            p.setInt(1, id);

            return p.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertIntoDatabase(String table, List<String> keys, List<String> values) {
        StringBuilder key = new StringBuilder(keys.get(0));
        for(int i = 1; i < keys.size(); i++) {
            key.append(",").append(keys.get(i));
        }
        StringBuilder value = new StringBuilder(values.get(0));
        for(int i = 1; i < values.size(); i++) {
            value.append(",").append(values.get(i));
        }
        try {
            PreparedStatement p = connection.prepareStatement("INSERT INTO " + table + "(" + key + ") VALUE (" + value + ")");
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
