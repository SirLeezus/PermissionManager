package lee.code.permissions;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.permissions.lists.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class PermissionsAPI {

    public static void setRank(Player player, String rank) {
        Permissions plugin = Permissions.getPlugin();
        if (plugin.getData().getRanks().contains(rank)) {
            plugin.getCacheManager().setRank(player.getUniqueId(), rank);
            plugin.getPermissionManager().register(player);
        }
    }

    public static void setRank(UUID uuid, String rank) {
        Permissions plugin = Permissions.getPlugin();
        if (plugin.getData().getRanks().contains(rank)) {
            plugin.getCacheManager().setRank(uuid, rank);
        } else Bukkit.getLogger().log(Level.WARNING, Lang.PREFIX.getString(null) + BukkitUtils.parseColorString("&cThe rank &6" + rank + " &cis not a rank in the perms.yml!"));
    }

    public static void addPermList(Player player, List<String> perms) {
        Permissions plugin = Permissions.getPlugin();
        plugin.getCacheManager().addPermList(player.getUniqueId(), perms);
        plugin.getPermissionManager().register(player);
    }

    public static void addPerm(Player player, String perm) {
        Permissions plugin = Permissions.getPlugin();
        plugin.getCacheManager().addPerm(player.getUniqueId(), perm);
        plugin.getPermissionManager().register(player);
    }

    public static void removePerm(Player player, String perm) {
        Permissions plugin = Permissions.getPlugin();
        plugin.getCacheManager().removePerm(player.getUniqueId(), perm);
        plugin.getPermissionManager().register(player);
    }

    public static void register(Player player) {
        Permissions.getPlugin().getPermissionManager().register(player);
    }

    public static boolean hasRank(UUID uuid) {
        return Permissions.getPlugin().getCacheManager().hasRank(uuid);
    }

    public static String getRank(UUID uuid) {
        return Permissions.getPlugin().getCacheManager().getRank(uuid);
    }
}
