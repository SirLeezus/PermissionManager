package lee.code.permissions.listeners;

import lee.code.permissions.Permissions;
import lee.code.permissions.database.CacheManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.UUID;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerPreLogin(PlayerLoginEvent e) {
        Permissions plugin = Permissions.getPlugin();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        CacheManager cacheManager = plugin.getCacheManager();

        //player data check
        if (!cacheManager.hasPlayerData(uuid)) cacheManager.createDefaultColumn(uuid);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        //register perms
        Permissions.getPlugin().getPermissionManager().register(player);
    }
}
