package ua.nure.andrii.yahniukov.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static ua.nure.andrii.yahniukov.enums.UserPermission.*;

@Getter
@AllArgsConstructor
public enum UserRole {
    ADMIN(Set.of(
            ADMIN_READ,
            ADMIN_WRITE,
            GET_USER
    )),
    MODERATOR(Set.of(
            MODERATOR_WRITE,
            MODERATOR_READ,
            GET_USER
    )),
    USER(Set.of(
            USER_READ,
            USER_WRITE,
            GET_USER
    )),
    CHARGER(Set.of(
            CHARGER_READ,
            CHARGER_WRITE,
            GET_PARTNER
    )),
    SERVICE(Set.of(
            STATION_WRITE,
            STATION_READ,
            GET_PARTNER
    ));

    private final Set<UserPermission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
