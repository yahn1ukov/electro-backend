package ua.nure.andrii.yahniukov.security.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.models.entities.users.UserEntity;
import ua.nure.andrii.yahniukov.security.models.SecurityUser;
import ua.nure.andrii.yahniukov.services.UserService;

@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userService.findUserByEmail(email);
        return SecurityUser.fromUser(user);
    }
}
