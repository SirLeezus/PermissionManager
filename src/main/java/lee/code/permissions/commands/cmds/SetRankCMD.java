package lee.code.permissions.commands.cmds;

import lee.code.permissions.Data;
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

public class SetRankCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        Permissions plugin = Permissions.getPlugin();
        PermissionManager pm = plugin.getPermissionManager();
        Data data = plugin.getData();
        CacheManager cacheManager = plugin.getCacheManager();

        if (args.length > 1) {
            OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(args[0]);
            if (target != null) {
                UUID tUUID = target.getUniqueId();
                String rank = args[1].toUpperCase();

                if (data.getRanks().contains(rank)) {
                    cacheManager.setRank(tUUID, rank);
                    sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_RANKSET_SUCCESSFUL.getComponent(new String[] { rank, target.getName() })));
                }

                if (target.isOnline()) {
                    Player tPlayer = target.getPlayer();
                    if (tPlayer != null) {
                        pm.register(tPlayer);
                    }
                }
            } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[] { args[0] })));
        } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        return true;
    }
}
