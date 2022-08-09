package lee.code.permissions.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum PermsFile {
    RANKS("ranks"),
    ;

    @Getter private final String path;
}
