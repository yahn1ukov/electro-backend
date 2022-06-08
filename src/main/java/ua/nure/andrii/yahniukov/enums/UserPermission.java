package ua.nure.andrii.yahniukov.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserPermission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    CHARGER_WRITE("charger:write"),
    CHARGER_READ("charger:read"),
    STATION_READ("station:read"),
    STATION_WRITE("station:write"),
    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),
    MODERATOR_READ("moderator:read"),
    MODERATOR_WRITE("moderator:write");

    private final String permission;
}
