package ua.nure.andrii.yahniukov.security.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.ChargerUser.ChargerUserEntity;
import ua.nure.andrii.yahniukov.ChargerUser.ChargerUserRepository;
import ua.nure.andrii.yahniukov.ChargerUser.ChargerUserService;
import ua.nure.andrii.yahniukov.StationUser.StationUserEntity;
import ua.nure.andrii.yahniukov.StationUser.StationUserService;
import ua.nure.andrii.yahniukov.User.UserEntity;
import ua.nure.andrii.yahniukov.User.UserRepository;
import ua.nure.andrii.yahniukov.User.UserService;
import ua.nure.andrii.yahniukov.security.models.SecurityUser;

@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final ChargerUserService chargerUserService;
    private final ChargerUserRepository chargerUserRepository;
    private final StationUserService stationUserService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (userRepository.existsByEmail(email)) {
            UserEntity user = userService.findByEmail(email);
            return SecurityUser.fromUser(user);
        } else if (chargerUserRepository.existsByEmail(email)) {
            ChargerUserEntity chargerUser = chargerUserService.findByEmail(email);
            return SecurityUser.fromUser(chargerUser);
        } else {
            StationUserEntity stationUser = stationUserService.findByEmail(email);
            return SecurityUser.fromUser(stationUser);
        }
    }
}
