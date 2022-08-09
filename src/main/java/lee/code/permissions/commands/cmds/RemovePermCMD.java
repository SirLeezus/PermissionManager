package lee.code.permissions.commands.cmds;

import lee.code.permissions.Permissions;
import lee.code.permissions.database.CacheManager;
import lee.code.permissions.lists.Lang;
import lee.code.permissions.managers.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class RemovePermCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        Permissions plugin = Permissions.getPlugin();
        CacheManager cacheManager = plugin.getCacheManager();
        PermissionManager pm = plugin.getPermissionManager();

        if (args.length > 1) {
            OfflinePlayer targetPlayer = Bukkit.getOfflinePlayerIfCached(args[0]);
            if (targetPlayer != null) {
                UUID tUUID = targetPlayer.getUniqueId();
                String name = targetPlayer.getName();
                String perm = args[1];
                if (cacheManager.hasPerm(tUUID, perm)) {
                    cacheManager.removePerm(tUUID, perm);
                    if (targetPlayer.isOnline()) {
                        Player tPlayer = targetPlayer.getPlayer();
                        if (tPlayer != null && !tPlayer.isOp()) pm.register(tPlayer);
                    }
                    sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_REMOVEPERM_SUCCESSFUL.getComponent(new String[] { perm, name })));
                } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_REMOVEPERM_DOES_NOT_HAVE.getComponent(new String[] { name, perm })));
            } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
        } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        return true;
    }
}
