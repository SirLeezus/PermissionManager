package lee.code.permissions;

import lee.code.core.util.files.FileManager;
import lee.code.permissions.lists.PermsFile;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Data {

    private final ConcurrentHashMap<String, List<String>> ranks = new ConcurrentHashMap<>();

    public List<String> getRanks() {
        return new ArrayList<>(ranks.keySet());
    }
    public List<String> getDefaultPerms() { return ranks.get("DEFAULT"); }
    public List<String> getPerms(String rank) { return ranks.get(rank); }

    public void load() {
        //perms
        FileManager fileManager = Permissions.getPlugin().getFileManager();
        String fileName = "perms";
        fileManager.createYML(fileName);
        YamlConfiguration yamlConfig = fileManager.getYML(fileName).getFile();

        if (yamlConfig.contains(PermsFile.RANKS.getPath())) {
            ConfigurationSection ranksSection = yamlConfig.getConfigurationSection(PermsFile.RANKS.getPath());
            if (ranksSection != null) {
                ranksSection.getKeys(false).forEach(rank -> ranks.put(rank, ranksSection.getStringList(rank)));
            }
        }
    }
}
