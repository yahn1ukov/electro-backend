package ua.nure.andrii.yahniukov.security.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.models.entities.users.ChargerUserEntity;
import ua.nure.andrii.yahniukov.models.entities.users.StationUserEntity;
import ua.nure.andrii.yahniukov.models.entities.users.UserEntity;
import ua.nure.andrii.yahniukov.repositories.users.UserRepository;
import ua.nure.andrii.yahniukov.security.models.SecurityUser;
import ua.nure.andrii.yahniukov.services.UserService;

@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (userRepository.existsByEmail(email)) {
            UserEntity user = userService.findUserByEmail(email);
            return SecurityUser.fromUser(user);
        } else if (userRepository.existsByEmail(email)) {
            ChargerUserEntity chargerUser = userService.findChargerUserByEmail(email);
            return SecurityUser.fromUser(chargerUser);
        } else {
            StationUserEntity stationUser = userService.findStationUserByEmail(email);
            return SecurityUser.fromUser(stationUser);
        }
    }
}
