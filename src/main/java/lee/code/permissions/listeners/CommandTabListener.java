package lee.code.permissions.listeners;

import lee.code.permissions.Permissions;
import lee.code.permissions.managers.PermissionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class CommandTabListener implements Listener {

    @EventHandler (priority = EventPriority.LOWEST)
    public void onCommandTabShow(PlayerCommandSendEvent e) {
        Permissions plugin = Permissions.getPlugin();
        PermissionManager pm = plugin.getPermissionManager();

        Player player = e.getPlayer();
        if (!player.isOp()) {
            e.getCommands().clear();
            e.getCommands().addAll(pm.getCommands(player));
        }
    }
}
