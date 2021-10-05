package me.leonardo.flarumregister.functions.mysql;

import me.leonardo.flarumregister.FlarumRegister;
import org.bukkit.Bukkit;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class MySQLAccess  {
    private FlarumRegister plugin;
    Connection c;
    ResultSet rs;
    Statement s;
    public boolean startConnection()  {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            c= DriverManager.getConnection(
                "jdbc:mysql://"+this.plugin.getConfig().getString("mysql.host")+":"+this.plugin.getConfig().getInt("mysql.port")+"/"+this.plugin.getConfig().getString("mysql.database"),this.plugin.getConfig().getString("mysql.username"),this.plugin.getConfig().getString("mysql.password"));
        } catch (Exception e) {
            e.printStackTrace();
            if (String.valueOf(e.getMessage()).equals("")){
                return true;
            }
        }
        return false;
    }



    public void executeQuery (String statement) {
        try {
            startConnection();
            s = c.createStatement();
            rs = s.executeQuery(statement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void executeUpdate (String statement) {
        try {
            startConnection();
            s = c.createStatement();
            s.executeUpdate(statement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public Connection getConnection() {
        return c;
    }

    public ResultSet getResult() {
        return rs;
    }

    public Statement getStatement() {
        return s;
    }

    public MySQLAccess(FlarumRegister plugin) {
        this.plugin = plugin;
    }
}


