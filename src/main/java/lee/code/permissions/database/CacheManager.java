package lee.code.permissions.database;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lee.code.permissions.Permissions;
import lee.code.permissions.database.tables.PlayerTable;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CacheManager {

    @Getter
    private final Cache<UUID, PlayerTable> playerCache = CacheBuilder
            .newBuilder()
            .initialCapacity(5000)
            .recordStats()
            .build();

    public boolean hasPlayerData(UUID uuid) {
        return getPlayerCache().getIfPresent(uuid) != null;
    }

    private PlayerTable getPlayerTable(UUID player) {
        return getPlayerCache().getIfPresent(player);
    }

    public void createPlayerData(PlayerTable playerTable) {
        getPlayerCache().put(playerTable.getPlayer(), playerTable);
        Permissions.getPlugin().getDatabaseManager().createPlayerTable(playerTable);
    }

    public void setPlayerData(PlayerTable playerTable) {
        getPlayerCache().put(playerTable.getPlayer(), playerTable);
    }

    private void updatePlayerTable(PlayerTable playerTable) {
        getPlayerCache().put(playerTable.getPlayer(), playerTable);
        Permissions.getPlugin().getDatabaseManager().updatePlayerTable(playerTable);
    }

    public void createDefaultColumn(UUID uuid) {
        createPlayerData(new PlayerTable(uuid));
    }

    public void setRank(UUID uuid, String rank) {
        PlayerTable playerTable = getPlayerTable(uuid);
        playerTable.setRank(rank);
        updatePlayerTable(playerTable);
    }

    public String getRank(UUID uuid) {
        return getPlayerTable(uuid).getRank();
    }

    public boolean hasRank(UUID uuid) {
        return !getPlayerTable(uuid).getRank().equals("0");
    }

    public void addPerm(UUID uuid, String perm) {
        PlayerTable playerTable = getPlayerTable(uuid);
        StringBuilder sPerms = new StringBuilder(playerTable.getPerms());
        if (!sPerms.toString().contains(perm)) {
            sPerms = sPerms.toString().equals("0") ? new StringBuilder(perm) : sPerms.append(",").append(perm);
            playerTable.setPerms(sPerms.toString());
            updatePlayerTable(playerTable);
        }
    }

    public void addPermList(UUID uuid, List<String> newPerms) {
        PlayerTable playerTable = getPlayerTable(uuid);
        StringBuilder sPerms = new StringBuilder(playerTable.getPerms());
        if (sPerms.toString().equals("0")) sPerms = new StringBuilder();
        for (String sPerm : newPerms) {
            if (sPerms.isEmpty()) {
                new StringBuilder(sPerm);
            } else {
                if (!sPerms.toString().contains(sPerm)) sPerms.append(",").append(sPerm);
            }
        }
        playerTable.setPerms(sPerms.toString());
        updatePlayerTable(playerTable);
    }

    public void removePerm(UUID uuid, String perm) {
        PlayerTable playerTable = getPlayerTable(uuid);
        String sPerms = playerTable.getPerms();
        List<String> permList = new ArrayList<>(Arrays.asList(StringUtils.split(sPerms, ',')));
        permList.remove(perm);
        String newPerms = StringUtils.join(permList, ",");
        if (newPerms.isBlank()) newPerms = "0";
        playerTable.setPerms(newPerms);
        updatePlayerTable(playerTable);
    }

    public List<String> getPerms(UUID uuid) {
        return new ArrayList<>(Arrays.asList(StringUtils.split(getPlayerTable(uuid).getPerms(), ',')));
    }

    public boolean hasPerm(UUID uuid, String perm) {
        return getPlayerTable(uuid).getPerms().contains(perm);
    }
}
