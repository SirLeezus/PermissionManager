package lee.code.permissions.database.tables;

import lee.code.core.ormlite.field.DatabaseField;
import lee.code.core.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "player")
public class PlayerTable {

    @DatabaseField(id = true, canBeNull = false)
    private UUID player;

    @DatabaseField(columnName = "perms", canBeNull = false)
    private String perms;

    @DatabaseField(columnName = "rank", canBeNull = false)
    private String rank;

    public PlayerTable(UUID player) {
        this.player = player;
        this.perms = "0";
        this.rank = "0";
    }
}