package ua.nure.andrii.yahniukov.security.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ua.nure.andrii.yahniukov.ChargerUser.ChargerUserEntity;
import ua.nure.andrii.yahniukov.StationUser.StationUserEntity;
import ua.nure.andrii.yahniukov.User.UserEntity;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class SecurityUser implements UserDetails {

    private final String username;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;
    private final Boolean isBlock;
    private final Boolean isVerification;

    public static UserDetails fromUser(UserEntity user) {
        return new User(
                user.getEmail(),
                user.getPassword(),
                user.getIsVerification(),
                true,
                true,
                user.getIsNotBlock(),
                user.getRole().getAuthorities()
        );
    }

    public static UserDetails fromUser(ChargerUserEntity chargerUser) {
        return new User(
                chargerUser.getEmail(),
                chargerUser.getPassword(),
                chargerUser.getIsVerification(),
                true,
                true,
                chargerUser.getIsNotBlock(),
                chargerUser.getRole().getAuthorities()
        );
    }

    public static UserDetails fromUser(StationUserEntity stationUser) {
        return new User(
                stationUser.getEmail(),
                stationUser.getPassword(),
                stationUser.getIsVerification(),
                true,
                true,
                stationUser.getIsNotBlock(),
                stationUser.getRole().getAuthorities()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
