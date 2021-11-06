package me.leonardo.flarumregister.commands;

import me.leonardo.flarumregister.FlarumRegister;
import me.leonardo.flarumregister.functions.mysql.MySQLAccess;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WebAuth implements CommandExecutor {
    private FlarumRegister plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (command.getName().equalsIgnoreCase("webauth")) {
                if (args.length >= 1) {
                    if (p.hasPermission("flarumregister.command.register") || p.hasPermission("flarumregister.command.webauth")) {
                        p.sendMessage("debug 1");
                        String prefix = this.plugin.getConfig().getString("flarum.table-prefix");
                        try {
                            MySQLAccess sql = new MySQLAccess(this.plugin);
                            sql.executeQuery("SELECT * FROM " + prefix + "users;");
                            p.sendMessage("debug 2");
                            while (sql.getResult().next()) {
                                p.sendMessage("debug 3");
                                if (sql.getResult().getString("username") == p.getName()) {
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("messages.registration-error-already")));
                                    p.sendMessage("debug 4");
                                } else {
                                    String dateFormatted = LocalDateTime.now()
                                            .format(DateTimeFormatter
                                                    .ofPattern("yyyy-MM-dd HH:mm:ss"));

                                    String hash = BCrypt.hashpw(args[0], BCrypt.gensalt());
                                    sql.executeUpdate("INSERT INTO " + prefix + "users" + " (username,is_email_confirmed,joined_at,password,email) VALUES " + "('" + p.getName() + "'," + 1 + ",'" + dateFormatted + "','" + hash + "','" + p.getName() + "@minecraft');");
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.registration-success")));
                                }
                            }

                            sql.getConnection().close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&',this.plugin.getConfig().getString("messages.registration-no-perm")));
                    }
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("messages.registration-sintax-error")));
                }
            }

        }
        return true;
    }


    public WebAuth(FlarumRegister plugin) {
        this.plugin = plugin;
    }

}
