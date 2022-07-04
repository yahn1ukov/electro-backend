package ua.nure.andrii.yahniukov.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.chargerUser.ChargerUserEntity;
import ua.nure.andrii.yahniukov.chargerUser.ChargerUserService;
import ua.nure.andrii.yahniukov.security.model.SecurityUser;
import ua.nure.andrii.yahniukov.stationUser.StationUserEntity;
import ua.nure.andrii.yahniukov.stationUser.StationUserService;
import ua.nure.andrii.yahniukov.user.UserEntity;
import ua.nure.andrii.yahniukov.user.UserService;

@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;
    private final ChargerUserService chargerUserService;
    private final StationUserService stationUserService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (userService.existsByEmail(email)) {
            UserEntity user = userService.findByEmail(email);
            return SecurityUser.fromUser(user);
        } else if (chargerUserService.existsByEmail(email)) {
            ChargerUserEntity chargerUser = chargerUserService.findByEmail(email);
            return SecurityUser.fromUser(chargerUser);
        } else {
            StationUserEntity stationUser = stationUserService.findByEmail(email);
            return SecurityUser.fromUser(stationUser);
        }
    }
}