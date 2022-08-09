package lee.code.permissions;

import lee.code.core.util.files.FileManager;
import lee.code.permissions.commands.cmds.AddPermCMD;
import lee.code.permissions.commands.cmds.RemovePermCMD;
import lee.code.permissions.commands.cmds.SetRankCMD;
import lee.code.permissions.commands.tabs.AddPermTab;
import lee.code.permissions.commands.tabs.RemovePermTab;
import lee.code.permissions.commands.tabs.SetRankTab;
import lee.code.permissions.database.DatabaseManager;
import lee.code.permissions.database.CacheManager;
import lee.code.permissions.listeners.CommandTabListener;
import lee.code.permissions.listeners.JoinListener;
import lee.code.permissions.managers.PermissionManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class Permissions extends JavaPlugin {

    @Getter private CacheManager cacheManager;
    @Getter private DatabaseManager databaseManager;
    @Getter private PermissionManager permissionManager;
    @Getter private FileManager fileManager;
    @Getter private Data data;


    @Override
    public void onEnable() {
        this.cacheManager = new CacheManager();
        this.databaseManager = new DatabaseManager();
        this.permissionManager = new PermissionManager();
        this.fileManager = new FileManager(this);
        this.data = new Data();

        databaseManager.initialize();
        data.load();

        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
        databaseManager.closeConnection();
    }

    private void registerCommands() {
        getCommand("addperm").setExecutor(new AddPermCMD());
        getCommand("addperm").setTabCompleter(new AddPermTab());
        getCommand("removeperm").setExecutor(new RemovePermCMD());
        getCommand("removeperm").setTabCompleter(new RemovePermTab());
        getCommand("setrank").setExecutor(new SetRankCMD());
        getCommand("setrank").setTabCompleter(new SetRankTab());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new CommandTabListener(), this);
    }

    public static Permissions getPlugin() {
        return Permissions.getPlugin(Permissions.class);
    }
}
