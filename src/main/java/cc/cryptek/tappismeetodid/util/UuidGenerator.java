package cc.cryptek.tappismeetodid.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public final class UuidGenerator {

    public static UUID getOrGenerateUuid(UUID id) {
        if (id == null) {
            id = UUID.randomUUID();
        }

        return id;
    }
}
