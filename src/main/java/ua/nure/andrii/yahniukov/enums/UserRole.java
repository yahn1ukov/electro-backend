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
    ADMIN(Set.of(ADMIN_READ, ADMIN_WRITE)),
    USER(Set.of(USER_READ, USER_WRITE)),
    MODERATOR(Set.of(MODERATOR_READ, MODERATOR_WRITE)),
    CHARGER(Set.of(CHARGER_READ, CHARGER_WRITE)),
    STATION(Set.of(STATION_READ, STATION_WRITE));

    private final Set<UserPermission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}