package ua.nure.andrii.yahniukov.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserPermission {
    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    MODERATOR_READ("moderator:read"),
    MODERATOR_WRITE("moderator:write"),
    CHARGER_READ("charger:read"),
    CHARGER_WRITE("charger:write"),
    STATION_READ("station:read"),
    STATION_WRITE("station:write");

    private final String permission;
}