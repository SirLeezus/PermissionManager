package lee.code.permissions.lists;

import lee.code.core.util.bukkit.BukkitUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;

@AllArgsConstructor
public enum Lang {
    PREFIX("&ePermissions &6➔ &r"),
    USAGE("&6&lUsage: &e{0}"),
    COMMAND_ADDPERM_SUCCESSFUL("&aThe perm &b{0} &awas successfully added to the player &6{1}&a!"),
    COMMAND_REMOVEPERM_SUCCESSFUL("&aThe perm &b{0} &awas successfully removed to the player &6{1}&a!"),
    ERROR_ADDPERM_HAS_PERM("&cThe player &6{0} &calready has the permission &b{1}&c."),
    ERROR_PLAYER_NOT_FOUND("&cThe player &6{0} &ccould not be found."),
    ERROR_REMOVEPERM_DOES_NOT_HAVE("&cThe player &6{0} &cdoes not have the permission &b{1}&c."),
    COMMAND_RANKSET_SUCCESSFUL("&aThe rank &6{0} &awas successfully set for the player &6{1}&a!"),
    ;

    @Getter private final String string;

    public String getString(String[] variables) {
        String value = string;
        if (variables == null || variables.length == 0) return BukkitUtils.parseColorString(value);
        for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
        return BukkitUtils.parseColorString(value);
    }

    public Component getComponent(String[] variables) {
        String value = string;
        if (variables == null || variables.length == 0) return BukkitUtils.parseColorComponent(value);
        for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
        return BukkitUtils.parseColorComponent(value);
    }
}
