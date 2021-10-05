package me.leonardo.flarumregister;


import me.leonardo.flarumregister.commands.WebAuth;
import me.leonardo.flarumregister.functions.mysql.MySQLAccess;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.File;
import java.util.logging.Logger;

public final class FlarumRegister extends JavaPlugin {



    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Plugin enabled.");
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getCommand("webauth").setExecutor(new WebAuth(this));
        MySQLAccess sql = new MySQLAccess(this);


    }






    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Plugin disabled.");
    }

}

