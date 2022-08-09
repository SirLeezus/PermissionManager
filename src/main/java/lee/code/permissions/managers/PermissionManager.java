package lee.code.permissions.managers;

import lee.code.permissions.Data;
import lee.code.permissions.Permissions;
import lee.code.permissions.database.CacheManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.*;

public class PermissionManager {

    public void register(Player player) {
        Permissions plugin = Permissions.getPlugin();
        Data data = plugin.getData();
        CacheManager cacheManager = plugin.getCacheManager();
        UUID uuid = player.getUniqueId();

        PermissionAttachment attachment = player.addAttachment(plugin);
        if (!player.isOp()) attachment.getPermissions().clear();
        for (PermissionAttachmentInfo perm : player.getEffectivePermissions()) attachment.setPermission(perm.getPermission(), false);

        //set database perms
        for (String perm : cacheManager.getPerms(uuid)) attachment.setPermission(perm, true);

        //yml default perms
        for (String perm : data.getDefaultPerms()) attachment.setPermission(perm, true);

        //yml rank perms
        if (cacheManager.hasRank(uuid)) {
            for (String rank : data.getRanks()) {
                if (cacheManager.getRank(uuid).equals(rank)) {
                    for (String perm : data.getPerms(rank)) attachment.setPermission(perm, true);
                }
            }
        }

        player.recalculatePermissions();
        player.updateCommands();
    }

    public Collection<String> getCommands(Player player) {
        Permissions plugin = Permissions.getPlugin();
        Collection<String> commands = new HashSet<>();
        CommandMap commandMap = plugin.getServer().getCommandMap();
         for (Map.Entry<String, Command> sCommand : commandMap.getKnownCommands().entrySet()) {
             String perm = sCommand.getValue().getPermission();
             if (perm != null && player.hasPermission(perm)) {
                 Command command = sCommand.getValue();
                 List<String> aliases = command.getAliases();
                 commands.addAll(aliases);
                 commands.add(command.getName());
             }
         }
        return commands;
    }
}
